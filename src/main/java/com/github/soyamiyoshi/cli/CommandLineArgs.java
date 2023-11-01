package com.github.soyamiyoshi.cli;

import com.beust.jcommander.Parameter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommandLineArgs {
    @Parameter(names = {"-o", "--object-key"}, description = "Object key")
    private String objectKey;

    @Parameter(names = {"-l", "--local-file-path"}, description = "Local file path")
    private String localFilePath;

    @Parameter(names = {"-u", "--upload"}, description = "Upload action")
    private boolean isUpload;

    @Parameter(names = {"-d", "--download"}, description = "Download action")
    private boolean isDownload;

    @Parameter(names = {"--heavy"}, description = "Download more than 64MB data")
    private boolean isDownloadMorethan64MBdata;

    @Parameter(names = {"-h", "--help"}, description = "Display help/usage.", help = true)
    private boolean help;

    @Parameter(names = {"-p", "--public-key-path"}, description = "Path to public key")
    private String publicKeyPath;

    @Parameter(names = {"-k", "--private-key-path"}, description = "Path to private key")
    private String privateKeyPath;

    @Parameter(names = {"-b", "--bucket-name"}, description = "Bucket name")
    private String bucketName;
}
