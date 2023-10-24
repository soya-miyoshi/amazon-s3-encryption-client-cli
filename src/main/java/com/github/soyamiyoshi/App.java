package com.github.soyamiyoshi;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
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

    private static PublicKey loadPemFormatPublicKey()
            throws Exception {
        String pemContent = new String(
                Files.readAllBytes(Paths.get(
                        PUBLIC_KEY_PATH)),
                StandardCharsets.UTF_8);

        // Remove the first and last lines (the PEM header and footer)
        String publicKeyPEM = pemContent
                .replace(
                        "-----BEGIN PUBLIC KEY-----",
                        "")
                .replace(
                        "-----END PUBLIC KEY-----",
                        "")
                .replaceAll("\\s", ""); // This will remove newlines and spaces

        byte[] decodedBytes =
                Base64.getDecoder().decode(
                        publicKeyPEM);
        X509EncodedKeySpec spec =
                new X509EncodedKeySpec(
                        decodedBytes);
        KeyFactory keyFactory = KeyFactory
                .getInstance("RSA");
        return keyFactory
                .generatePublic(spec);
    }


    // TODO: Handle exceptions
    // private static PublicKey loadPublicKey()
    // throws Exception {
    // try {
    // byte[] keyBytes = Files
    // .readAllBytes(Paths.get(
    // PUBLIC_KEY_PATH));
    // X509EncodedKeySpec spec =
    // new X509EncodedKeySpec(
    // keyBytes);
    // KeyFactory keyFactory =
    // KeyFactory.getInstance(
    // "RSA");
    // PublicKey publicKey = keyFactory
    // .generatePublic(spec);
    // return publicKey;
    // } catch (Exception e) {
    // throw e;
    // }
    // }


    private static void uploadUsingPublicKeyEncryption(
            String objectKey,
            Path filePath) {
        PublicKey publicKey = null;
        try {
            publicKey =
                    loadPemFormatPublicKey();
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
