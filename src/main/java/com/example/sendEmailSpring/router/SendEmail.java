package com.example.sendEmailSpring.router;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

//@Configuration
@RestController
public class SendEmail {

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("sendEmail")
    public ResponseEntity<?> sendEmail(){

        SimpleMailMessage email = new SimpleMailMessage();
        //email which will receive the message
        email.setTo("difsanchezru@gmail.com");

        //email which send the message
        email.setFrom("quisofka@gmail.com");

        //Email's tittle
        email.setSubject("Mensaje de prueba 1");

        //Email's message
        email.setText("this is just a test");

        // what actually send the email
        mailSender.send(email);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
