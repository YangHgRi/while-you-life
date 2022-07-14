package yanghgri.whileyouplay;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.format.FormatterRegistry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import yanghgri.whileyouplay.enums.Integer2GameTagConverter;
import yanghgri.whileyouplay.enums.Integer2ReviewConverter;

@SpringBootApplication
@EnableAsync
@EnableScheduling
@EnableTransactionManagement
@MapperScan("yanghgri.whileyouplay.mapper")
public class WhileYouPlayApplication implements WebMvcConfigurer {
    public static void main(String[] args) {
        SpringApplication.run(WhileYouPlayApplication.class, args);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new Integer2GameTagConverter());
        registry.addConverter(new Integer2ReviewConverter());
    }
}