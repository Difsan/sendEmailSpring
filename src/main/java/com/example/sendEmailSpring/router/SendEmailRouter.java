package com.example.sendEmailSpring.router;


import com.example.sendEmailSpring.model.Email;
import com.example.sendEmailSpring.usecases.SendEmailUseCase;
import com.example.sendEmailSpring.usecases.SendHtmlEmailUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Collections;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
//@RestController
//@RequiredArgsConstructor
public class SendEmailRouter {

    @Bean
    public RouterFunction<ServerResponse> sendEmail(SendEmailUseCase sendEmailUseCase){
        return route(POST("/sendEmail/{email}"),
                request -> sendEmailUseCase.sendEmail(request.pathVariable("email"))
                        .thenReturn(ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(Collections.singletonMap("message","email sent")))
                        .flatMap(serverResponseMono -> serverResponseMono)
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.BAD_REQUEST).bodyValue(throwable.getMessage()))

                );
    }

    @Bean
    public RouterFunction<ServerResponse> sendEmailHtml (SendHtmlEmailUseCase sendHtmlEmailUseCase) {
        return route(POST("/sendHtmlEmail").and(accept(MediaType.APPLICATION_JSON)),
                    request -> request.bodyToMono(Email.class)
                            .flatMap(email -> sendHtmlEmailUseCase.sendHtmlEmail(email)
                                    .flatMap(result -> ServerResponse.status(201)
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .bodyValue(Collections.singletonMap("message","email sent")))
                                    .onErrorResume(throwable -> ServerResponse.status(HttpStatus.BAD_REQUEST)
                                            .bodyValue(throwable.getMessage()))));
    }



}
