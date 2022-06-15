package pck.enote.api.req;


import pck.enote.api.helper.StructClass;

import java.util.HashMap;

public abstract class BaseReq {
    protected REQUEST_TYPE type;

    public BaseReq(REQUEST_TYPE type) {
        this.type = type;
    }

    public static REQUEST_TYPE getReqTypeFromPackedReq(String packedReq) {
        return REQUEST_TYPE.valueOf(StructClass.unpack(packedReq).get("type"));
    }

    public static REQUEST_TYPE getReqTypeFromPackedReq(HashMap<String, String> reqData) {
        return REQUEST_TYPE.valueOf(reqData.get("type"));
    }

    public REQUEST_TYPE getType() {
        return type;
    }

    public void setType(REQUEST_TYPE type) {
        this.type = type;
    }

    /**
     * Check and import data from input
     *
     * @param packedReq has format "key1:val1;key2:val2;..."
     * @return true if valid
     */
    abstract public boolean initFromPackedReq(String packedReq);

    abstract public boolean initFromHashMap(HashMap<String, String> reqData);

    abstract public String getPackedReq();
}
