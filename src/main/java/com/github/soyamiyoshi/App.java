package com.github.soyamiyoshi;

import static com.github.soyamiyoshi.util.EnvLoader.getEnvOrExit;
import java.nio.file.Path;
import com.github.soyamiyoshi.act.Act;
import com.github.soyamiyoshi.cli.ArgsParser;
import com.github.soyamiyoshi.cli.CommandLineArgs;

public class App {

    public static void main(String[] args) {
        CommandLineArgs cliArgs = ArgsParser.parse(args);

        if (cliArgs.isUpload()) {
            Act.uploadAsync(getEnvOrExit("BUCKET_NAME"), cliArgs.getObjectKey(),
                    Path.of(cliArgs.getLocalFilePath()));
        }

    }
}
