package com.github.soyamiyoshi;

import java.nio.file.Path;
import java.nio.file.Paths;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.encryption.s3.S3EncryptionClient;

public class App {

    private static final String BUCKET_NAME =
            System.getenv("BUCKET_NAME");
    private static final String KMS_KEY_ID =
            System.getenv("KMS_KEY_ID");

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println(
                    "Usage: java App <OBJECT_KEY> <FILE_PATH>");
            return;
        }

        String OBJECT_KEY = args[0];
        Path FILE_PATH = Paths.get(args[1]);

        S3Client v3Client =
                S3EncryptionClient
                        .builder()
                        .kmsKeyId(KMS_KEY_ID)
                        .build();

        PutObjectRequest putObjectRequest =
                PutObjectRequest.builder()
                        .bucket(BUCKET_NAME)
                        .key(OBJECT_KEY)
                        .build();

        v3Client.putObject(putObjectRequest,
                RequestBody.fromFile(
                        FILE_PATH));

        v3Client.close();
    }
}
