package com.shishicai.app.domain;

import java.util.List;

/**
 * Created by Administrator on 2017/5/17 0017.
 */

public class AwardReferInfo {

    private String error;
    private List<ItemArrayBean> itemArray;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<ItemArrayBean> getItemArray() {
        return itemArray;
    }

    public void setItemArray(List<ItemArrayBean> itemArray) {
        this.itemArray = itemArray;
    }

    public static class ItemArrayBean {
        /**
         * date : 2017-05-17
         * periodNumber : 618291
         * period : 618291
         * code :
         * star : 3
         * data : [{"name":"冠军","data":[{"code":"1,2,5,6,7","color":"","result":"0"},{"code":"单","color":"","result":"0"},{"code":"小","color":"","result":"0"}]},{"name":"亚军","data":[{"code":"2,3,5,8,9","color":"","result":"0"},{"code":"单","color":"","result":"0"},{"code":"小","color":"","result":"0"}]},{"name":"第三名","data":[{"code":"1,4,6,7,9","color":"","result":"0"},{"code":"单","color":"","result":"0"},{"code":"大","color":"","result":"0"}]},{"name":"第四名","data":[{"code":"3,5,7,8,10","color":"","result":"0"},{"code":"单","color":"","result":"0"},{"code":"大","color":"","result":"0"}]},{"name":"第五名","data":[{"code":"3,7,8,9,10","color":"","result":"0"},{"code":"单","color":"","result":"0"},{"code":"大","color":"","result":"0"}]},{"name":"第六名","data":[{"code":"1,2,3,5,7","color":"","result":"0"},{"code":"单","color":"","result":"0"},{"code":"小","color":"","result":"0"}]},{"name":"第七名","data":[{"code":"1,2,3,6,9","color":"","result":"0"},{"code":"单","color":"","result":"0"},{"code":"小","color":"","result":"0"}]},{"name":"第八名","data":[{"code":"1,2,3,5,10","color":"","result":"0"},{"code":"单","color":"","result":"0"},{"code":"小","color":"","result":"0"}]},{"name":"第九名","data":[{"code":"1,2,4,7,8","color":"","result":"0"},{"code":"双","color":"","result":"0"},{"code":"小","color":"","result":"0"}]},{"name":"第十名","data":[{"code":"3,5,6,9,10","color":"","result":"0"},{"code":"单","color":"","result":"0"},{"code":"大","color":"","result":"0"}]},{"name":"冠亚和","data":[{"code":"3,6,13,14,18","color":"","result":"0"}]}]
         */

        private String date;
        private String periodNumber;
        private String period;
        private String code;
        private String star;
        private List<DataBeanX> data;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getPeriodNumber() {
            return periodNumber;
        }

        public void setPeriodNumber(String periodNumber) {
            this.periodNumber = periodNumber;
        }

        public String getPeriod() {
            return period;
        }

        public void setPeriod(String period) {
            this.period = period;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getStar() {
            return star;
        }

        public void setStar(String star) {
            this.star = star;
        }

        public List<DataBeanX> getData() {
            return data;
        }

        public void setData(List<DataBeanX> data) {
            this.data = data;
        }

        public static class DataBeanX {
            /**
             * name : 冠军
             * data : [{"code":"1,2,5,6,7","color":"","result":"0"},{"code":"单","color":"","result":"0"},{"code":"小","color":"","result":"0"}]
             */

            private String name;
            private List<DataBean> data;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<DataBean> getData() {
                return data;
            }

            public void setData(List<DataBean> data) {
                this.data = data;
            }

            public static class DataBean {
                /**
                 * code : 1,2,5,6,7
                 * color :
                 * result : 0
                 */

                private String code;
                private String color;
                private String result;

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public String getColor() {
                    return color;
                }

                public void setColor(String color) {
                    this.color = color;
                }

                public String getResult() {
                    return result;
                }

                public void setResult(String result) {
                    this.result = result;
                }
            }
        }
    }
}
