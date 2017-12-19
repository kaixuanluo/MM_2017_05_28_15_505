package com.example.a90678.lkx_common_17_05_17_16_45.common.download;

import com.example.a90678.lkx_common_17_05_17_16_45.common.service.DownUploadService;
import com.example.a90678.lkx_common_17_05_17_16_45.common.upload.DownUploadProgressListener;

import java.io.File;
import java.io.InputStream;

/**
 * apk下载请求数据基础类
 * Created by WZG on 2016/10/20.
 */

public class DownInfo {
    private String id;
    /*存储位置*/
    private File savePath;
    /*文件总长度*/
    private long countLength;
    /*下载长度*/
    private long readLength;

    private String url;

    private String urlSub;

    private Enum statu;

    String name;

    private HttpProgressOnNextListener listener;

    private File downloadFile;

    private DownUploadService downUploadService;

    private InputStream is;

    public InputStream getIs() {
        return is;
    }

    public void setIs(InputStream is) {
        this.is = is;
    }

    public DownUploadService getDownUploadService() {
        return downUploadService;
    }

    public void setDownUploadService(DownUploadService downUploadService) {
        this.downUploadService = downUploadService;
    }

    public File getDownloadFile() {
        return downloadFile;
    }

    public void setDownloadFile(File downloadFile) {
        this.downloadFile = downloadFile;
    }

    public String getUrlSub() {
        return urlSub;
    }

    public void setUrlSub(String urlSub) {
        this.urlSub = urlSub;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public File getSavePath() {
        return savePath;
    }

    public void setSavePath(File savePath) {
        this.savePath = savePath;
    }

    public HttpProgressOnNextListener getListener() {
        return listener;
    }

    public void setListener(HttpProgressOnNextListener listener) {
        this.listener = listener;
    }

    public long getCountLength() {
        return countLength;
    }

    public void setCountLength(long countLength) {
        this.countLength = countLength;
    }

    public long getReadLength() {
        return readLength;
    }

    public void setReadLength(long readLength) {
        this.readLength = readLength;
    }

    public Enum getStatu() {
        return statu;
    }

    public void setStatu(Enum statu) {
        this.statu = statu;
    }
}
