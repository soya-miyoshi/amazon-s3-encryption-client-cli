package com.github.soyamiyoshi.util.keyprovider;

import static com.github.soyamiyoshi.util.PemContentUtil.getPemContentBytes;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

public class CPrivateKeyProvider implements IKeyProvider {

    private String privateKeyPath;

    public CPrivateKeyProvider(final String privateKeyPath) {
        this.privateKeyPath = privateKeyPath;
    }

    @Override
    public PrivateKey getKey() {
        try {
            byte[] decodedBytes =
                    getPemContentBytes(this.privateKeyPath, "-----BEGIN PRIVATE KEY-----",
                            "-----END PRIVATE KEY-----");
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decodedBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(spec);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            System.err.println("Error loading private key: " + e.getMessage());
            return null;
        }
    }
}
