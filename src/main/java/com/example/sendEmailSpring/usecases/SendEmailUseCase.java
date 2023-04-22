package com.example.sendEmailSpring.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
// I cann't use this for inject the JavaMailSender, I have to use the @Atowired
//@RequiredArgsConstructor
public class SendEmailUseCase {

    //I need to use that with Autowired, instead of @RequiredArgsConstructor
    @Autowired
    private JavaMailSender mailSender;

    public Mono<Void> sendEmail(String email){
        return Mono.fromRunnable(
                ()->{
                    SimpleMailMessage email1 = new SimpleMailMessage();
                    //email which will receive the message
                    email1.setTo(email);

                    //email which send the message
                    email1.setFrom("quisofka@gmail.com");

                    //Email's tittle
                    email1.setSubject("Mensaje de prueba 1");

                    //Email's message
                    email1.setText("this is just a test");

                    System.out.println(email1);

                    // what actually send the email
                   mailSender.send(email1);
                }
        );
    }
}
