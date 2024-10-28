package com.example.demo;

import com.example.demo.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventListener {
    private final JavaMailSender mailSender;

    @EventListener
    @Order(2)
    public void sendWelcomeEmail(UserCreatedEvent event) {
        System.out.println("fisrt listener");

        // 이메일 전송 로직
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(event.getUserEmail());
//        message.setSubject("Practice EDA!!");
//        message.setFrom(event.getUserEmail());
//        message.setText("Practice EDA!!");
//        System.out.println(message.getText());
//        mailSender.send(message);
//        System.out.println("email sent to: " + event.getUserEmail());
    }
    @EventListener
    @Order(1)
    public void handleUserCreatedEvent(UserCreatedEvent event) {
        // 두번째 곁가지 로직
        System.out.println("second listener");
    }
}
