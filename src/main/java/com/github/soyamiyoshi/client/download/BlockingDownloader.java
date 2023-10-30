package com.github.soyamiyoshi.client.download;

import java.security.PrivateKey;
import java.util.concurrent.CompletableFuture;
import static com.github.soyamiyoshi.util.ObjectSaver.saveToFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.async.AsyncResponseTransformer;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.encryption.s3.S3AsyncEncryptionClient;
import software.amazon.encryption.s3.materials.PartialRsaKeyPair;

public class PrivateKeyAsyncDownloadClient extends DownloadClient {

    public PrivateKeyAsyncDownloadClient(PrivateKey privateKey) {
        super(privateKey);
    }

    @Override
    public void download(
            final String bucketName,
            final String objectKey) {

        S3AsyncClient v3AsyncClient = S3AsyncEncryptionClient.builder()
                .rsaKeyPair(new PartialRsaKeyPair(privateKey, null))
                .build();

        CompletableFuture<ResponseBytes<GetObjectResponse>> futureGet =
                v3AsyncClient.getObject(builder -> builder
                        .bucket(bucketName)
                        .key(objectKey)
                        .build(), AsyncResponseTransformer.toBytes());

        ResponseBytes<GetObjectResponse> getResponse = futureGet.join();

        saveToFile(getResponse.asInputStream(), objectKey);

        v3AsyncClient.close();

    }

}
