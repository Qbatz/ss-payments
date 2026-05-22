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

    public static java.util.Date addDaysToDate(java.util.Date date, int noOfDays) {
        return java.util.Date.from(date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate().plusDays(noOfDays).atStartOfDay(java.time.ZoneId.systemDefault()).toInstant());
    }

    public static int compareWithTwoDates(java.util.Date date1, java.util.Date date2) {
        java.time.LocalDate localDate1 = date1.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        java.time.LocalDate localDate2 = date2.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();

        return localDate1.compareTo(localDate2);
    }
}
