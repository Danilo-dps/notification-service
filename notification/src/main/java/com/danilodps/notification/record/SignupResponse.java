package com.danilodps.notification.record;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record SignupResponse(String id, String username, String email, LocalDateTime signupTimestamp){}