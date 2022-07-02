package pck.enote.fe.model;

public class Note {
    private String type, uri, createdAt;
    private int id;
    private byte[] content;

    public Note() {
    }

    public Note(int id, String type, String uri, String createdAt) {
        this.id = id;
        this.type = type;
        this.uri = uri;
        this.createdAt = createdAt;
    }

    public Note(int id, String type, String uri, String createdAt, byte[] content) {
        this.id = id;
        this.type = type;
        this.uri = uri;
        this.createdAt = createdAt;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "NoteInfo{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", uri='" + uri + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}