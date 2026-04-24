package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {

    private PasswordUtil() {}

    public static String hash(String plainText) {
        if (plainText == null || plainText.isEmpty()) return "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(plainText.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
            System.err.println("Loi hash mat khau: " + e.getMessage());
            return plainText;
        }
    }

    public static boolean verify(String plainText, String hashedPassword) {
        return hash(plainText).equals(hashedPassword);
    }
}