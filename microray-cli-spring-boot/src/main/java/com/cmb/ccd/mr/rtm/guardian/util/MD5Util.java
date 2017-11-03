package com.cmb.ccd.mr.rtm.guardian.util;

import java.security.MessageDigest;

/**
 * 采用MD5 Hash
 */
public class MD5Util {

    final private static char[] HEX_ARRAY = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String getMD5(String string) {
        return getMD5(string.getBytes());
    }

    public static String getMD5(byte[] bytes) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception ignored) {
        }

        assert md5 != null;
        byte[] result = md5.digest(bytes);
        return bytesToHex(result);
    }

    /**
     * Convert a byte array to a Hex String.
     */
    private static String bytesToHex(byte[] bytes) {

        if (null == bytes || bytes.length == 0)
            return "";

        char[] hexChars = new char[bytes.length * 2];
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}
