package pck.enote.api.req;


import pck.enote.api.helper.REQUEST_TYPE;

public class BaseReq {
    protected REQUEST_TYPE type;

    public BaseReq(REQUEST_TYPE type) {
        this.type = type;
    }


    public REQUEST_TYPE getType() {
        return type;
    }

    public void setType(REQUEST_TYPE type) {
        this.type = type;
    }
}
