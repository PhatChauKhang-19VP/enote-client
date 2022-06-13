package pck.enote.api.res;


import pck.enote.api.helper.REQUEST_TYPE;
import pck.enote.api.helper.StructClass;

import java.util.HashMap;

public class TestConnectionRes extends BaseRes {
    public TestConnectionRes(RESPONSE_STATUS status, String msg, REQUEST_TYPE type) {
        super(status, msg, type);
    }

    public TestConnectionRes(String packedRes) {
        super(RESPONSE_STATUS.FAILED, "failed", REQUEST_TYPE.TEST_CONNECTION);
        HashMap<String, String> resData = StructClass.unpack(packedRes);
        status = RESPONSE_STATUS.valueOf(resData.get("status"));
        msg = resData.get("msg");
        type = REQUEST_TYPE.valueOf(resData.get("type"));
    }

    public String getPackedRes(){
        HashMap<String, String> resData = new HashMap<>();
        resData.put("status", status.name());
        resData.put("msg", msg);
        resData.put("type", type.name());

        String packedRes = StructClass.pack(resData);

        return packedRes;
    }

    @Override
    public String toString() {
        return "TestConnectionRes{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", type=" + type +
                '}';
    }
}
