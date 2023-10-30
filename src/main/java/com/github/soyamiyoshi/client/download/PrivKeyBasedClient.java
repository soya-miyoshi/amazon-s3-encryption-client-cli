package com.github.soyamiyoshi.client.download;

import static com.github.soyamiyoshi.util.KeyLoader.loadPemFormatPrivateKey;
import java.security.PrivateKey;
import java.util.Optional;
import software.amazon.awssdk.services.s3.S3AsyncClient;

public abstract class PrivKeyBasedClient implements AutoCloseable {
    protected PrivateKey mPrivateKey;
    protected S3AsyncClient mV3AsyncClient;

    protected PrivKeyBasedClient() {
        Optional<PrivateKey> _privateKey = loadPemFormatPrivateKey();
        if (_privateKey.isEmpty()) {
            System.err.println("Error loading private key");
            System.exit(1);
            return;
        }
        this.mPrivateKey = _privateKey.get();

        // Ensure the subclass provides the v3AsyncClient
        this.mV3AsyncClient = this.createS3AsyncClient();
        if (this.mV3AsyncClient == null) {
            throw new IllegalStateException("v3AsyncClient must not be null");
        }
    }

    protected abstract S3AsyncClient createS3AsyncClient();

    public abstract void download(
            final String bucketName,
            final String objectKey);

    @Override
    public void close() {
        System.out.println("Closing the client");
        this.mV3AsyncClient.close();
    }

}

