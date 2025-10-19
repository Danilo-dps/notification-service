package com.danilodps.notification.record;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;

@Builder
public record DepositResponse(UUID depositId, String username, String userEmail, BigDecimal amount, LocalDateTime depositTimestamp) {
}
