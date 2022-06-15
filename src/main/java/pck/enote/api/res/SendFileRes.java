package pck.enote.api.res;

import pck.enote.api.helper.StructClass;
import pck.enote.api.req.REQUEST_TYPE;

import java.util.HashMap;

public class SendFileRes extends BaseRes {
    private String fileUrl;

    private SendFileRes(RESPONSE_STATUS status, String msg, REQUEST_TYPE type) {
        super(status, msg, type);
    }

    public SendFileRes(RESPONSE_STATUS status, String msg, REQUEST_TYPE type, String fileUrl) {
        super(status, msg, type);
        this.fileUrl = fileUrl;
    }

    public SendFileRes(String packedRes) {
        super(RESPONSE_STATUS.FAILED, "failed", REQUEST_TYPE.TEST_CONNECTION);
        HashMap<String, String> resData = StructClass.unpack(packedRes);
        status = RESPONSE_STATUS.valueOf(resData.get("status"));
        msg = resData.get("msg");
        type = REQUEST_TYPE.valueOf(resData.get("type"));
        fileUrl = resData.get("fileUrl");
    }

    @Override
    public String getPackedRes() {
        HashMap<String, String> resData = new HashMap<>();
        resData.put("status", status.name());
        resData.put("msg", msg);
        resData.put("type", type.name());
        resData.put("fileUrl", fileUrl);

        String packedRes = StructClass.pack(resData);

        return packedRes;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    @Override
    public String toString() {
        return "SendFileRes{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", type=" + type +
                ", fileUrl='" + fileUrl + '\'' +
                '}';
    }
}
