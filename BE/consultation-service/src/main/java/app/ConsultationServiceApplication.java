package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class ConsultationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsultationServiceApplication.class, args);
    }

}
