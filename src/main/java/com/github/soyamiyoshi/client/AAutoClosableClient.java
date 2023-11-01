package com.github.soyamiyoshi.client;

import software.amazon.awssdk.services.s3.S3AsyncClient;

public abstract class AAutoClosableClient implements AutoCloseable {
    protected S3AsyncClient mV3AsyncClient;

    protected void setV3AsyncClient() {
        this.mV3AsyncClient = createS3AsyncClient();
    }

    protected abstract S3AsyncClient createS3AsyncClient();

    @Override
    public void close() {
        System.out.println("Closing the client");
        this.mV3AsyncClient.close();
    }
}
