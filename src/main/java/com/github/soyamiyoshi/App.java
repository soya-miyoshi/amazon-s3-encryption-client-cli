package com.github.soyamiyoshi;

import static com.github.soyamiyoshi.Act.blockingDownload;
import static com.github.soyamiyoshi.Act.blockingUpload;
import static com.github.soyamiyoshi.util.EnvLoader.getEnvOrExit;
import java.nio.file.Path;
import com.github.soyamiyoshi.cli.ArgsParser;
import com.github.soyamiyoshi.cli.CommandLineArgs;

public class App {
    public static void main(String[] args) {
        CommandLineArgs cliArgs = ArgsParser.parse(args);

        if (cliArgs.isUpload()) {
            // output as a log
            System.out.println("Uploading " + cliArgs.getLocalFilePath() + " to "
                    + cliArgs.getObjectKey() + "...");
            blockingUpload(getEnvOrExit("BUCKET_NAME"), cliArgs.getObjectKey(),
                    Path.of(cliArgs.getLocalFilePath()));
            // output as a log
            System.out.println("Upload completed.");
        }
        if (cliArgs.isDownload()) {
            // output as a log
            System.out.println("Downloading " + cliArgs.getObjectKey() + "...");
            blockingDownload(getEnvOrExit("BUCKET_NAME"), cliArgs.getObjectKey());
            System.out.println("Download completed.");
        }

        return;
    }
}
