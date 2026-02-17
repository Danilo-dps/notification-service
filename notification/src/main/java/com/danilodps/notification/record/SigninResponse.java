package com.danilodps.notification.record;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record SigninResponse(String id, String username, String email, LocalDateTime signinTimestamp){}
