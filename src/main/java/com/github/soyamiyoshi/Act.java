package com.github.soyamiyoshi;

import java.nio.file.Path;
import com.github.soyamiyoshi.client.download.CBlockingDownloader;
import com.github.soyamiyoshi.client.download.CDelayedAuthenticationDownloader;
import com.github.soyamiyoshi.client.upload.BlockingUploader;
import com.github.soyamiyoshi.util.keyprovider.CPublicKeyProvider;

public class Act {

    public static void blockingUpload(final String bucketName, final String objectKey,
            final Path uploadFilePath) {
        try (final BlockingUploader blockUploadClient =
                new BlockingUploader(new CPublicKeyProvider())) {
            blockUploadClient.upload(bucketName, objectKey, uploadFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void blockingDownload(final String bucketName, final String objectKey) {
        try (final CBlockingDownloader blockDownloadClient =
                new CBlockingDownloader(new CPublicKeyProvider())) {
            blockDownloadClient.download(bucketName, objectKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void blockingDownloadMorethan64MBdata(final String bucketName,
            final String objectKey) {
        try (final CDelayedAuthenticationDownloader blockDownloadClient =
                new CDelayedAuthenticationDownloader(new CPublicKeyProvider())) {
            blockDownloadClient.download(bucketName, objectKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
