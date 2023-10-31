package com.github.soyamiyoshi.client.download;

import com.github.soyamiyoshi.client.AKeyBasedClient;
import com.github.soyamiyoshi.util.keyprovider.IKeyProvider;
import software.amazon.awssdk.services.s3.S3AsyncClient;

public abstract class APrivKeyBasedClient extends AKeyBasedClient {

    protected APrivKeyBasedClient(IKeyProvider keyProvider) {
        super(keyProvider);
    }

    protected abstract S3AsyncClient createS3AsyncClient();

    public abstract void download(
            final String bucketName,
            final String objectKey);

}

