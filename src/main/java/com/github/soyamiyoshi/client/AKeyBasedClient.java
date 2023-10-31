package com.github.soyamiyoshi.client;

import com.github.soyamiyoshi.util.keyprovider.IKeyProvider;
import software.amazon.awssdk.services.s3.S3AsyncClient;

public abstract class AKeyBasedClient implements AutoCloseable {
    protected S3AsyncClient mV3AsyncClient;
    protected IKeyProvider mKeyProvider;

    protected AKeyBasedClient(final IKeyProvider keyProvider) {

        if (keyProvider == null) {
            throw new IllegalArgumentException("keyProvider must not be null");
        }
        this.mKeyProvider = keyProvider;

        this.mV3AsyncClient = this.createS3AsyncClient();
        if (this.mV3AsyncClient == null) {
            throw new IllegalStateException("v3AsyncClient must not be null");
        }
    }

    protected abstract S3AsyncClient createS3AsyncClient();

    @Override
    public void close() {
        System.out.println("Closing the client");
        this.mV3AsyncClient.close();
    }
}
