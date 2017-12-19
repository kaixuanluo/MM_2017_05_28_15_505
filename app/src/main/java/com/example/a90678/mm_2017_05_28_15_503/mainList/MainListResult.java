package com.example.a90678.mm_2017_05_28_15_503.mainList;

import com.example.a90678.lkx_common_17_05_17_16_45.common.bean.BaseListResult;

import java.util.List;

/**
 * Created by 90678 on 2017/5/28.
 */

public class MainListResult extends BaseListResult<MainListResult.Result.MainList> {

    @Override
    public List<Result.MainList> getList() {
        return getResult().rows;
    }

    private Result result;

    public Result getResult() {
        return result;
    }

    public static class Result {

        List<MainList> rows;

        public List<MainList> getRows() {
            return rows;
        }

        public static class MainList {

            private String date;
            private int nId;
            private String title;
            private String content;
            private String url;

            public void setDate(String date) {
                this.date = date;
            }

            public void setNId(int nId) {
                this.nId = nId;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getDate() {
                return date;
            }

            public int getNId() {
                return nId;
            }

            public String getTitle() {
                return title;
            }

            public String getContent() {
                return content;
            }

            public String getUrl() {
                return url;
            }
        }
    }
}
