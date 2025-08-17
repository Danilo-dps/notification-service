package com.danilodps.notification.record;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record SigninResponse(String id, String username, String email, LocalDateTime now){}
