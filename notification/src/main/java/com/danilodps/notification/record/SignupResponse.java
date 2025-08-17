package com.danilodps.notification.record;

import lombok.Builder;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record SignupResponse(String id, String username, String email, LocalDateTime now){}