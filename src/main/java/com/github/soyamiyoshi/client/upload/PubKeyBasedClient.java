package com.github.soyamiyoshi.client.upload;

import java.nio.file.Path;
import java.security.PublicKey;

public abstract class PubKeyBasedClient {
    protected PublicKey publicKey;

    protected PubKeyBasedClient(final PublicKey publicKey) {
        if (publicKey == null) {
            throw new IllegalArgumentException("PublicKey cannot be null");
        }
        this.publicKey = publicKey;
    }

    public abstract void upload(
            final String bucketName,
            final String objectKey,
            final Path localFilePath);

}

