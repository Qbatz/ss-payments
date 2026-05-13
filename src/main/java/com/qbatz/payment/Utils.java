package com.qbatz.payment;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.HexFormat;

public class Utils {
    public static String generateHmac(String data, String secret) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec keySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        mac.init(keySpec);

        byte[] rawHmac = mac.doFinal(data.getBytes());
        return HexFormat.of().formatHex(rawHmac);
    }
}
