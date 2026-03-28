package com.mega.megasenachecker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${app.email.sender}")
    private String emailSender;

    @Value("${app.email.recipient}")
    private String emailRecipient;

    public void enviar(String titulo, String mensagem) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(emailSender);
        mail.setTo(emailRecipient);
        mail.setSubject(titulo);
        mail.setText(mensagem);

        mailSender.send(mail);

    }
}