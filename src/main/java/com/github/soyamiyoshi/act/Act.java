package com.github.soyamiyoshi.act;

import static com.github.soyamiyoshi.util.KeyLoader.loadPemFormatPublicKey;
import java.nio.file.Path;
import java.security.PublicKey;
import java.util.Optional;
import com.github.soyamiyoshi.uploadclient.PublicKeyAsyncUploadClient;

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
}
