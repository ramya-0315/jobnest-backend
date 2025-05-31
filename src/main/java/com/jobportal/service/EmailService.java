package com.jobportal.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("your-email@gmail.com");
            helper.setTo(to);
            helper.setFrom(System.getenv("EMAIL_USERNAME"));
            helper.setSubject(subject);
            helper.setText(text, true);
            mailSender.send(message);
            System.out.println("Email sent successfully to " + to);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }

    }
}





