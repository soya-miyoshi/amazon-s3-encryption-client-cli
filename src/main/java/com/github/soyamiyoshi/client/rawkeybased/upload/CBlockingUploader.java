package com.github.soyamiyoshi.client.rawkeybased.upload;

import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import com.github.soyamiyoshi.util.keyprovider.CPublicKeyProvider;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.encryption.s3.S3AsyncEncryptionClient;
import software.amazon.encryption.s3.materials.PartialRsaKeyPair;

public class CBlockingUploader extends APubKeyBasedClient {

    public CBlockingUploader(CPublicKeyProvider keyProvider) {
        super(keyProvider);
    }

    // Invoked by the KeyBasedClient constructor to initialize mV3AsyncClient.
    @Override
    protected S3AsyncClient createS3AsyncClient() {
        return S3AsyncEncryptionClient.builder()
                .rsaKeyPair(new PartialRsaKeyPair(null, mPublicKeyProvider.getKey()))
                .build();
    }

    @Override
    public void upload(
            final String bucketName,
            final String objectKey,
            final Path localFilePath) {
        AsyncRequestBody asyncRequestBody = null;

        try {
            asyncRequestBody = AsyncRequestBody.fromFile(localFilePath);
        } catch (UncheckedIOException e) {
            System.err.println("Error creating asyncRequestBody");
            System.exit(1);
        }
        CompletableFuture<PutObjectResponse> futurePut =
                this.mV3AsyncClient.putObject(builder -> builder
                        .bucket(bucketName)
                        .key(objectKey)
                        .build(), asyncRequestBody);
        futurePut.join();
    }
}
