package com.mega.megasenachecker.infrastructure.adapter.out.email;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailNotificationAdapterTest {

    @Mock
    private JavaMailSender mailSender;

    private EmailNotificationAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new EmailNotificationAdapter(mailSender);
        ReflectionTestUtils.setField(adapter, "emailSender", "remetente@gmail.com");
        ReflectionTestUtils.setField(adapter, "emailRecipient", "destinatario@gmail.com");
    }

    @Test
    void notificar_deveChamarMailSenderSend() {
        adapter.notificar("Assunto teste", "Corpo da mensagem");

        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void notificar_deveEnviarComRemetenteCorreto() {
        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        adapter.notificar("Assunto", "Mensagem");

        verify(mailSender).send(captor.capture());
        assertThat(captor.getValue().getFrom()).isEqualTo("remetente@gmail.com");
    }

    @Test
    void notificar_deveEnviarParaDestinatarioCorreto() {
        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        adapter.notificar("Assunto", "Mensagem");

        verify(mailSender).send(captor.capture());
        assertThat(captor.getValue().getTo()).contains("destinatario@gmail.com");
    }

    @Test
    void notificar_deveDefinirAssuntoCorreto() {
        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        adapter.notificar("Resultado Concurso 2800", "Mensagem");

        verify(mailSender).send(captor.capture());
        assertThat(captor.getValue().getSubject()).isEqualTo("Resultado Concurso 2800");
    }

    @Test
    void notificar_deveDefinirCorpoCorreto() {
        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        adapter.notificar("Assunto", "Corpo da mensagem esperada");

        verify(mailSender).send(captor.capture());
        assertThat(captor.getValue().getText()).isEqualTo("Corpo da mensagem esperada");
    }
}

