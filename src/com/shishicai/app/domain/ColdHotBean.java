package com.shishicai.app.domain;

import java.util.List;

/**
 * Created by Administrator on 2017/5/23 0023.
 */

public class ColdHotBean {


    /**
     * error :
     * itemArray : [[8],[3,7,1,10,9,6,4],[2,5]]
     */

    private String error;
    private List<List<Integer>> itemArray;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<List<Integer>> getItemArray() {
        return itemArray;
    }

    public void setItemArray(List<List<Integer>> itemArray) {
        this.itemArray = itemArray;
    }
}
