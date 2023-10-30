package com.github.soyamiyoshi.client;

import java.security.Key;
import software.amazon.awssdk.services.s3.S3AsyncClient;

public abstract class KeyBasedClient implements AutoCloseable {
    protected Key mKey;
    protected S3AsyncClient mV3AsyncClient;

    protected KeyBasedClient() {
        // Ensure the subclass provides the key
        // Initialize the key before creating the v3AsyncClient
        this.mKey = this.setKey();

        // Ensure the subclass provides the v3AsyncClient
        this.mV3AsyncClient = this.createS3AsyncClient();
        if (this.mV3AsyncClient == null) {
            throw new IllegalStateException("v3AsyncClient must not be null");
        }
    }

    protected abstract Key setKey();

    protected abstract S3AsyncClient createS3AsyncClient();

    @Override
    public void close() {
        System.out.println("Closing the client");
        this.mV3AsyncClient.close();
    }
}
