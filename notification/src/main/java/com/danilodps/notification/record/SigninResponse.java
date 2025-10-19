package com.danilodps.notification.record;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;

@Builder
public record SigninResponse(UUID id, String username, String email, LocalDateTime signinTimestamp){}
