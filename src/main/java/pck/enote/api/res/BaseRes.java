package pck.enote.api.res;


import pck.enote.api.req.REQUEST_TYPE;

public abstract class BaseRes {
    protected RESPONSE_STATUS status;
    protected String msg;
    protected REQUEST_TYPE type;

    public BaseRes(RESPONSE_STATUS status, String msg, REQUEST_TYPE type) {
        this.status = status;
        this.msg = msg;
        this.type = type;
    }

    public RESPONSE_STATUS getStatus() {
        return status;
    }

    public void setStatus(RESPONSE_STATUS status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public REQUEST_TYPE getType() {
        return type;
    }

    public void setType(REQUEST_TYPE type) {
        this.type = type;
    }

    /**
     * Help packed Response data to format "key1:value1;key2:value2;....."
     *
     * @return packed response
     */
    abstract public String getPackedRes();

    @Override
    public String toString() {
        return "BaseRes{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", type=" + type +
                '}';
    }
}
