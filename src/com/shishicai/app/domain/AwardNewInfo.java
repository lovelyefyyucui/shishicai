package com.shishicai.app.domain;

/**
 * Created by Administrator on 2017/5/17 0017.
 */

public class AwardNewInfo {


    /**
     * time : 1495004002327
     * current : {"periodNumber":618302,"awardTime":"2017-05-17 14:52:00","awardNumbers":"1,6,5,4,9,7,8,2,10,3"}
     * next : {"periodNumber":618303,"awardTime":"2017-05-17 14:57:00","awardTimeInterval":217672,"delayTimeInterval":15}
     */

    private long time;
    private CurrentBean current;
    private NextBean next;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public CurrentBean getCurrent() {
        return current;
    }

    public void setCurrent(CurrentBean current) {
        this.current = current;
    }

    public NextBean getNext() {
        return next;
    }

    public void setNext(NextBean next) {
        this.next = next;
    }

    public static class CurrentBean {
        /**
         * periodNumber : 618302
         * awardTime : 2017-05-17 14:52:00
         * awardNumbers : 1,6,5,4,9,7,8,2,10,3
         */

        private int periodNumber;
        private String awardTime;
        private String awardNumbers;

        public int getPeriodNumber() {
            return periodNumber;
        }

        public void setPeriodNumber(int periodNumber) {
            this.periodNumber = periodNumber;
        }

        public String getAwardTime() {
            return awardTime;
        }

        public void setAwardTime(String awardTime) {
            this.awardTime = awardTime;
        }

        public String getAwardNumbers() {
            return awardNumbers;
        }

        public void setAwardNumbers(String awardNumbers) {
            this.awardNumbers = awardNumbers;
        }
    }

    public static class NextBean {
        /**
         * periodNumber : 618303
         * awardTime : 2017-05-17 14:57:00
         * awardTimeInterval : 217672
         * delayTimeInterval : 15
         */

        private int periodNumber;
        private String awardTime;
        private int awardTimeInterval;
        private int delayTimeInterval;

        public int getPeriodNumber() {
            return periodNumber;
        }

        public void setPeriodNumber(int periodNumber) {
            this.periodNumber = periodNumber;
        }

        public String getAwardTime() {
            return awardTime;
        }

        public void setAwardTime(String awardTime) {
            this.awardTime = awardTime;
        }

        public int getAwardTimeInterval() {
            return awardTimeInterval;
        }

        public void setAwardTimeInterval(int awardTimeInterval) {
            this.awardTimeInterval = awardTimeInterval;
        }

        public int getDelayTimeInterval() {
            return delayTimeInterval;
        }

        public void setDelayTimeInterval(int delayTimeInterval) {
            this.delayTimeInterval = delayTimeInterval;
        }
    }
}
