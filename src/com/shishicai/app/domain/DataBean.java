package com.shishicai.app.domain;

/**
 * Created by Administrator on 2017/5/1 0001.
 */

public class DataBean {
    /**
     * expect : 20170501035
     * opencode : 7,3,8,3,5
     * opentime : 2017-05-01 11:50:40
     * opentimestamp : 1493610640
     */

    private String expect;
    private String opencode;
    private String opentime;
    private int opentimestamp;

    public String getExpect() {
        return expect;
    }

    public void setExpect(String expect) {
        this.expect = expect;
    }

    public String getOpencode() {
        return opencode;
    }

    public void setOpencode(String opencode) {
        this.opencode = opencode;
    }

    public String getOpentime() {
        return opentime;
    }

    public void setOpentime(String opentime) {
        this.opentime = opentime;
    }

    public int getOpentimestamp() {
        return opentimestamp;
    }

    public void setOpentimestamp(int opentimestamp) {
        this.opentimestamp = opentimestamp;
    }
}
