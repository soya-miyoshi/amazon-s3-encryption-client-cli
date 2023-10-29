package com.github.soyamiyoshi.cli;

public class ArgsValidater {
    public static void validate(CommandLineArgs cliArgs) throws IllegalArgumentException {
        if (cliArgs.isUpload() && cliArgs.isDownload()) {
            // warn that both upload and download are specified
            System.out.println(
                    "Both upload and download are specified. So will do both.");
        }

        if (!cliArgs.isUpload() && !cliArgs.isDownload()) {
            throw new IllegalArgumentException("Either upload or download must be specified.");
        }

        if (cliArgs.isUpload() && (cliArgs.getLocalFilePath() == null
                || cliArgs.getLocalFilePath().isEmpty())) {
            throw new IllegalArgumentException("Local file path must be specified for upload.");
        }
    }
}
