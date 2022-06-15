package pck.enote.api.req;


import pck.enote.api.helper.StructClass;

import java.util.HashMap;

public class TestConnectionReq extends BaseReq {
    public TestConnectionReq() {
        super(REQUEST_TYPE.TEST_CONNECTION);
    }

    @Override
    public boolean initFromPackedReq(String packedReq) {
        type = REQUEST_TYPE.TEST_CONNECTION;
        return true;
    }

    @Override
    public boolean initFromHashMap(HashMap<String, String> reqData) {
        type = REQUEST_TYPE.TEST_CONNECTION;
        return true;
    }

    @Override
    public String getPackedReq() {
        HashMap<String, String> reqData = new HashMap<>();
        reqData.put("type", type.name());

        String packedReq = StructClass.pack(reqData);

        return packedReq;
    }
}
