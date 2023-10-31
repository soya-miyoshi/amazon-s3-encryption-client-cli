package com.github.soyamiyoshi.client.download;

import java.security.PrivateKey;
import java.util.concurrent.CompletableFuture;
import com.github.soyamiyoshi.util.keyprovider.IKeyProvider;
import static com.github.soyamiyoshi.util.ObjectSaver.saveToFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.async.AsyncResponseTransformer;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.encryption.s3.S3AsyncEncryptionClient;
import software.amazon.encryption.s3.materials.PartialRsaKeyPair;

public class CBlockingDownloader extends APrivKeyBasedClient {

    public CBlockingDownloader(IKeyProvider keyProvider) {
        super(keyProvider);
    }

    // Invoked by the KeyBasedClient constructor to initialize mV3AsyncClient.
    @Override
    protected S3AsyncClient createS3AsyncClient() {
        return S3AsyncEncryptionClient.builder()
                .rsaKeyPair(new PartialRsaKeyPair((PrivateKey) this.mKeyProvider.getKey(), null))
                .build();
    }

    @Override
    public void download(
            final String bucketName,
            final String objectKey) {

        CompletableFuture<ResponseBytes<GetObjectResponse>> futureGet =
                mV3AsyncClient.getObject(builder -> builder
                        .bucket(bucketName)
                        .key(objectKey)
                        .build(), AsyncResponseTransformer.toBytes());

        ResponseBytes<GetObjectResponse> getResponse = futureGet.join();

        saveToFile(getResponse.asInputStream(), objectKey);
    }
}
