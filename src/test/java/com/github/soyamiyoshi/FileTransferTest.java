package com.github.soyamiyoshi;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.github.soyamiyoshi.client.download.DelayedAuthenticationDownloader;
import com.github.soyamiyoshi.client.upload.BlockingUploader;
import static com.github.soyamiyoshi.util.EnvLoader.getEnvOrExit;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileTransferTest {

    private static String TEST_BUCKET_NAME;
    private static String TEST_OBJECT_KEY;
    private static Path TEST_LOCAL_FILE_PATH;

    @BeforeAll
    static void setUp() {
        TEST_BUCKET_NAME = getEnvOrExit("TEST_BUCKET_NAME");
        TEST_OBJECT_KEY = getEnvOrExit("TEST_OBJECT_KEY");
        TEST_LOCAL_FILE_PATH = Paths.get(getEnvOrExit("TEST_LOCAL_FILE_PATH"));
    }

    // Write test that always passes
    @Test
    public void testAlwaysPasses() {
        assert true;
    }

    @Test
    public void testFileTransfer() throws Exception {
        // Initialize the clients
        try (BlockingUploader uploader = new BlockingUploader()) {
            uploader.upload(TEST_BUCKET_NAME, TEST_OBJECT_KEY, TEST_LOCAL_FILE_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        } ;

        try (DelayedAuthenticationDownloader downloader = new DelayedAuthenticationDownloader()) {
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
