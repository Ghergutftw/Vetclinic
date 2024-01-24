package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//Deoarece folosim FeignClient, trebuie sa adaugam @EnableFeignClients
@SpringBootApplication
public class AdoptionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdoptionServiceApplication.class, args);
    }

}
