package com.github.soyamiyoshi.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import static com.github.soyamiyoshi.util.EnvLoader.getEnvOrExit;

public class KeyLoader {

    private static String publicKeyPath = getEnvOrExit("PUBLIC_KEY_PATH");
    private static String privateKeyPath = getEnvOrExit("PRIVATE_KEY_PATH");

    private static byte[] getPemContentBytes(String path, String beginDelimiter,
            String endDelimiter) throws IOException {
        String pemContent = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);

        String pemData = pemContent
                .replace(beginDelimiter, "")
                .replace(endDelimiter, "")
                .replaceAll("\\s", ""); // Remove newlines and spaces

        return Base64.getDecoder().decode(pemData);
    }

    public static PublicKey loadPemFormatPublicKey()
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] decodedBytes = getPemContentBytes(publicKeyPath, "-----BEGIN PUBLIC KEY-----",
                "-----END PUBLIC KEY-----");
        X509EncodedKeySpec spec = new X509EncodedKeySpec(decodedBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(spec);
    }

    public static PrivateKey loadPemFormatPrivateKey()
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] decodedBytes = getPemContentBytes(privateKeyPath, "-----BEGIN PRIVATE KEY-----",
                "-----END PRIVATE KEY-----");
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decodedBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(spec);
    }
}
