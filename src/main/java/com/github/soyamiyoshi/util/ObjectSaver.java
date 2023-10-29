package com.github.soyamiyoshi.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ObjectSaver {
    public static void saveToFile(InputStream is, String outputPath) {
        try (FileOutputStream fos = new FileOutputStream(outputPath)) {

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = is.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
