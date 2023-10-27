package com.github.soyamiyoshi.uploadclient;

import java.nio.file.Path;
import java.security.PrivateKey;
import java.security.PublicKey;

public abstract class UploadClient {
    protected PublicKey publicKey;
    protected PrivateKey privateKey;

    protected UploadClient(final PublicKey publicKey) {
        if (publicKey == null) {
            throw new IllegalArgumentException("PublicKey cannot be null");
        }
        this.publicKey = publicKey;
    }

    protected UploadClient(final PrivateKey privateKey) {
        if (privateKey == null) {
            throw new IllegalArgumentException("PrivateKey cannot be null");
        }
        this.privateKey = privateKey;
    }

    public abstract void upload(
            final String bucketName,
            final String objectKey,
            final Path localFilePath);

}

