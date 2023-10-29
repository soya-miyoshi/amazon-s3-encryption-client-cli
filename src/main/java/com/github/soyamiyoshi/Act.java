package com.github.soyamiyoshi;

import static com.github.soyamiyoshi.util.KeyLoader.loadPemFormatPublicKey;
import static com.github.soyamiyoshi.util.KeyLoader.loadPemFormatPrivateKey;
import java.nio.file.Path;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Optional;
import com.github.soyamiyoshi.client.download.PrivateKeyAsyncDownloadClient;
import com.github.soyamiyoshi.client.upload.PublicKeyAsyncUploadClient;

public class Act {

    public static void uploadAsync(final String bucketName, final String objectKey,
            final Path uploadFilePath) {
        Optional<PublicKey> publicKey = loadPemFormatPublicKey();
        if (publicKey.isEmpty()) {
            System.err.println("Error loading public key");
            System.exit(1);
            return;
        }
        PublicKeyAsyncUploadClient publicKeyAsyncUploadClient =
                new PublicKeyAsyncUploadClient(publicKey.get());

        publicKeyAsyncUploadClient.upload(bucketName, objectKey, uploadFilePath);
    }

    public static void downloadAsync(final String bucketName, final String objectKey) {
        Optional<PrivateKey> privateKey = loadPemFormatPrivateKey();
        if (privateKey.isEmpty()) {
            System.err.println("Error loading private key");
            System.exit(1);
            return;
        }
        PrivateKeyAsyncDownloadClient privateKeyAsyncDownloadClient =
                new PrivateKeyAsyncDownloadClient(privateKey.get());

        privateKeyAsyncDownloadClient.download(bucketName, objectKey);
    }
}
