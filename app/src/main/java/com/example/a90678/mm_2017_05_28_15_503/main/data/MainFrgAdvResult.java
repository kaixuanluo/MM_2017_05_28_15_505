package com.example.a90678.mm_2017_05_28_15_503.main.data;

import com.example.a90678.lkx_common_17_05_17_16_45.common.bean.BaseListResult;
import com.example.a90678.lkx_common_17_05_17_16_45.common.module.BaseApiServiceModule;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 90678 on 2017/6/2.
 */

public class MainFrgAdvResult extends BaseListResult<MainFrgAdvResult.MainFrgAdv> implements Serializable {

    @Override
    public List<MainFrgAdv> getList() {
        return getResult();
    }

    List<MainFrgAdv> result;

    public List<MainFrgAdv> getResult() {
        return result;
    }

    public void setResult(List<MainFrgAdv> result) {
        this.result = result;
    }

    public static class MainFrgAdv implements Serializable {
        int id;
        int src;
        String urlImg;
        String title;
        String urlVideo;
        String urlVideoSub;
        String number; //播放次数
        boolean isBlur;
        boolean isAd;
        long fileLength;

        public MainFrgAdv() {
        }

        @Override
        public String toString() {
            return "MainFrgAdv{" +
                    "id=" + id +
                    ", src=" + src +
                    ", urlImg='" + urlImg + '\'' +
                    ", title='" + title + '\'' +
                    ", urlVideo='" + urlVideo + '\'' +
                    '}';
        }

        public String getUrlVideoSub() {
            return urlVideo;
        }

        public void setUrlVideoSub(String urlVideoSub) {
            this.urlVideo = urlVideoSub;
        }

        public long getFileLength() {
            return fileLength;
        }

        public void setFileLength(long fileLength) {
            this.fileLength = fileLength;
        }

        public boolean isAd() {
            return isAd;
        }

        public void setAd(boolean ad) {
            isAd = ad;
        }

        public String getUrlVideo() {
            return BaseApiServiceModule.getWebRoot() + urlVideo;
        }

        public void setUrlVideo(String urlVideo) {
            this.urlVideo = urlVideo;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSrc() {
            return src;
        }

        public void setSrc(int src) {
            this.src = src;
        }

        public String getUrlImg() {
            if (isBlur) {
                return urlImg;
            } else {
                return BaseApiServiceModule.getWebRoot() + urlImg;
            }
        }

        public void setUrlImg(String urlImg) {
            this.urlImg = urlImg;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public boolean isBlur() {
            return isBlur;
        }

        public void setBlur(boolean blur) {
            isBlur = blur;
        }
    }
}
