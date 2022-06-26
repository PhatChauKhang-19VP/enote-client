package pck.enote.api.req;


public class GetNoteReq extends BaseReq {
    Integer noteId;
    private String username;

    public GetNoteReq(String username, Integer noteId) {
        super(REQUEST_TYPE.GET_NOTE);
        this.noteId = noteId;
        this.username = username;
    }

    public Integer getNoteId() {
        return noteId;
    }

    public String getUsername() {
        return username;
    }
}
