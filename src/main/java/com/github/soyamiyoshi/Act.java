package com.github.soyamiyoshi;

import static com.github.soyamiyoshi.util.KeyLoader.loadPemFormatPublicKey;
import static com.github.soyamiyoshi.util.KeyLoader.loadPemFormatPrivateKey;
import java.nio.file.Path;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Optional;
import com.github.soyamiyoshi.client.download.BlockingDownloader;
import com.github.soyamiyoshi.client.upload.BlockingUploader;

public class Act {

    public static void blockingUpload(final String bucketName, final String objectKey,
            final Path uploadFilePath) {
        Optional<PublicKey> publicKey = loadPemFormatPublicKey();
        if (publicKey.isEmpty()) {
            System.err.println("Error loading public key");
            System.exit(1);
            return;
        }
        BlockingUploader blockUploadClient =
                new BlockingUploader(publicKey.get());

        blockUploadClient.upload(bucketName, objectKey, uploadFilePath);
    }

    public static void blockingDownload(final String bucketName, final String objectKey) {
        Optional<PrivateKey> privateKey = loadPemFormatPrivateKey();
        if (privateKey.isEmpty()) {
            System.err.println("Error loading private key");
            System.exit(1);
            return;
        }
        BlockingDownloader blockDownloadClient =
                new BlockingDownloader(privateKey.get());

        blockDownloadClient.download(bucketName, objectKey);
    }
}
