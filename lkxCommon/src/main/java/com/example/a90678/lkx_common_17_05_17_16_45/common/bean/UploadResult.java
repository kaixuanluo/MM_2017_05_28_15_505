package com.example.a90678.lkx_common_17_05_17_16_45.common.bean;

/**
 * 描述说明  <br/>
 * Author : luokaixuan <br/>
 * CreateDate : 2016/11/24 13:39 <br/>
 * Modified : luokaixuan <br/>
 * ModifiedDate : 2016/11/24 13:39 <br/>
 * Email : 1005949566@qq.com <br/>
 * Version 1.0
 */
public class UploadResult extends BaseLoadingResult {

    public static class Upload {
        String id;
        boolean isthumb;

        @Override
        public String toString() {
            return "Upload{" +
                    "id=" + id +
                    ", isthumb=" + isthumb +
                    '}';
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isthumb() {
            return isthumb;
        }

        public void setIsthumb(boolean isthumb) {
            this.isthumb = isthumb;
        }
    }
}
