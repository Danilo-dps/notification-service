package com.danilodps.notification.record;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record DepositResponse(String depositId, String username, String userEmail, BigDecimal amount, LocalDateTime depositTimestamp) {
}
