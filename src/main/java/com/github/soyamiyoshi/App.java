package com.github.soyamiyoshi;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.encryption.s3.S3EncryptionClient;
import software.amazon.encryption.s3.materials.PartialRsaKeyPair;

public class App {

    private static final String BUCKET_NAME =
            System.getenv("BUCKET_NAME");
    private static final String PUBLIC_KEY_PATH =
            System.getenv("PUBLIC_KEY_PATH");

    // TODO: Handle exceptions
    private static PublicKey loadPublicKey()
            throws Exception {
        try {
            // Read the public key bytes
            byte[] keyBytes = Files
                    .readAllBytes(Paths.get(
                            PUBLIC_KEY_PATH));

            // Convert the public key bytes to a PublicKey object
            X509EncodedKeySpec spec =
                    new X509EncodedKeySpec(
                            keyBytes);
            KeyFactory keyFactory =
                    KeyFactory.getInstance(
                            "RSA"); // Assuming it's an RSA key
            PublicKey publicKey = keyFactory
                    .generatePublic(spec);

            // Now you can use the publicKey object
            System.out.println("Public Key: "
                    + publicKey);
            return publicKey;
        } catch (Exception e) {
            throw e;
        }
    }

    private static void uploadUsingPublicKeyEncryption(
            String objectKey,
            Path filePath) {
        PublicKey publicKey = null;
        try {
            publicKey = loadPublicKey();
        } catch (Exception e) {
            System.out.println(
                    "failed at loadPublicKey()");
            System.exit(1);
        }

        S3Client v3Client =
                S3EncryptionClient.builder()
                        .rsaKeyPair(
                                new PartialRsaKeyPair(
                                        null,
                                        publicKey))
                        .build();

        PutObjectRequest putObjectRequest =
                PutObjectRequest.builder()
                        .bucket(BUCKET_NAME)
                        .key(objectKey)
                        .build();

        v3Client.putObject(putObjectRequest,
                RequestBody
                        .fromFile(filePath));
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println(
                    "Usage: java App <OBJECT_KEY> <FILE_PATH>");
            return;
        }
        String OBJECT_KEY = args[0];
        Path FILE_PATH = Paths.get(args[1]);
        uploadUsingPublicKeyEncryption(
                OBJECT_KEY, FILE_PATH);
    }
}
