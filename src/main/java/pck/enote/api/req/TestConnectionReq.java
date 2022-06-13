package pck.enote.api.req;


import pck.enote.api.helper.REQUEST_TYPE;
import pck.enote.api.helper.StructClass;

import java.util.HashMap;

public class TestConnectionReq extends BaseReq {
    public TestConnectionReq() {
        super(REQUEST_TYPE.TEST_CONNECTION);
    }

    public String getPackedReq() {
        HashMap<String, String> reqData = new HashMap<>();
        reqData.put("type", type.name());

        String packedReq = StructClass.pack(reqData);

        return packedReq;
    }
}
