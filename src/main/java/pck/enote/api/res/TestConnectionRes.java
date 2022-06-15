package pck.enote.api.res;

import pck.enote.api.req.REQUEST_TYPE;

public class TestConnectionRes extends BaseRes {
    public TestConnectionRes(RESPONSE_STATUS status, String msg) {
        super(status, msg, REQUEST_TYPE.TEST_CONNECTION);
    }
}
