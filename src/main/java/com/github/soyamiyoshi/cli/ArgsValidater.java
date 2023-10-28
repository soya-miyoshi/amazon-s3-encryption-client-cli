package com.github.soyamiyoshi.cli;

public class ArgsValidater {
    public static void validate(CommandLineArgs cliArgs) throws IllegalArgumentException {
        if (cliArgs.isUpload() && cliArgs.isDownload()) {
            throw new IllegalArgumentException(
                    "Cannot specify both upload and download options.");
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
