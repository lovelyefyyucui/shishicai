package com.shishicai.app.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/1 0001.
 */

public class AwardInfo {


    /**
     * rows : 5
     * code : cqssc
     * info : 免费接口随机延迟3-6分钟，实时接口请访问opencai.net或QQ:23081452(注明彩票或API)
     * data : [{"expect":"20170501035","opencode":"7,3,8,3,5","opentime":"2017-05-01 11:50:40","opentimestamp":1493610640},{"expect":"20170501034","opencode":"2,1,7,5,6","opentime":"2017-05-01 11:40:40","opentimestamp":1493610040},{"expect":"20170501033","opencode":"9,5,0,2,3","opentime":"2017-05-01 11:30:40","opentimestamp":1493609440},{"expect":"20170501032","opencode":"6,4,2,4,7","opentime":"2017-05-01 11:20:40","opentimestamp":1493608840},{"expect":"20170501031","opencode":"6,7,9,4,4","opentime":"2017-05-01 11:10:40","opentimestamp":1493608240}]
     */

    private int rows;
    private String code;
    private String info;
    private ArrayList<DataBean> data;

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public ArrayList<DataBean> getData() {
        return data;
    }

    public void setData(ArrayList<DataBean> data) {
        this.data = data;
    }

}
