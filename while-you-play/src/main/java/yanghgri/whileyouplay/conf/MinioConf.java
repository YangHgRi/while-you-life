package yanghgri.whileyouplay.conf;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConf {
    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder().endpoint("http://localhost:9000/").credentials("user", "S36871414").build();
    }

}
