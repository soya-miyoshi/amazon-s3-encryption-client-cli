package com.github.soyamiyoshi.util.keyprovider;

import static com.github.soyamiyoshi.util.EnvLoader.getEnvOrExit;
import static com.github.soyamiyoshi.util.PemContentUtil.getPemContentBytes;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class CPublicKeyProvider implements IKeyProvider {

    @Override
    public PublicKey getKey() {
        String publicKeyPath = getEnvOrExit("PUBLIC_KEY_PATH");
        try {
            byte[] decodedBytes = getPemContentBytes(publicKeyPath, "-----BEGIN PUBLIC KEY-----",
                    "-----END PUBLIC KEY-----");
            X509EncodedKeySpec spec = new X509EncodedKeySpec(decodedBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(spec);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            System.err.println("Error loading public key: " + e.getMessage());
            System.exit(1);
            return null;
        }
    }

}
