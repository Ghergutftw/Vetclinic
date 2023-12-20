package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
    //There must be a better way to do this, but I couldn't find it.
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("doctor-service", r -> r.path("/doctor-service/**")
                        .uri("lb://localhost:8084/"))
                .route("patient-service", r -> r.path("/patient-service/**")
                        .uri("lb://localhost:8081/"))
                .route("appointment-service", r -> r.path("/appointment-service/**")
                        .uri("lb://localhost:8083/"))
                .build();
    }

}
