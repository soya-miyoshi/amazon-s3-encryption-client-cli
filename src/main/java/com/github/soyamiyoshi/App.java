package com.github.soyamiyoshi;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.encryption.s3.S3AsyncEncryptionClient;
import software.amazon.encryption.s3.materials.PartialRsaKeyPair;

public class App {

    private static final String BUCKET_NAME = System.getenv("BUCKET_NAME");
    private static final String PUBLIC_KEY_PATH = System.getenv("PUBLIC_KEY_PATH");

    private static PublicKey loadPemFormatPublicKey()
            throws Exception {
        String pemContent = new String(
                Files.readAllBytes(Paths.get(
                        PUBLIC_KEY_PATH)),
                StandardCharsets.UTF_8);

        String publicKeyPEM = pemContent
                .replace(
                        "-----BEGIN PUBLIC KEY-----",
                        "")
                .replace(
                        "-----END PUBLIC KEY-----",
                        "")
                .replaceAll("\\s", ""); // This will remove newlines and spaces

        byte[] decodedBytes = Base64.getDecoder().decode(
                publicKeyPEM);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(
                decodedBytes);
        KeyFactory keyFactory = KeyFactory
                .getInstance("RSA");
        return keyFactory
                .generatePublic(spec);
    }

    public static void uploadAsyncUsingPublicKey(
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

        S3AsyncClient v3AsyncClient = S3AsyncEncryptionClient.builder()
                .rsaKeyPair(new PartialRsaKeyPair(null, publicKey))
                .build();

        CompletableFuture<PutObjectResponse> futurePut =
                v3AsyncClient.putObject(builder -> builder
                        .bucket(BUCKET_NAME)
                        .key(objectKey)
                        .build(), AsyncRequestBody.fromFile(filePath));
        futurePut.join();

        v3AsyncClient.close();
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println(
                    "Usage: java App <OBJECT_KEY> <FILE_PATH>");
            return;
        }

        String OBJECT_KEY = args[0];
        Path FILE_PATH = Paths.get(args[1]);

        uploadAsyncUsingPublicKey(OBJECT_KEY, FILE_PATH);
    }

}
