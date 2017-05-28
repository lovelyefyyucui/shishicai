package com.shishicai.app.domain;

/**
 * Created by Administrator on 2017/5/10 0010.
 */

public class NumBean {
    /**
     * haoma : {"number":[{"last_time_omit":13,"no":1,"wishrate":"66.67%","aver_omit":9,"history_max_omit":68,"current_omit":6,"appearrate":"9.81%"},{"last_time_omit":0,"no":2,"wishrate":"11.11%","aver_omit":9,"history_max_omit":79,"current_omit":1,"appearrate":"10.14%"},{"last_time_omit":2,"no":3,"wishrate":"155.56%","aver_omit":9,"history_max_omit":108,"current_omit":14,"appearrate":"10.04%"},{"last_time_omit":0,"no":4,"wishrate":"88.89%","aver_omit":9,"history_max_omit":73,"current_omit":8,"appearrate":"10.13%"},{"last_time_omit":13,"no":5,"wishrate":"44.44%","aver_omit":9,"history_max_omit":115,"current_omit":4,"appearrate":"9.95%"},{"last_time_omit":11,"no":6,"wishrate":"266.67%","aver_omit":9,"history_max_omit":79,"current_omit":24,"appearrate":"10.07%"},{"last_time_omit":21,"no":7,"wishrate":"55.56%","aver_omit":9,"history_max_omit":72,"current_omit":5,"appearrate":"9.94%"},{"last_time_omit":0,"no":8,"wishrate":"111.11%","aver_omit":9,"history_max_omit":81,"current_omit":10,"appearrate":"10.12%"},{"last_time_omit":2,"no":9,"wishrate":"0.0%","aver_omit":9,"history_max_omit":84,"current_omit":0,"appearrate":"9.88%"},{"last_time_omit":17,"no":10,"wishrate":"133.33%","aver_omit":9,"history_max_omit":74,"current_omit":12,"appearrate":"9.92%"}]}
     */

    private HaomaBean haoma;

    public HaomaBean getHaoma() {
        return haoma;
    }

    public void setHaoma(HaomaBean haoma) {
        this.haoma = haoma;
    }
}
