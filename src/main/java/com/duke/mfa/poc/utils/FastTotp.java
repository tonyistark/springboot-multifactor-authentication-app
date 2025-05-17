package com.duke.mfa.poc.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.util.encoders.Base32;

/**
 * A lightweight Time based OTP generator and verifier.
 */
public final class FastTotp {
    private static final int DEFAULT_TIME_STEP_SECONDS = 30;
    private static final String HMAC_ALGORITHM = "HmacSHA1";

    private FastTotp() {
    }

    static byte[] decodeBase32(String secret) {
        return Base32.decode(secret);
    }

    static int generateCode(byte[] key, long timestep, int digits) {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(8).order(ByteOrder.BIG_ENDIAN);
            buffer.putLong(timestep);
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            mac.init(new SecretKeySpec(key, HMAC_ALGORITHM));
            byte[] hash = mac.doFinal(buffer.array());
            int offset = hash[hash.length - 1] & 0x0F;
            int binary = ((hash[offset] & 0x7F) << 24) | ((hash[offset + 1] & 0xFF) << 16)
                    | ((hash[offset + 2] & 0xFF) << 8) | (hash[offset + 3] & 0xFF);
            int otp = binary % (int) Math.pow(10, digits);
            return otp;
        } catch (Exception e) {
            throw new IllegalStateException("Failed to generate TOTP", e);
        }
    }

    public static String generateCurrentCode(char[] secret, int digits) {
        long timestep = System.currentTimeMillis() / 1000L / DEFAULT_TIME_STEP_SECONDS;
        byte[] key = decodeBase32(PasswordUtils.getBase32TotpSecret(secret));
        int code = generateCode(key, timestep, digits);
        return String.format("%0" + digits + "d", code);
    }

    public static boolean verifyCode(char[] secret, String code, int window, int digits) {
        long timestep = System.currentTimeMillis() / 1000L / DEFAULT_TIME_STEP_SECONDS;
        byte[] key = decodeBase32(PasswordUtils.getBase32TotpSecret(secret));
        for (int i = -window; i <= window; i++) {
            int candidate = generateCode(key, timestep + i, digits);
            if (code.equals(String.format("%0" + digits + "d", candidate))) {
                return true;
            }
        }
        return false;
    }
}
