package com.github.soyamiyoshi.client.download;

import java.security.PrivateKey;

public abstract class DownloadClient {
    protected PrivateKey privateKey;

    protected DownloadClient(final PrivateKey privateKey) {
        if (privateKey == null) {
            throw new IllegalArgumentException("PublicKey cannot be null");
        }
        this.privateKey = privateKey;
    }

    public abstract void download(
            final String bucketName,
            final String objectKey);
}

