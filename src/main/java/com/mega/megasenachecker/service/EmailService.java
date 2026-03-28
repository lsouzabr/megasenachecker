package com.mega.megasenachecker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviar(String destino, String titulo, String mensagem) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("lsouzabr@gmail.com");
        mail.setTo("lsouzabr@gmail.com");
        mail.setSubject(titulo);
        mail.setText(mensagem);

        mailSender.send(mail);

    }
}