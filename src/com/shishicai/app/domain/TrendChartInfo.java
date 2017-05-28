package com.shishicai.app.domain;

import java.util.List;

/**
 * Created by Administrator on 2017/5/26 0026.
 */

public class TrendChartInfo {

    /**
     * state : 200
     * error :
     * items : [{"period":"95","ball":2},{"period":"96","ball":5},{"period":"97","ball":4},{"period":"98","ball":6},{"period":"99","ball":9},{"period":"00","ball":9},{"period":"01","ball":5},{"period":"02","ball":4},{"period":"03","ball":9},{"period":"04","ball":9},{"period":"05","ball":9},{"period":"06","ball":7},{"period":"07","ball":4},{"period":"08","ball":9},{"period":"09","ball":10},{"period":"10","ball":5},{"period":"11","ball":5},{"period":"12","ball":3},{"period":"13","ball":6},{"period":"14","ball":1},{"period":"15","ball":5},{"period":"16","ball":9},{"period":"17","ball":2},{"period":"18","ball":5},{"period":"19","ball":7},{"period":"20","ball":3},{"period":"21","ball":10},{"period":"22","ball":9},{"period":"23","ball":7},{"period":"24","ball":1},{"period":"25","ball":10},{"period":"26","ball":4},{"period":"27","ball":2},{"period":"28","ball":6},{"period":"29","ball":3},{"period":"30","ball":9},{"period":"31","ball":7},{"period":"32","ball":4},{"period":"33","ball":3},{"period":"34","ball":7}]
     */

    private int state;
    private String error;
    private List<ItemsBean> items;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * period : 95
         * ball : 2
         */

        private String period;
        private int ball;

        public String getPeriod() {
            return period;
        }

        public void setPeriod(String period) {
            this.period = period;
        }

        public int getBall() {
            return ball;
        }

        public void setBall(int ball) {
            this.ball = ball;
        }
    }
}
