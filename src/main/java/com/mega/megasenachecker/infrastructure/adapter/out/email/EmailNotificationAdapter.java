package com.mega.megasenachecker.infrastructure.adapter.out.email;

import com.mega.megasenachecker.domain.port.out.NotificacaoPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationAdapter implements NotificacaoPort {

    private final JavaMailSender mailSender;

    @Value("${app.email.sender}")
    private String emailSender;

    @Value("${app.email.recipient}")
    private String emailRecipient;

    public EmailNotificationAdapter(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void notificar(String titulo, String mensagem) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(emailSender);
        mail.setTo(emailRecipient);
        mail.setSubject(titulo);
        mail.setText(mensagem);
        mailSender.send(mail);
    }
}

