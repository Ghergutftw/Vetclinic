package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

//Deoarece folosim FeignClient, trebuie sa adaugam @EnableFeignClients
@SpringBootApplication
@EnableFeignClients
public class AdoptionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdoptionServiceApplication.class, args);
    }

}
