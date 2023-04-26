package com.example.sendEmailSpring.usecases;


import com.example.sendEmailSpring.model.Email;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.thymeleaf.TemplateEngine;
import reactor.core.publisher.Mono;

@Service
public class SendHtmlEmailUseCase {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailProperties mailProperties;

    @Autowired
    private ResourceLoader resourceLoader;

    public Mono<Void> sendHtmlEmail(Email email) {
        return Mono.fromRunnable(() -> {
            try {
                // Load the HTML template from the classpath
                System.out.println("entrando a sendHtmlEmail");
                Resource resource = new ClassPathResource("template/email-template.html");
                System.out.println("guardando resource");
                System.out.println(resource);
                byte[] contentBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
                String htmlBody = new String(contentBytes, "UTF-8");
                System.out.println("imprimiendo htmlBody");
                System.out.println(htmlBody);

                // Replace placeholders in the HTML template with dynamic values
                htmlBody = htmlBody.replace("[[name]]", email.getName());

                MimeMessage message = mailSender.createMimeMessage();

                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setFrom("quissofka@gmail.com");
                helper.setTo(email.getTo());
                helper.setSubject(email.getMessageSubject());
                helper.setText(htmlBody, true);

                mailSender.send(message);
            } catch (Exception ex) {
                throw new RuntimeException( ex);
            }
        });
    }
}
