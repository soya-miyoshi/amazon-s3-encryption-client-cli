package com.github.soyamiyoshi;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PublicKey;
import java.util.Optional;
import com.github.soyamiyoshi.uploadclient.PublicKeyAsyncUploadClient;
import static com.github.soyamiyoshi.util.KeyLoader.loadPemFormatPublicKey;

public class App {

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println(
                    "Usage: java App <BUCKET_NAME> <OBJECT_KEY> <UPLOAD_FILE_PATH>");
            return;
        }

        String bucketName = args[0];
        String objectKey = args[1];
        Path uploadFilePath = Paths.get(args[2]);

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
