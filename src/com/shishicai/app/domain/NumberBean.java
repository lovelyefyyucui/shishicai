package com.shishicai.app.domain;

/**
 * Created by Administrator on 2017/5/10 0010.
 */

public class NumberBean {
        /**
         * last_time_omit : 13
         * no : 1
         * wishrate : 66.67%
         * aver_omit : 9
         * history_max_omit : 68
         * current_omit : 6
         * appearrate : 9.81%
         */

        private int last_time_omit;
        private int no;
        private String wishrate;
        private int aver_omit;
        private int history_max_omit;
        private int current_omit;
        private String appearrate;

        public int getLast_time_omit() {
            return last_time_omit;
        }

        public void setLast_time_omit(int last_time_omit) {
            this.last_time_omit = last_time_omit;
        }

        public int getNo() {
            return no;
        }

        public void setNo(int no) {
            this.no = no;
        }

        public String getWishrate() {
            return wishrate;
        }

        public void setWishrate(String wishrate) {
            this.wishrate = wishrate;
        }

        public int getAver_omit() {
            return aver_omit;
        }

        public void setAver_omit(int aver_omit) {
            this.aver_omit = aver_omit;
        }

        public int getHistory_max_omit() {
            return history_max_omit;
        }

        public void setHistory_max_omit(int history_max_omit) {
            this.history_max_omit = history_max_omit;
        }

        public int getCurrent_omit() {
            return current_omit;
        }

        public void setCurrent_omit(int current_omit) {
            this.current_omit = current_omit;
        }

        public String getAppearrate() {
            return appearrate;
        }

        public void setAppearrate(String appearrate) {
            this.appearrate = appearrate;
        }
}
