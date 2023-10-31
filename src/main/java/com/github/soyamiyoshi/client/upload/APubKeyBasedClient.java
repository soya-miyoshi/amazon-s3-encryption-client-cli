package com.github.soyamiyoshi.client.upload;

import java.nio.file.Path;
import com.github.soyamiyoshi.client.AKeyBasedClient;
import com.github.soyamiyoshi.util.keyprovider.IKeyProvider;
import software.amazon.awssdk.services.s3.S3AsyncClient;

public abstract class APubKeyBasedClient extends AKeyBasedClient {

    protected APubKeyBasedClient(IKeyProvider keyProvider) {
        super(keyProvider);
    }

    protected abstract S3AsyncClient createS3AsyncClient();

    public abstract void upload(
            final String bucketName,
            final String objectKey,
            final Path localFilePath);

}

