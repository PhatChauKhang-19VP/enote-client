package pck.enote.api.req;


public class GetNoteReq extends BaseReq {
    private String noteId;

    public GetNoteReq(String noteId) {
        super(REQUEST_TYPE.GET_NOTE);
        this.noteId = noteId;
    }

    public String getNoteId() {
        return noteId;
    }
}
