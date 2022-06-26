package pck.enote.fe.model;

public class Note {
    public String type, uri, createdAt;
    public int id;

    public Note() {
    }

    public Note(int id, String type, String uri, String createdAt) {
        this.id = id;
        this.type = type;
        this.uri = uri;
        this.createdAt = createdAt;
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