package com.github.soyamiyoshi;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.encryption.s3.S3EncryptionClient;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        S3Client v3Client = S3EncryptionClient.builder().kmsKeyId(kmsKeyId).build();
        v3Client.putObject(null, null);
        v3Client.close();
        System.out.println("Hello World!");
    }
}
