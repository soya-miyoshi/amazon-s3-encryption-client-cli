package com.github.soyamiyoshi;

import static com.github.soyamiyoshi.util.KeyLoader.loadPemFormatPublicKey;
import java.nio.file.Path;
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
        try (final BlockingDownloader blockDownloadClient = new BlockingDownloader()) {
            blockDownloadClient.download(bucketName, objectKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void blockingDownloadMorethan64MBdata() {}
}
