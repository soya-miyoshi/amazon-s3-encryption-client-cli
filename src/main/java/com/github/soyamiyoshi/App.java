package com.github.soyamiyoshi;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PublicKey;
import java.util.Optional;
import com.github.soyamiyoshi.uploadclient.PublicKeyAsyncUploadClient;
import static com.github.soyamiyoshi.util.EnvLoader.getEnvOrExit;
import static com.github.soyamiyoshi.util.KeyLoader.loadPemFormatPublicKey;

public class App {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println(
                    "Usage: java App <OBJECT_KEY> <UPLOAD_FILE_PATH>");
            return;
        }

        String bucketName = getEnvOrExit("BUCKET_NAME");
        String objectKey = args[0];
        Path uploadFilePath = Paths.get(args[1]);

        Optional<PublicKey> publicKey = loadPemFormatPublicKey();
        if (publicKey.isEmpty()) {
            System.err.println("Error loading public key");
            System.exit(1);
            return;
        }

        PublicKeyAsyncUploadClient publicKeyAsyncUploadClient =
                new PublicKeyAsyncUploadClient(publicKey.get());

        publicKeyAsyncUploadClient.upload(bucketName, objectKey, uploadFilePath);
    }

}
