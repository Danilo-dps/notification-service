package com.danilodps.notification.service;

import com.danilodps.notification.config.KafkaConfigNotification;
import com.danilodps.notification.record.DepositResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaNotificationConsumer {

    private final EmailService emailService;

    public KafkaNotificationConsumer(EmailService emailService) { this.emailService = emailService; }

    @KafkaListener(
            topics = KafkaConfigNotification.DEPOSIT_NOTIFICATION_TOPIC,
            groupId = "notification-deposit-group",
            containerFactory = "depositResponseConcurrentKafkaListenerContainerFactory"
    )
    public void handleDepositCreated(DepositResponse depositResponse){
        if(depositResponse == null){
            log.error("Payload inválido: Comprovante é nulo");
            return;
        }

    }

    private void sendEmailNotification(String email, String subject, String message){
        try{
            emailService.sendEmail(email, subject, message);
        } catch (MatchException e){
            log.error("Erro ao enviar o email de notificação '{}' para {}: {}", subject, email, e.getMessage(), e);
        }
        catch (Exception e){
            log.error("Erro inesperado ao processar o evento '{}': {}", subject, e.getMessage(), e);
        }
    }
}
