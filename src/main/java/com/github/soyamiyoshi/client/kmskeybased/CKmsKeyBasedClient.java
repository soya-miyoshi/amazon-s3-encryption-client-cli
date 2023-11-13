package com.github.soyamiyoshi.client.kmskeybased;

import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import com.github.soyamiyoshi.client.AAutoClosableClient;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.core.async.AsyncResponseTransformer;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.encryption.s3.S3AsyncEncryptionClient;

public class CKmsKeyBasedClient extends AAutoClosableClient {

    protected String mKmsKeyID;

    public CKmsKeyBasedClient(final String kmsKeyId) {
        if (kmsKeyId == null) {
            throw new IllegalArgumentException("kmsKeyId must not be null");
        }
        this.mKmsKeyID = kmsKeyId;
        super.setV3AsyncClient();
    }

    @Override
    protected S3AsyncClient createS3AsyncClient() {
        return S3AsyncEncryptionClient.builder()
                .kmsKeyId(this.mKmsKeyID)
                .build();
    }

    public void blockingUpload(
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

    public void blockingDownload(
            final String bucketName,
            final String objectKey) {

        CompletableFuture<GetObjectResponse> futureGet =
                mV3AsyncClient.getObject(builder -> builder
                        .bucket(bucketName)
                        .key(objectKey)
                        .build(), AsyncResponseTransformer.toFile(Path.of(objectKey)));
        futureGet.join();
    }

}
