package com.users.users.models.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
 public class VerifyOtpRequest {
    private String username;
    private String otp;
    private String password;


}
