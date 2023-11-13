package com.github.soyamiyoshi;

import java.nio.file.Path;
import com.github.soyamiyoshi.client.kmskeybased.CKmsKeyBasedClient;
import com.github.soyamiyoshi.client.rawkeybased.download.CBlockingDownloader;
import com.github.soyamiyoshi.client.rawkeybased.download.CDelayedAuthenticationDownloader;
import com.github.soyamiyoshi.client.rawkeybased.upload.CBlockingUploader;
import com.github.soyamiyoshi.util.keyprovider.CPrivateKeyProvider;
import com.github.soyamiyoshi.util.keyprovider.CPublicKeyProvider;

public class Act {

    public static void blockingUpload(final String bucketName, final String objectKey,
            final Path uploadFilePath, final String pulicKeyPath) {
        try (final CBlockingUploader blockUploadClient =
                new CBlockingUploader(new CPublicKeyProvider(pulicKeyPath))) {
            blockUploadClient.upload(bucketName, objectKey, uploadFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void blockingDownload(final String bucketName, final String objectKey,
            final String privateKeyPath) {
        try (final CBlockingDownloader blockDownloadClient =
                new CBlockingDownloader(new CPrivateKeyProvider(privateKeyPath))) {
            blockDownloadClient.download(bucketName, objectKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void blockingDownloadMorethan64MBdata(final String bucketName,
            final String objectKey, final String privateKeyPath) {
        try (final CDelayedAuthenticationDownloader blockDownloadClient =
                new CDelayedAuthenticationDownloader(new CPrivateKeyProvider(privateKeyPath))) {
            blockDownloadClient.download(bucketName, objectKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void blockingUploadKmsKey(final String bucketName, final String objectKey,
            final Path uploadFilePath, final String kmsKeyArn) {
        try (final CKmsKeyBasedClient blockUploadClient =
                new CKmsKeyBasedClient(kmsKeyArn)) {
            blockUploadClient.blockingUpload(bucketName, objectKey, uploadFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void blockingDownloadKmsKey(final String bucketName, final String objectKey,
            final String kmsKeyArn) {
        try (final CKmsKeyBasedClient blockDownloadClient =
                new CKmsKeyBasedClient(kmsKeyArn)) {
            blockDownloadClient.blockingDownload(bucketName, objectKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
