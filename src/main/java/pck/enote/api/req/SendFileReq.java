package pck.enote.api.req;

import pck.enote.api.helper.StructClass;
import pck.enote.helper.FileHelper;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;

public class SendFileReq extends BaseReq {
    private String mimeType;
    private byte[] buffer;

    public SendFileReq() {
        super(REQUEST_TYPE.UPLOAD);
    }

    public SendFileReq(File file) {
        super(REQUEST_TYPE.UPLOAD);
        try {
            mimeType = Files.probeContentType(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            mimeType = "";
        }
        System.out.println(mimeType);
        buffer = FileHelper.getFileBuffer(file);
    }

    public SendFileReq(String packedReq) {
        super(REQUEST_TYPE.UPLOAD);
        HashMap<String, String> reqData = StructClass.unpack(packedReq);
        buffer = reqData.get("buffer").getBytes();
    }

    @Override
    public boolean initFromPackedReq(String packedReq) {
        type = REQUEST_TYPE.UPLOAD;
        HashMap<String, String> reqData = StructClass.unpack(packedReq);
        try {
            mimeType = reqData.get("mimeType");
            buffer = reqData.get("buffer").getBytes();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }

    @Override
    public boolean initFromHashMap(HashMap<String, String> reqData) {
        type = REQUEST_TYPE.UPLOAD;
        try {
            mimeType = reqData.get("mimeType");
            buffer = reqData.get("buffer").getBytes();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getPackedReq() {
        HashMap<String, String> reqData = new HashMap<>();
        reqData.put("type", type.name());
        reqData.put("mimeType", mimeType);
        reqData.put("buffer", new String(buffer, StandardCharsets.UTF_8));

        String packedReq = StructClass.pack(reqData);

        return packedReq;
    }

    public String getMimeType() {
        return mimeType;
    }

    public byte[] getBuffer() {
        return buffer;
    }
}
