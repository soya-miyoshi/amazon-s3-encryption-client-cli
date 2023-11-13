package com.github.soyamiyoshi;

import static com.github.soyamiyoshi.Act.blockingDownload;
import static com.github.soyamiyoshi.Act.blockingDownloadKmsKey;
import static com.github.soyamiyoshi.Act.blockingDownloadMorethan64MBdata;
import static com.github.soyamiyoshi.Act.blockingUpload;
import static com.github.soyamiyoshi.Act.blockingUploadKmsKey;
import java.nio.file.Path;
import com.github.soyamiyoshi.cli.ArgsParser;
import com.github.soyamiyoshi.cli.CommandLineArgs;

public class App {
    public static void main(String[] args) {
        CommandLineArgs cliArgs = ArgsParser.parse(args);

        if (cliArgs.isUpload()) {
            // output as a log
            System.out.println("Uploading " + cliArgs.getLocalFilePath() + " to "
                    + cliArgs.getObjectKey() + "...");

            if (cliArgs.getKmsKeyId() != null) {
                System.out.println("Uploading with KMS key...");
                blockingUploadKmsKey(cliArgs.getBucketName(), cliArgs.getObjectKey(),
                        Path.of(cliArgs.getLocalFilePath()), cliArgs.getKmsKeyId());
            } else {
                System.out.println("Uploading with public key...");
                blockingUpload(cliArgs.getBucketName(), cliArgs.getObjectKey(),
                        Path.of(cliArgs.getLocalFilePath()), cliArgs.getPublicKeyPath());
            }
            // output as a log
            System.out.println("Upload completed.");
        }

        if (cliArgs.isDownload()) {
            String bucketName = cliArgs.getBucketName();
            // output as a log
            System.out.println("Downloading " + cliArgs.getObjectKey() + "...");

            if (cliArgs.getKmsKeyId() != null) {
                System.out.println("Downloading with KMS key...");
                blockingDownloadKmsKey(cliArgs.getBucketName(), cliArgs.getObjectKey(),
                        cliArgs.getKmsKeyId());
            } else {
                if (cliArgs.isDownloadMorethan64MBdata()) {
                    System.out.println("Downloading more than 64MB data...");
                    blockingDownloadMorethan64MBdata(bucketName,
                            cliArgs.getObjectKey(), cliArgs.getPrivateKeyPath());
                } else {
                    System.out.println("Downloading with private key...");
                    blockingDownload(bucketName, cliArgs.getObjectKey(),
                            cliArgs.getPrivateKeyPath());
                }
            }
            System.out.println("Download completed.");
        }
        return;
    }
}
