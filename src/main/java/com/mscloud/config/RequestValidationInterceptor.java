package com.mscloud.config;

import com.mscloud.config.util.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
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
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String clinicId = request.getHeader("X-Clinic-Id");
        String timestamp = request.getHeader("X-Timestamp");
        String nonce = request.getHeader("X-Nonce");
        String receivedPublicKey = request.getHeader("X-Public-Key");
        String receivedEncryptedKey = request.getHeader("X-Encrypted-Key");

        log.info("🛡️ Klinikadan gələn sorğu:");
        log.info("Clinic ID        : {}", clinicId);
        log.info("Timestamp        : {}", timestamp);
        log.info("Nonce            : {}", nonce);
        log.info("Public Key       : {}", receivedPublicKey);
        log.info("Encrypted Key    : {}", receivedEncryptedKey);

        if (clinicId == null || timestamp == null || nonce == null || receivedPublicKey == null ||
                receivedEncryptedKey == null) {
            log.warn("❌ Missing headers from clinic {}", clinicId);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing headers");
            return false;
        }

        String privateKey = clinicSecrets.get(clinicId);
        if (privateKey == null) {
            log.warn("❌ Unknown clinic: {}", clinicId);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized clinic");
            return false;
        }

        // 🧠 CLOUD TƏRƏFDƏ PUBLIC KEY-i YENİDƏN HESABLA
        String calculatedPublicKey = SecurityUtils.generatePublicKey(clinicId, timestamp, nonce);

        // 1️⃣ PUBLIC KEY MÜQAYİSƏ
        if (!calculatedPublicKey.equals(receivedPublicKey)) {
            log.warn("❌ PublicKey mismatch for clinic {}: expected={}, received={}", clinicId, calculatedPublicKey,
                    receivedPublicKey);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Public key mismatch");
            return false;
        }

        // 2️⃣ ENCRYPTED KEY DOĞRULAMA
        String expectedEncryptedKey = SecurityUtils.generateHmac(privateKey, calculatedPublicKey);

        if (!expectedEncryptedKey.equals(receivedEncryptedKey)) {
            log.warn("❌ Encrypted key mismatch for clinic {}: expected={}, received={}", clinicId, expectedEncryptedKey,
                    receivedEncryptedKey);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Signature mismatch");
            return false;
        }

        log.info("✅ Signature validation passed for clinic {}", clinicId);
        return true;
    }
}