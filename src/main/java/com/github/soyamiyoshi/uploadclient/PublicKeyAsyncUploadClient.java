package com.github.soyamiyoshi.uploadclient;

import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.security.PublicKey;
import java.util.concurrent.CompletableFuture;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.encryption.s3.S3AsyncEncryptionClient;
import software.amazon.encryption.s3.materials.PartialRsaKeyPair;

public class PublicKeyAsyncUploadClient extends UploadClient {

    public PublicKeyAsyncUploadClient(PublicKey publicKey) {
        super(publicKey);
    }

    @Override
    public void upload(
            final String bucketName,
            final String objectKey,
            final Path localFilePath) {

        S3AsyncClient v3AsyncClient = S3AsyncEncryptionClient.builder()
                .rsaKeyPair(new PartialRsaKeyPair(null, publicKey))
                .build();

        AsyncRequestBody asyncRequestBody = null;

        try {
            asyncRequestBody = AsyncRequestBody.fromFile(localFilePath);
        } catch (UncheckedIOException e) {
            System.err.println("Error creating asyncRequestBody");
            System.exit(1);
        }
        CompletableFuture<PutObjectResponse> futurePut =
                v3AsyncClient.putObject(builder -> builder
                        .bucket(bucketName)
                        .key(objectKey)
                        .build(), asyncRequestBody);
        futurePut.join();
        v3AsyncClient.close();
    }
}
