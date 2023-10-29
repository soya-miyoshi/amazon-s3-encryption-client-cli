package com.github.soyamiyoshi;

import static com.github.soyamiyoshi.Act.downloadAsync;
import static com.github.soyamiyoshi.Act.uploadAsync;
import static com.github.soyamiyoshi.util.EnvLoader.getEnvOrExit;
import java.nio.file.Path;
import com.github.soyamiyoshi.cli.ArgsParser;
import com.github.soyamiyoshi.cli.CommandLineArgs;

public class App {
    public static void main(String[] args) {
        CommandLineArgs cliArgs = ArgsParser.parse(args);

        if (cliArgs.isUpload()) {
            uploadAsync(getEnvOrExit("BUCKET_NAME"), cliArgs.getObjectKey(),
                    Path.of(cliArgs.getLocalFilePath()));
        }
        if (cliArgs.isDownload()) {
            downloadAsync(getEnvOrExit("BUCKET_NAME"), cliArgs.getObjectKey());
            System.exit(1);
        }

        return;
    }
}
