package app.feign;

import app.dto.AccountInfo;

import app.dto.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "consultation-service", url = "http://localhost:8082/email-service/")
public interface EmailInterface {

    @PostMapping("/send-email")
    Response sendEmailWithNewAccountInfo(@RequestParam String to, @RequestBody AccountInfo accountInfo);
}