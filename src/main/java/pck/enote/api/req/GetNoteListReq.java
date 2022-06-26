package pck.enote.api.req;

public class GetNoteListReq extends BaseReq {
    private String username;

    public GetNoteListReq(String username) {
        super(REQUEST_TYPE.GET_NOTE_LIST);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "GetNoteListReq{" +
                "username='" + username + '\'' +
                ", type=" + type +
                '}';
    }
}
