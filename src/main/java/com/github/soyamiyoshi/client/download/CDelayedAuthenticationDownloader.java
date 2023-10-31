package com.github.soyamiyoshi.client.download;

import java.security.PrivateKey;
import com.github.soyamiyoshi.util.keyprovider.IKeyProvider;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.encryption.s3.S3AsyncEncryptionClient;
import software.amazon.encryption.s3.materials.PartialRsaKeyPair;

public class CDelayedAuthenticationDownloader extends CBlockingDownloader {

    public CDelayedAuthenticationDownloader(IKeyProvider keyProvider) {
        super(keyProvider);
    }

    @Override
    protected S3AsyncClient createS3AsyncClient() {
        return S3AsyncEncryptionClient.builder()
                .rsaKeyPair(new PartialRsaKeyPair((PrivateKey) this.mKeyProvider.getKey(), null))
                .enableDelayedAuthenticationMode(true)
                .build();
    }
}
