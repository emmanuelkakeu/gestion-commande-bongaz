package com.users.users.services.auth;



import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Service
public class OtpService {

    private static final int OTP_LENGTH = 6;
    private static final SecureRandom secureRandom = new SecureRandom();
    private final Map<String, String> otpStorage = new HashMap<>();

    public String generateOtp() {
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(secureRandom.nextInt(10));
        }
        return otp.toString();
    }
    public void saveOtp(String username, String otp) {
        otpStorage.put(username, otp);
    }

    public boolean verifyOtp(String username, String otp) {
        return otp.equals(otpStorage.get(username));
    }
}
