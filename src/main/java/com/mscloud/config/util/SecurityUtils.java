package com.mscloud.config.util;


import org.apache.commons.codec.digest.HmacUtils;

public class SecurityUtils {

    public static String generatePublicKey(String clinicId, String timestamp, String nonce) {
        return HmacUtils.hmacSha256Hex("staticSalt123", clinicId + timestamp + nonce);
    }

    public static String generateHmac(String privateKey, String dataToSign) {
        return HmacUtils.hmacSha256Hex(privateKey, dataToSign);
    }
}
