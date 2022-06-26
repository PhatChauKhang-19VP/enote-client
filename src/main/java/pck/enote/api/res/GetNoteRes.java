package pck.enote.api.res;

import pck.enote.api.req.REQUEST_TYPE;
import pck.enote.fe.model.Note;

public class GetNoteRes extends BaseRes {
    private final Note note;

    private GetNoteRes(RESPONSE_STATUS status, String msg) {
        super(status, msg, REQUEST_TYPE.GET_NOTE);
        note = null;
    }

    public GetNoteRes(RESPONSE_STATUS status, String msg, Note note) {
        super(status, msg, REQUEST_TYPE.GET_NOTE);
        this.note = note;
    }

    public Note getNote() {
        return note;
    }


    @Override
    public String toString() {
        return "GetNoteRes{" + "note=" + note + "status=" + status + ", msg='" + msg + '\'' + ", type=" + type + '}';
    }
}
