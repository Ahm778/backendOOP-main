package com.ekher.projet.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    @Autowired
    EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendSimpleEmail(String toEmail, String subject, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("TrainingCentermanagement@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);

        String emailBody = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Welcome to Our Platform</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            background-color: #f4f4f4;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "        }\n" +
                "        .email-container {\n" +
                "            width: 100%;\n" +
                "            max-width: 600px;\n" +
                "            margin: 0 auto;\n" +
                "            background-color: #ffffff;\n" +
                "            padding: 20px;\n" +
                "            border-radius: 8px;\n" +
                "        }\n" +
                "        .email-header {\n" +
                "            background-color: #4CAF50;\n" +
                "            color: white;\n" +
                "            padding: 10px;\n" +
                "            border-radius: 8px 8px 0 0;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "        .email-body {\n" +
                "            margin-top: 20px;\n" +
                "        }\n" +
                "        .email-footer {\n" +
                "            text-align: center;\n" +
                "            font-size: 12px;\n" +
                "            color: #777;\n" +
                "            margin-top: 20px;\n" +
                "        }\n" +
                "        .button {\n" +
                "            background-color: #4CAF50;\n" +
                "            color: white;\n" +
                "            padding: 10px 20px;\n" +
                "            text-decoration: none;\n" +
                "            border-radius: 5px;\n" +
                "            font-weight: bold;\n" +
                "        }\n" +
                "        .button:hover {\n" +
                "            background-color: #45a049;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"email-container\">\n" +
                "        <div class=\"email-header\">\n" +
                "            <h2>Welcome to Our Platform!</h2>\n" +
                "        </div>\n" +
                "        <div class=\"email-body\">\n" +
                "            <p>Hello,</p>\n" +
                "            <p>Thank you for registering with us. You have successfully registered with the email address: <strong>" + toEmail + "</strong>.</p>\n" +
                "            <p>This is an auto-generated password for your account: <strong>" + password + "</strong></p>\n" +
                "            <p>You can change your password at any time from your account settings on the platform.</p>\n" +
                "        </div>\n" +
                "        <div class=\"email-footer\">\n" +
                "            <p>Best regards,</p>\n" +
                "            <p>Training Center</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>\n";

        message.setText(emailBody);
        mailSender.send(message);
        System.out.println("Mail sent successfully...");
    }
}

