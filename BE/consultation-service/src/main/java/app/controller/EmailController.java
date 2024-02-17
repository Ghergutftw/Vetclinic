package app.controller;

import app.dto.AccountInfo;
import app.dto.Response;
import app.service.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email-service")
@Slf4j
@AllArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send-email")
    public Response sendEmailWithNewAccountInfo(@RequestParam String to, @RequestBody AccountInfo accountInfo){
        return emailService.sendNewAccountInformations(to, accountInfo);
    }

}
