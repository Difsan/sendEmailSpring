package com.example.sendEmailSpring.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Email {

    String to;
    String messageSubject;
    String messageBody;
    String name;
}
