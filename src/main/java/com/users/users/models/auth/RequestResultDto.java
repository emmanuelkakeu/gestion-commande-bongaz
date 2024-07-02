package com.users.users.models.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestResultDto<Body> {
    private int code;
    private Body data;
    private String message;
    private String status;
    private Instant timestamp;

    // Getters and Setters
}

