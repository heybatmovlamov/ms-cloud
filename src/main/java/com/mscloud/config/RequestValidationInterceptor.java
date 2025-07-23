package com.mscloud.config;

import com.mscloud.config.util.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class RequestValidationInterceptor implements HandlerInterceptor {

    private final Map<String, String> clinicSecrets = Map.of(
            "1", "clinic1PrivateKey"
    );

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String clinicId = request.getHeader("X-Clinic-Id");
        String publicKey = request.getHeader("X-Public-Key");
        String encryptedKey = request.getHeader("X-Encrypted-Key");
        String timestamp = request.getHeader("X-Timestamp");
        String nonce = request.getHeader("X-Nonce");

        log.info("🛡️ Klinikadan gələn sorğu:");
        log.info("Clinic ID        : {}", clinicId);
        log.info("Timestamp        : {}", timestamp);
        log.info("Nonce            : {}", nonce);
        log.info("Public Key       : {}", publicKey);
        log.info("Encrypted Key    : {}", encryptedKey);



        if (clinicId == null || publicKey == null || encryptedKey == null || timestamp == null || nonce == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing security headers");
            return false;
        }

        String privateKey = clinicSecrets.get(clinicId);
        if (privateKey == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Clinic not authorized");
            return false;
        }

        String expectedEncryptedKey = SecurityUtils.generateHmac(privateKey, publicKey);
        if (!expectedEncryptedKey.equals(encryptedKey)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Signature mismatch");
            return false;
        }

        return true;
    }
}
