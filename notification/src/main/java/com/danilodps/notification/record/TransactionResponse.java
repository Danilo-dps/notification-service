package com.danilodps.notification.record;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record TransactionResponse(String transactionId, BigDecimal amount, LocalDateTime transactionTimestamp, String userSenderEmail, String receiverEmail, String userSenderName, String receiverName) { }
