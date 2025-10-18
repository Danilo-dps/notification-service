package com.danilodps.notification.record;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record TransactionResponse(UUID transactionId, BigDecimal amount, LocalDateTime transactionTimestamp, String userSender, String receiver) { }
