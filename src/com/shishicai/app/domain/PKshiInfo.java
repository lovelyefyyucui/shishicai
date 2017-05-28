package com.shishicai.app.domain;

import java.util.List;

/**
 * Created by Administrator on 2017/5/7 0007.
 */

public class PKshiInfo {


    /**
     * root : {"pk10":[{"time":"20170507 09:07","issue":616443,"nums":"6 3 2 1 7 9 10 4 5 8"},{"time":"20170507 09:12","issue":616444,"nums":"5 7 2 6 1 9 10 8 3 4"},{"time":"20170507 09:17","issue":616445,"nums":"2 5 7 4 6 10 1 3 8 9"},{"time":"20170507 09:22","issue":616446,"nums":"2 8 1 7 5 6 4 10 3 9"},{"time":"20170507 09:27","issue":616447,"nums":"10 4 9 1 5 6 8 3 2 7"},{"time":"20170507 09:32","issue":616448,"nums":"3 10 7 4 9 1 5 6 8 2"},{"time":"20170507 09:37","issue":616449,"nums":"9 1 6 8 4 10 5 7 3 2"},{"time":"20170507 09:42","issue":616450,"nums":"6 4 1 2 5 3 10 8 9 7"},{"time":"20170507 09:47","issue":616451,"nums":"3 2 10 7 5 9 8 6 4 1"},{"time":"20170507 09:52","issue":616452,"nums":"8 1 2 5 7 10 3 6 9 4"},{"time":"20170507 09:57","issue":616453,"nums":"8 1 9 6 5 7 10 2 4 3"},{"time":"20170507 10:02","issue":616454,"nums":"8 9 4 10 1 5 7 2 3 6"},{"time":"20170507 10:07","issue":616455,"nums":"7 8 3 6 5 4 10 2 1 9"},{"time":"20170507 10:12","issue":616456,"nums":"10 7 1 4 8 6 3 2 9 5"},{"time":"20170507 10:17","issue":616457,"nums":"9 7 1 3 10 6 8 2 5 4"},{"time":"20170507 10:22","issue":616458,"nums":"3 10 5 1 4 7 8 9 2 6"},{"time":"20170507 10:27","issue":616459,"nums":"10 8 6 7 4 3 9 5 2 1"},{"time":"20170507 10:32","issue":616460,"nums":"7 3 6 9 8 5 2 1 4 10"},{"time":"20170507 10:37","issue":616461,"nums":"8 6 1 4 9 3 2 10 5 7"}]}
     */

    private RootBean root;

    public RootBean getRoot() {
        return root;
    }

    public void setRoot(RootBean root) {
        this.root = root;
    }

    public static class RootBean {
        private List<Pk10Bean> pk10;

        public List<Pk10Bean> getPk10() {
            return pk10;
        }

        public void setPk10(List<Pk10Bean> pk10) {
            this.pk10 = pk10;
        }

        public static class Pk10Bean {
            /**
             * time : 20170507 09:07
             * issue : 616443
             * nums : 6 3 2 1 7 9 10 4 5 8
             */

            private String time;
            private int issue;
            private String nums;

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public int getIssue() {
                return issue;
            }

            public void setIssue(int issue) {
                this.issue = issue;
            }

            public String getNums() {
                return nums;
            }

            public void setNums(String nums) {
                this.nums = nums;
            }
        }
    }
}
