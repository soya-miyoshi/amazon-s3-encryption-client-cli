package com.github.soyamiyoshi.client.upload;

import java.nio.file.Path;
import com.github.soyamiyoshi.client.AAutoClosableClient;
import com.github.soyamiyoshi.util.keyprovider.CPublicKeyProvider;
import software.amazon.awssdk.services.s3.S3AsyncClient;

public abstract class APubKeyBasedClient extends AAutoClosableClient {

    protected CPublicKeyProvider mPublicKeyProvider;

    protected APubKeyBasedClient(CPublicKeyProvider publicKeyProvider) {
        if (publicKeyProvider == null) {
            throw new IllegalArgumentException("publicKeyProvider must not be null");
        }
        this.mPublicKeyProvider = publicKeyProvider;
        this.setV3AsyncClient();
    }

    protected abstract S3AsyncClient createS3AsyncClient();

    public abstract void upload(
            final String bucketName,
            final String objectKey,
            final Path localFilePath);

}

