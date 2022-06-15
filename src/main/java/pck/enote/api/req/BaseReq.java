package pck.enote.api.req;

public abstract class BaseReq {
    protected REQUEST_TYPE type;

    public BaseReq(REQUEST_TYPE type) {
        this.type = type;
    }

    public REQUEST_TYPE getType() {
        return type;
    }
}
