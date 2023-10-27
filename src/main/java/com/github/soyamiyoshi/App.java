package com.github.soyamiyoshi;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import com.github.soyamiyoshi.uploadclient.PublicKeyAsyncUploadClient;
import static com.github.soyamiyoshi.util.KeyLoader.loadPemFormatPublicKey;

public class App {

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println(
                    "Usage: java App <BUCKET_NAME> <OBJECT_KEY> <FILE_PATH>");
            return;
        }

        String BUCKET_NAME = args[0];
        String OBJECT_KEY = args[1];
        Path FILE_PATH = Paths.get(args[2]);

        PublicKey publicKey = null;
        try {
            publicKey = loadPemFormatPublicKey();
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            System.err.println("Error loading public key: " + e.getMessage());
            System.exit(1);
            return;
        }

        PublicKeyAsyncUploadClient publicKeyAsyncUploadClient =
                new PublicKeyAsyncUploadClient(publicKey);

        publicKeyAsyncUploadClient.upload(BUCKET_NAME, OBJECT_KEY, FILE_PATH);
    }

}
