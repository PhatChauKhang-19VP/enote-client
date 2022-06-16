package pck.enote.fe.model;

public class Note {
    private String filename;
    private byte[] buffer;

    public Note() {
    }

    public Note(String filename, byte[] buffer) {
        this.filename = filename;
        this.buffer = buffer;
    }

    public String getFilename() {
        return filename;
    }

    public byte[] getBuffer() {
        return buffer;
    }
}
