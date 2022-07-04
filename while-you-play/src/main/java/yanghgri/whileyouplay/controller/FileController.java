package yanghgri.whileyouplay.controller;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@Slf4j
public class FileController {
    private static final String defaultBucketName = "default";
    private final MinioClient minioClient;

    @Autowired
    public FileController(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    @PostMapping("upload")
    public void upload(@RequestPart("file") MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        minioClient.putObject(PutObjectArgs.builder().bucket(defaultBucketName).object(file.getOriginalFilename()).stream(file.getInputStream(), file.getSize(), -1L).build());
    }

    @GetMapping("download")
    public void download(@RequestParam("file-name") String fileName, HttpServletResponse response) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        try (InputStream file = minioClient.getObject(GetObjectArgs.builder().bucket(defaultBucketName).object(fileName).build());
             OutputStream outputStream = response.getOutputStream()) {
            byte[] bytes = new byte[4096];
            int i;
            while ((i = file.read(bytes)) > 0) {
                outputStream.write(bytes, 0, i);
            }
        }
    }
}