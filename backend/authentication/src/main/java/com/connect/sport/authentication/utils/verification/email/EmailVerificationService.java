package com.connect.sport.authentication.utils.verification.email;

import com.connect.sport.authentication.model.User;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@NoArgsConstructor
public class EmailVerificationService {

    private final static String EMAIL_CONFIRMATION_SUBJECT = "Confirm your SportConnect account";

    @Autowired
    private JavaMailSender javaMailSender;

    public String generateVerificationToken() {
        return UUID.randomUUID().toString();
    }

    public void sendVerificationEmail(User user, String verificationToken) {

        String verificationLink = "http://localhost:8081/api/v1/auth/verify?token="
                + verificationToken + "&email=" + decodeEmail(user.getEmail());
        String emailContent = "Hello " + user.getFirstName() + " " + user.getLastName()
                + ",\nPlease click the following link to verify your email address: " + verificationLink;

        sendSimpleMessage(user.getEmail(), EMAIL_CONFIRMATION_SUBJECT, emailContent);
    }

    public void sendSimpleMessage(String to, String subject, String text) {


        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        javaMailSender.send(message);
    }

    private String decodeEmail(String email) {
        List<String> tokens = Arrays.stream(email.split("@")).toList();

        if (tokens.isEmpty()) {
            return "";
        }

        return tokens.get(0) + "%40" + tokens.get(1);
    }
}
