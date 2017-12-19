package com.example.a90678.lkx_common_17_05_17_16_45.common.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * 文件目录工具类 <br/>
 * Author : zhongw <br/>
 * CreateDate : 2015/7/17 16:20 <br/>
 * Modified : zhongw <br/>
 * ModifiedDate : 2015/7/17 16:20 <br/>
 * Email : 730123427@qq.com <br/>
 * Version 1.0
 */
public class DirUtil {
    private static final String DIR_APP = "";
    private static final String DIR_CACHE = "app-cache ";
    private static final String DIR_IMAGE = "app-image";
    private static final String DIR_RECORD = "app-record";
    private static final String DIR_DOWNLOAD = "凯旋视频";

    /**
     * 获取SDCard路径 , 不可用返回Null
     */
    public static File getSDCardDir() {
        File sdCardDir = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            sdCardDir = Environment.getExternalStorageDirectory();
        }
        return sdCardDir;
    }

    /**
     * 获取在SDCard下的应用文件夹
     *
     * @return /sdcard/Soffice
     */
    public static File getSDCardAppDir() {
        File sdCardDir = getSDCardDir();
        if (sdCardDir == null) return null;
        return createDirIfNotExist(sdCardDir, DIR_APP);
    }

    /**
     * 获取SDCard App Image目录
     *
     * @return 当sdcard可用时, 返回/sdcard/Soffice/app-image , 否则返回app私有路径下的app-image
     */
    public static File getSDCardAppImageDir() {
        return createDirIfNotExist(getSDCardAppDir(), DIR_IMAGE);
    }

    /**
     * 获取SDCard App Record 目录
     *
     * @return 当sdcard可用时, 返回/sdcard/Soffice/app-record , 否则返回app私有路径下的app-record
     */
    public static File getSDCardAppRecordDir() {
        return createDirIfNotExist(getSDCardAppDir(), DIR_RECORD);
    }

    /**
     * 获取SDCard App Download 目录
     *
     * @return 当sdcard可用时, 返回/sdcard/Soffice/app-download , 否则返回app私有路径下的app-download
     */
    public static File getSDCardAppDownloadDir() {
        return createDirIfNotExist(getSDCardAppDir(), DIR_DOWNLOAD);
    }

    public static File getUploadFile() {
        return createDirIfNotExist(getSDCardAppDir(), DIR_DOWNLOAD+"/"+"download_2016_11_23.gif");
    }

    /**
     * 获取 App Cache 目录
     *
     * @return 如果SDCard可用, 获取SDCard中app的缓存目录, 否者获取app私有缓存目录.
     */
    public static File getAppCacheDir(Context context) {
        if (context == null)
            throw new NullPointerException("Context can not be null !!");
        File cacheDir = context.getExternalCacheDir();
        cacheDir = cacheDir == null ? context.getCacheDir() : cacheDir;
        return cacheDir;
    }

    /**
     * 获取App Cache 目录
     *
     * @return 当sdcard可用时, 返回/sdcard/Android/data/cn.com.isoffice.soffice/cache/app-cache , 否则返回app私有路径下的app-cache
     */
    public static File getCacheDir(Context context) {
        return createDirIfNotExist(getAppCacheDir(context), DIR_CACHE);
    }

    public static File createDirIfNotExist(File parentDir, String dirName) {
        File dir = new File(parentDir, dirName);
        if (!dir.exists()) {
            dir.mkdir();
        }
        return dir;
    }
}
