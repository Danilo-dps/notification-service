package com.danilodps.notification.service;

import com.danilodps.notification.config.KafkaConfigNotification;
import com.danilodps.notification.record.DepositResponse;
import com.danilodps.notification.record.SigninResponse;
import com.danilodps.notification.record.SignupResponse;
import com.danilodps.notification.record.TransferResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaNotificationConsumer {

    private static final String PAYLOAD = "Payload inválido: Comprovante é nulo";
    private final EmailService emailService;

    public KafkaNotificationConsumer(EmailService emailService) { this.emailService = emailService; }

    @KafkaListener(
            topics = KafkaConfigNotification.DEPOSIT_NOTIFICATION_TOPIC,
            groupId = "notification-deposit-group",
            containerFactory = "depositResponseConcurrentKafkaListenerContainerFactory"
    )
    public void handleDepositCreated(DepositResponse depositResponse){
        if(depositResponse == null){
            log.error(PAYLOAD);
            return;
        }
        sendEmailNotification(depositResponse.userEmail(), "Depósito realizado com sucesso", String.format("Olá %s, foi depositado o valor de %.2f",
                depositResponse.username(),
                depositResponse.amount()));
    }

    @KafkaListener(
            topics = KafkaConfigNotification.TRANSFER_NOTIFICATION_TOPIC,
            groupId = "notification-transfer-group",
            containerFactory = "transferResponseConcurrentKafkaListenerContainerFactory"
    )
    public void handleTransferCreated(TransferResponse transferResponse){
        if(transferResponse == null){
            log.error(PAYLOAD);
            return;
        }
        sendEmailNotification(transferResponse.fromEmail(), "Transferência realizada com sucesso", String.format("Olá %s, foi transferido o valor de %.2f",
                transferResponse.fullName(),
                transferResponse.amount()));
    }

    @KafkaListener(
            topics = KafkaConfigNotification.SIGN_UP,
            groupId = "notification-signup-group",
            containerFactory = "signUpResponseConcurrentKafkaListenerContainerFactory"
    )
    public void handleSignupCreated(SignupResponse signupResponse){
        if(signupResponse == null){
            log.error(PAYLOAD);
            return;
        }
        sendEmailNotification(signupResponse.email(), "Cadastro feito com sucesso", String.format("Olá %s, cadastro feito com sucesso para o email %s",
                signupResponse.username(),
                signupResponse.email()));
    }

    @KafkaListener(
            topics = KafkaConfigNotification.SIGN_IN,
            groupId = "notification-signin-group",
            containerFactory = "signInResponseConcurrentKafkaListenerContainerFactory"
    )
    public void handleSigninCreated(SigninResponse signinResponse){
        if(signinResponse == null){
            log.error(PAYLOAD);
            return;
        }
        sendEmailNotification(signinResponse.email(), "Login feito com sucesso", String.format("Olá %s, login feito com sucesso para o email %s",
                signinResponse.username(),
                signinResponse.email()));
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
