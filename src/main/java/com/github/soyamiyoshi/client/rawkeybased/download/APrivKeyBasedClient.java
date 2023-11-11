package com.github.soyamiyoshi.client.rawkeybased.download;

import com.github.soyamiyoshi.client.AAutoClosableClient;
import com.github.soyamiyoshi.util.keyprovider.CPrivateKeyProvider;
import software.amazon.awssdk.services.s3.S3AsyncClient;

public abstract class APrivKeyBasedClient extends AAutoClosableClient {

    protected CPrivateKeyProvider mPrivateKeyProvider;

    protected APrivKeyBasedClient(final CPrivateKeyProvider keyProvider) {
        if (keyProvider == null) {
            throw new IllegalArgumentException("keyProvider must not be null");
        }
        this.mPrivateKeyProvider = keyProvider;
        super.setV3AsyncClient();
    }

    protected abstract S3AsyncClient createS3AsyncClient();

    public abstract void download(
            final String bucketName,
            final String objectKey);

}

