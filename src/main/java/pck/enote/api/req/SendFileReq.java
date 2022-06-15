package pck.enote.api.req;

import pck.enote.helper.FileHelper;

import java.io.File;

public class SendFileReq extends BaseReq {
    private final String filename;
    private final byte[] buffer;

    public SendFileReq(File file) {
        super(REQUEST_TYPE.UPLOAD);
        filename = file.getName();
        buffer = FileHelper.getFileBuffer(file);
    }

    public SendFileReq(REQUEST_TYPE type, String filename, byte[] buffer) {
        super(type);
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
