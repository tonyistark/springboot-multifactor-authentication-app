package com.duke.mfa.poc.utils;

import junit.framework.TestCase;

/**
 * Tests for {@link FastTotp}.
 */
public class FastTotpTest extends TestCase {

    public void testGenerateCode() {
        String secret = "JBSWY3DPEHPK3PXP"; // base32 for 'Hello!1234'
        byte[] key = FastTotp.decodeBase32(secret);
        assertEquals(282760, FastTotp.generateCode(key, 0, 6));
        assertEquals(996554, FastTotp.generateCode(key, 1, 6));
        assertEquals(602287, FastTotp.generateCode(key, 2, 6));
        assertEquals(143627, FastTotp.generateCode(key, 3, 6));
    }

    public void testVerifyCode() {
        char[] secret = "Hello!1234".toCharArray();
        String code = FastTotp.generateCurrentCode(secret, 6);
        assertTrue(FastTotp.verifyCode(secret, code, 0, 6));
    }
}
