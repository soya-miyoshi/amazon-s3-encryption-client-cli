package com.github.soyamiyoshi.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class PemContentUtil {

    public static byte[] getPemContentBytes(String path, String beginDelimiter,
            String endDelimiter) throws IOException {
        String pemContent = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);

        String pemData = pemContent
                .replace(beginDelimiter, "")
                .replace(endDelimiter, "")
                .replaceAll("\\s", ""); // Remove newlines and spaces

        return Base64.getDecoder().decode(pemData);
    }

}
