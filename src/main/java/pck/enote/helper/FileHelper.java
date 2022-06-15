package pck.enote.helper;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLConnection;
import java.nio.file.Files;

public class FileHelper {
    public static byte[] getFileBuffer(File file) {
        byte[] buffer = new byte[(int) file.length()];

        try {
            return Files.readAllBytes(file.toPath());
        } catch (Exception e) {
            e.printStackTrace();
            return buffer;
        }
    }

    public static String getMimetype(File file) {
        return URLConnection.guessContentTypeFromName(file.getName());
    }
}