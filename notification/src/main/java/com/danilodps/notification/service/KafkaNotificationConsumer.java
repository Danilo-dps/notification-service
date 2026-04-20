package com.danilodps.notification.service;

import com.danilodps.commons.domain.model.response.DepositResponse;
import com.danilodps.commons.domain.model.response.SignInResponse;
import com.danilodps.commons.domain.model.response.SignUpResponse;
import com.danilodps.commons.domain.model.response.TransactionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaNotificationConsumer {

    private static final String PAYLOAD_MESSAGE = "Payload inválido, objeto não pode ser nulo";
    private final EmailService emailService;

    @KafkaListener(
            groupId = "consumer-group-v1",
            topics = {"${spring.kafka.consumer.topic.kafka-deposit}"},
            containerFactory = "listenerContainerFactory"
    )
    public void handleDepositCreated(DepositResponse depositResponse){
        if(depositResponse == null){
            log.error(PAYLOAD_MESSAGE);
            return;
        }
        sendEmailNotification(depositResponse.userEmail(), "Depósito realizado com sucesso", String.format("Olá %s, foi depositado o valor de %.2f",
                depositResponse.username(),
                depositResponse.amount()));
    }

    @KafkaListener(
            groupId = "consumer-group-v1",
            topics = {"${spring.kafka.consumer.topic.kafka-transfer}"},
            containerFactory = "listenerContainerFactory"
    )
    public void handleTransferCreated(TransactionResponse transactionResponse){
        if(transactionResponse == null){
            log.error(PAYLOAD_MESSAGE);
            return;
        }
        sendEmailNotification(transactionResponse.receiverEmail(), "Transferência realizada com sucesso", String.format("Olá %s, foi transferido o valor de %.2f",
                transactionResponse.senderEmail(),
                transactionResponse.amount()));
    }

    @KafkaListener(
            groupId = "consumer-group-v1",
            topics = {"${spring.kafka.consumer.topic.kafka-signup}"},
            containerFactory = "listenerContainerFactory"
    )
    public void handleSignUpCreated(SignUpResponse signupResponse){
        if(signupResponse == null){
            log.error(PAYLOAD_MESSAGE);
            return;
        }
        sendEmailNotification(signupResponse.email(), "Cadastro feito com sucesso", String.format("Olá %s, cadastro feito com sucesso para o email %s",
                signupResponse.username(),
                signupResponse.email()));
    }

    @KafkaListener(
            groupId = "consumer-group-v1",
            topics = {"${spring.kafka.consumer.topic.kafka-signin}"},
            containerFactory = "listenerContainerFactory"
    )
    public void handleSignInCreated(SignInResponse signinResponse){
        if(signinResponse == null){
            log.error(PAYLOAD_MESSAGE);
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
