package com.danilodps.notification.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) { this.mailSender = mailSender; }

    public void sendEmail(String to, String subject, String text){
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            mailSender.send(message);
            log.info("E-mail enviado com sucesso para: {}, referente ao assunto {}", to, subject);
        } catch (Exception e){
            log.error("Erro ao enviar e-mail: {}", e.getMessage(), e);
            throw new IllegalArgumentException("Erro ao enviar e-mail");
        }
    }
}
