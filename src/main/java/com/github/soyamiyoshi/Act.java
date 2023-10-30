package com.github.soyamiyoshi;

import java.nio.file.Path;
import com.github.soyamiyoshi.client.download.BlockingDownloader;
import com.github.soyamiyoshi.client.download.DelayedAuthenticationDownloader;
import com.github.soyamiyoshi.client.upload.BlockingUploader;

public class Act {

    public static void blockingUpload(final String bucketName, final String objectKey,
            final Path uploadFilePath) {
        try (final BlockingUploader blockUploadClient = new BlockingUploader()) {
            blockUploadClient.upload(bucketName, objectKey, uploadFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void blockingDownload(final String bucketName, final String objectKey) {
        try (final BlockingDownloader blockDownloadClient = new BlockingDownloader()) {
            blockDownloadClient.download(bucketName, objectKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void blockingDownloadMorethan64MBdata(final String bucketName,
            final String objectKey) {
        try (final DelayedAuthenticationDownloader blockDownloadClient =
                new DelayedAuthenticationDownloader()) {
            blockDownloadClient.download(bucketName, objectKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
