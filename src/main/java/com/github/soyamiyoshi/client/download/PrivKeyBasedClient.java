package com.github.soyamiyoshi.client.download;

import static com.github.soyamiyoshi.util.KeyLoader.loadPemFormatPrivateKey;
import java.security.PrivateKey;
import java.util.Optional;
import com.github.soyamiyoshi.client.KeyBasedClient;
import software.amazon.awssdk.services.s3.S3AsyncClient;

public abstract class PrivKeyBasedClient extends KeyBasedClient {
    @Override
    protected PrivateKey setKey() {
        Optional<PrivateKey> _privateKey = loadPemFormatPrivateKey();
        if (_privateKey.isEmpty()) {
            System.err.println("Error loading private key");
            System.exit(1);
        }
        return _privateKey.get();
    }

    protected abstract S3AsyncClient createS3AsyncClient();

    public abstract void download(
            final String bucketName,
            final String objectKey);

}

