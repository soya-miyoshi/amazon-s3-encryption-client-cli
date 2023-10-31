package com.github.soyamiyoshi;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.github.soyamiyoshi.client.download.CDelayedAuthenticationDownloader;
import com.github.soyamiyoshi.client.upload.BlockingUploader;
import com.github.soyamiyoshi.util.keyprovider.IKeyProvider;
import static com.github.soyamiyoshi.util.EnvLoader.getEnvOrExit;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class FileTransferTest {

    private static String TEST_BUCKET_NAME;
    private static String TEST_OBJECT_KEY;
    private static Path TEST_LOCAL_FILE_PATH;
    private static KeyPair keyPair;

    @BeforeAll
    static void setUp() {
        TEST_BUCKET_NAME = getEnvOrExit("BUCKET_NAME");
        TEST_OBJECT_KEY = getEnvOrExit("OBJECT_KEY");
        TEST_LOCAL_FILE_PATH = Paths.get(getEnvOrExit("LOCAL_FILE_PATH"));
        try {
            keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            // signal that test failed to initialize
            assert false;
        }
    }

    class CMockPublicKeyProvider implements IKeyProvider {
        public PublicKey getKey() {
            return keyPair.getPublic();
        }
    }

    class CMockPrivateKeyProvider implements IKeyProvider {
        public PrivateKey getKey() {
            return keyPair.getPrivate();
        }
    }

    @Test
    public void testFileTransfer() throws Exception {
        // Initialize the clients
        try (BlockingUploader uploader = new BlockingUploader(new CMockPublicKeyProvider())) {
            uploader.upload(TEST_BUCKET_NAME, TEST_OBJECT_KEY, TEST_LOCAL_FILE_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        } ;

        try (CDelayedAuthenticationDownloader downloader =
                new CDelayedAuthenticationDownloader(new CMockPrivateKeyProvider())) {
            downloader.download(TEST_BUCKET_NAME, TEST_OBJECT_KEY);
        } catch (Exception e) {
            e.printStackTrace();
        } ;

        // Compare the contents
        byte[] originalContent = Files.readAllBytes(TEST_LOCAL_FILE_PATH);
        byte[] downloadedContent = Files.readAllBytes(Paths.get(TEST_OBJECT_KEY));

        assertArrayEquals(originalContent, downloadedContent,
                "The contents of the uploaded and downloaded files should be the same.");
    }
}