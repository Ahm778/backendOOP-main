package com.ekher.projet.demo.services;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class EmailService {
    private final JavaMailSender mailSender;
    private final ResourceLoader resourceLoader;

    @Autowired
    EmailService(JavaMailSender mailSender, ResourceLoader resourceLoader) {
        this.mailSender = mailSender;
        this.resourceLoader = resourceLoader;
    }

    private String loadEmailTemplate() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:templates/welcome-email-template.html");
        return new String(Files.readAllBytes(Paths.get(resource.getURI())));
    }

    @Async
    public void sendSimpleEmail(String toEmail, String subject, String password) {
        try {

            String emailTemplate = loadEmailTemplate();

            String emailBody = emailTemplate
                    .replace("{{email}}", toEmail)
                    .replace("{{password}}", password);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setFrom("TrainingCentermanagement@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(emailBody, true);

            mailSender.send(mimeMessage);
            System.out.println("Mail sent successfully...");
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }
    @Async
    public void sendPasswordResetEmail(String toEmail, String code) {
        try {
            Resource resource = resourceLoader.getResource("classpath:templates/password-reset-template.html");
            String emailTemplate = new String(Files.readAllBytes(Paths.get(resource.getURI())));
            String resetLink = "https://localhost:4200/reset-password?code=" + code;
            String emailBody = emailTemplate
                    .replace("{{email}}", toEmail)
                    .replace("{{resetLink}}", resetLink);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setFrom("TrainingCentermanagement@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject("Password Reset Request");
            helper.setText(emailBody, true);

            mailSender.send(mimeMessage);
            System.out.println("Password reset email sent successfully to " + toEmail);
        } catch (Exception e) {
            System.err.println("Failed to send password reset email: " + e.getMessage());
        }
    }
}