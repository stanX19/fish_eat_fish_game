package com.deepseadevs.fisheatfish;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginUtils {

    public static String hashString(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(input.getBytes());

            StringBuilder hexString = new StringBuilder(2 * encodedHash.length);
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error initializing SHA-256 algorithm", e);
        }
    }

    public static void main(String[] args) {
        String input = "YourStringToHash";
        String hashed = hashString(input);
        System.out.println("Hashed String: " + hashed);
    }
}

