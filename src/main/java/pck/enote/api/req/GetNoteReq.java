package pck.enote.api.req;


public class GetNoteReq extends BaseReq {
    private String username, noteId;

    public GetNoteReq(String username, String noteId) {
        super(REQUEST_TYPE.GET_NOTE);
        this.noteId = noteId;
        this.username = username;
    }

    public String getNoteId() {
        return noteId;
    }

}
