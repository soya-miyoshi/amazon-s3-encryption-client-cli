package com.github.soyamiyoshi.client.download;

import com.github.soyamiyoshi.util.keyprovider.CPrivateKeyProvider;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.encryption.s3.S3AsyncEncryptionClient;
import software.amazon.encryption.s3.materials.PartialRsaKeyPair;

public class CDelayedAuthenticationDownloader extends CBlockingDownloader {

    public CDelayedAuthenticationDownloader(CPrivateKeyProvider keyProvider) {
        super(keyProvider);
    }

    @Override
    protected S3AsyncClient createS3AsyncClient() {
        return S3AsyncEncryptionClient.builder()
                .rsaKeyPair(new PartialRsaKeyPair(super.mPrivateKeyProvider.getKey(), null))
                .enableDelayedAuthenticationMode(true)
                .build();
    }
}
