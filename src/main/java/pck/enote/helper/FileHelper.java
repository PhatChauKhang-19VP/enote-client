package pck.enote.helper;

import java.io.*;

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

    public static File getFileFromBuffer(byte[] buffer) throws IOException {
        File file = new File("tempFile");
        OutputStream os = new FileOutputStream(file);

        os.write(buffer);
        os.close();
        return file;
    }
}
