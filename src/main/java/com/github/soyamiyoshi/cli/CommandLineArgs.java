package com.github.soyamiyoshi.cli;

import com.beust.jcommander.Parameter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommandLineArgs {
    @Parameter(names = {"-o", "--object_key"}, description = "Object key")
    private String objectKey;

    @Parameter(names = {"-l", "--local_file_path"}, description = "Local file path")
    private String localFilePath;

    @Parameter(names = {"-u", "--upload"}, description = "Upload action")
    private boolean isUpload;

    @Parameter(names = {"-d", "--download"}, description = "Download action")
    private boolean isDownload;

    @Parameter(names = {"--heavy"}, description = "Download more than 64MB data")
    private boolean isDownloadMorethan64MBdata;

    @Parameter(names = {"-h", "--help"}, description = "Display help/usage.", help = true)
    private boolean help;
}
