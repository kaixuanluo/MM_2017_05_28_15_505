package com.example.a90678.lkx_common_17_05_17_16_45.common.upload;

import java.io.InputStream;

/**
 * 上传进度回调类
 * Created by WZG on 2016/10/20.
 */

public interface DownUploadProgressListener {
    /**
     * 上传进度
     * @param currentBytesCount
     * @param totalBytesCount
     */
    void onProgress(long currentBytesCount, InputStream inputStream, long totalBytesCount);
}