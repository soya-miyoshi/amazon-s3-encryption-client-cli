package com.github.soyamiyoshi.client.upload;

import static com.github.soyamiyoshi.util.KeyLoader.loadPemFormatPublicKey;
import java.nio.file.Path;
import java.security.PublicKey;
import java.util.Optional;
import com.github.soyamiyoshi.client.KeyBasedClient;
import software.amazon.awssdk.services.s3.S3AsyncClient;

public abstract class PubKeyBasedClient extends KeyBasedClient {
    @Override
    protected PublicKey setKey() {
        Optional<PublicKey> _publicKey = loadPemFormatPublicKey();
        if (_publicKey.isEmpty()) {
            System.err.println("Error loading public key");
            System.exit(1);
        }
        return _publicKey.get();
    }

    protected abstract S3AsyncClient createS3AsyncClient();

    public abstract void upload(
            final String bucketName,
            final String objectKey,
            final Path localFilePath);

}

