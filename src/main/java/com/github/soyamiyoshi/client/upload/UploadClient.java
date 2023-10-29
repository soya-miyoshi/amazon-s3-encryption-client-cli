package com.github.soyamiyoshi.client.upload;

import java.nio.file.Path;
import java.security.PublicKey;

public abstract class UploadClient {
    protected PublicKey publicKey;

    protected UploadClient(final PublicKey publicKey) {
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

