package com.shishicai.app.domain;

import java.util.List;

/**
 * Created by Administrator on 2017/5/19 0019.
 */

public class TwoSideLongInfo {


    /**
     * error :
     * itemArray : [["冠亚和,单","6"],["第九名,双","4"],["第五名,小","4"],["第六名,单","4"],["第六名,大","4"],["第五名,虎","4"],["冠　军,虎","3"],["第十名,大","3"],["冠　军,小","3"],["冠亚和,小","2"],["第九名,大","2"],["亚　军,小","2"],["亚　军,虎","2"],["第四名,双","1"],["第十名,双","1"],["第四名,大","1"],["第八名,大","1"],["第八名,单","1"],["第五名,双","1"],["亚　军,双","1"],["第三名,虎","1"],["第三名,小","1"],["第三名,单","1"],["第七名,小","1"],["第七名,单","1"],["冠　军,单","1"],["第四名,龙","1"]]
     */

    private String error;
    private List<List<String>> itemArray;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<List<String>> getItemArray() {
        return itemArray;
    }

    public void setItemArray(List<List<String>> itemArray) {
        this.itemArray = itemArray;
    }
}
