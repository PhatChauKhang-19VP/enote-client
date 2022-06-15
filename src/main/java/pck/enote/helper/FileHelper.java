package pck.enote.helper;

import java.io.File;
import java.io.FileInputStream;

public class FileHelper {
    public static byte[] getFileBuffer(File file) {
        FileInputStream fileInputStream = null;
        byte[] bFile = new byte[(int) file.length()];

        try {
            //Read bytes with InputStream
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bFile);
            fileInputStream.close();

            return bFile;
        } catch (Exception e) {
            e.printStackTrace();
            return bFile;
        }
    }
}
