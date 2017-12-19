package com.example.a90678.mm_2017_05_28_15_503.main.view;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.text.format.Formatter;
import android.util.Log;

import com.example.a90678.lkx_common_17_05_17_16_45.common.download.DownInfo;
import com.example.a90678.lkx_common_17_05_17_16_45.common.download.DownUpLoadPresenter;
import com.example.a90678.lkx_common_17_05_17_16_45.common.download.DownUpLoadPresenter3;
import com.example.a90678.lkx_common_17_05_17_16_45.common.download.HttpProgressOnNextListener;
import com.example.a90678.lkx_common_17_05_17_16_45.common.utils.DirUtil;
import com.example.a90678.lkx_common_17_05_17_16_45.common.utils.NotificationUtil;
import com.example.a90678.lkx_common_17_05_17_16_45.common.utils.TipUtils;

import java.io.File;
import java.util.Map;

/**
 * Created by 90678 on 2017/6/29.
 */

public class DownloadUtil {

    private void startUpload (final Activity activity) {

        NotificationUtil notificationUtil = new NotificationUtil(activity);

        DownUpLoadPresenter downUpLoadPresenter = new DownUpLoadPresenter(activity);
        final File file = new File("/sdcard/tabbled.apk");
        if (file == null || !file.exists()) {
            Log.e("file empty "," file empty");
            return;
        }
        final Map<Integer, NotificationCompat.Builder> builderMap = notificationUtil
                .addNotificationAndUpdateSummaries(file.getName(), "");
        NotificationCompat.Builder builder = null;
        int notificationId = 0;
        for (int key : builderMap.keySet()) {
            builder = builderMap.get(key);
            notificationId = key;
        }
        if (builder == null) {
            return;
        }
        final Notification notification = builder.build();
        final NotificationManager notificationManager = notificationUtil.getmNotificationManager();
        final NotificationCompat.Builder finalBuilder = builder;
        final int finalNotificationId = notificationId;
        downUpLoadPresenter.upload(file, new HttpProgressOnNextListener() {
            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onStart() {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                finalBuilder.setContentText(e.getMessage());
                notificationManager.notify(finalNotificationId, notification);
            }

            @Override
            public void updateProgress(long readLength) {
                finalBuilder.setContentText(""+ Formatter.formatFileSize
                        (activity,readLength)
                        +"/"
                        +Formatter.formatFileSize
                        (activity, file.length()));
                finalBuilder.setProgress(100, (int) (100F * readLength / file.length()), false);
                notificationManager.notify(finalNotificationId, notification);
            }
        });
    }

    public void startDownload (final Activity activity, String title, String url, String urlSub,
                                long fileLength) {
        NotificationUtil notificationUtil = new NotificationUtil(activity);
        DownUpLoadPresenter3 downUpLoadPresenter = new DownUpLoadPresenter3(activity);
        final DownInfo downInfo = new DownInfo();
//        downInfo.setCountLength(downloadMainFrgAdv.getFileLength());
        downInfo.setName(title);
        downInfo.setUrl(url);
        downInfo.setUrlSub(urlSub);
        downInfo.setSavePath(DirUtil.getSDCardAppDownloadDir());
        downInfo.setCountLength(fileLength);
        final File downloadFile = new File(downInfo.getSavePath(), downInfo.getName());
        downInfo.setDownloadFile(downloadFile);
        if (!downInfo.getSavePath().exists()) {
            downInfo.getSavePath().mkdirs();
        }
        if (downloadFile.exists() && downloadFile.length() == fileLength) {
            TipUtils.showTip(activity, "文件已存在");
            return;
        }
        final Map<Integer, NotificationCompat.Builder> builderMap = notificationUtil
                .addNotificationAndUpdateSummaries(title, "");
        NotificationCompat.Builder builder = null;
        int notificationId = 0;
        for (int key : builderMap.keySet()) {
            builder = builderMap.get(key);
            notificationId = key;
        }
        if (builder == null) {
            return;
        }
        final Notification notification = builder.build();
        final NotificationManager notificationManager = notificationUtil.getmNotificationManager();
        final NotificationCompat.Builder finalBuilder = builder;
        final int finalNotificationId = notificationId;
        final NotificationCompat.Builder finalBuilder1 = builder;
        final int finalNotificationId1 = notificationId;
        final long[] mReadLength = {0};
//        final Subscription[] subscribe = {Observable.interval(3000, 3000, TimeUnit.MILLISECONDS)
////延时3000 ，每间隔3000，时间单位
//                .compose(this.<Long>bindToLifecycle())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<Long>() {
//            @Override
//            public void call(Long aLong) {
//                finalBuilder.setContentText("" + Formatter.formatFileSize
//                        (MainFragmentListAndVideo.this.getContext(), mReadLength[0])
//                        + "/"
//                        + Formatter.formatFileSize
//                        (MainFragmentListAndVideo.this.getContext(), downInfo.getCountLength()));
//                finalBuilder1.setProgress(100, (int) (100F * mReadLength[0] / downInfo.getCountLength()), false);
//                notificationManager.notify(finalNotificationId1, finalBuilder1.build());
//            }
//        })};
        downInfo.setListener(new HttpProgressOnNextListener() {
            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onStart() {

            }

            @Override
            public void onComplete() {
//                subscribe[0].unsubscribe();
                notificationManager.cancel(finalNotificationId);

                if (downloadFile.getName().endsWith(".apk")){
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setDataAndType(Uri.fromFile(downloadFile), "application/vnd.android.package-archive");
//                    activity.startActivity(intent);

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    //版本在7.0以上是不能直接通过uri访问的
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
                        // 由于没有在Activity环境下启动Activity,设置下面的标签
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
                        Uri apkUri = FileProvider.getUriForFile(activity, "com.example.a90678.mm_2017_05_28_15_50", downloadFile);
                        //添加这一句表示对目标应用临时授权该Uri所代表的文件
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                    } else {
                        intent.setDataAndType(Uri.fromFile(downloadFile),
                                "application/vnd.android.package-archive");
                    }
                    activity.startActivity(intent);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                finalBuilder1.setContentText("重新连接中");
                notificationManager.notify(finalNotificationId1, finalBuilder1.build());
            }

            @Override
            public void updateProgress(long readLength) {
                mReadLength[0] = readLength;

                finalBuilder.setContentText("" + Formatter.formatFileSize
                        (activity, mReadLength[0])
                        + "/"
                        + Formatter.formatFileSize
                        (activity, downInfo.getCountLength()));
                finalBuilder1.setProgress(100, (int) (100F * mReadLength[0] / downInfo.getCountLength()), false);
                notificationManager.notify(finalNotificationId1, finalBuilder1.build());
            }
        });
        downUpLoadPresenter.download(downInfo);
    }

}
