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
import java.util.Optional;
import static com.github.soyamiyoshi.util.EnvLoader.getEnvOrExit;

public class KeyLoader {

    private static byte[] getPemContentBytes(String path, String beginDelimiter,
            String endDelimiter) throws IOException {
        String pemContent = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);

        String pemData = pemContent
                .replace(beginDelimiter, "")
                .replace(endDelimiter, "")
                .replaceAll("\\s", ""); // Remove newlines and spaces

        return Base64.getDecoder().decode(pemData);
    }

    public static Optional<PrivateKey> loadPemFormatPrivateKey() {
        String privateKeyPath = getEnvOrExit("PRIVATE_KEY_PATH");
        try {
            byte[] decodedBytes = getPemContentBytes(privateKeyPath, "-----BEGIN PRIVATE KEY-----",
                    "-----END PRIVATE KEY-----");
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decodedBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return Optional.of(keyFactory.generatePrivate(spec));
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            System.err.println("Error loading private key: " + e.getMessage());
            return Optional.empty();
        }
    }

    public static Optional<PublicKey> loadPemFormatPublicKey() {
        String publicKeyPath = getEnvOrExit("PUBLIC_KEY_PATH");
        try {
            byte[] decodedBytes = getPemContentBytes(publicKeyPath, "-----BEGIN PUBLIC KEY-----",
                    "-----END PUBLIC KEY-----");
            X509EncodedKeySpec spec = new X509EncodedKeySpec(decodedBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return Optional.of(keyFactory.generatePublic(spec));
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            System.err.println("Error loading public key: " + e.getMessage());
            return Optional.empty();
        }
    }
}
