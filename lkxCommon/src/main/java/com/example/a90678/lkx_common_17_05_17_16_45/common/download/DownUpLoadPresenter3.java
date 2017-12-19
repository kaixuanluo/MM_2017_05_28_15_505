package com.example.a90678.lkx_common_17_05_17_16_45.common.download;

import android.app.Activity;
import android.text.format.Formatter;
import android.util.Log;

import com.example.a90678.lkx_common_17_05_17_16_45.common.bean.UploadResult;
import com.example.a90678.lkx_common_17_05_17_16_45.common.module.BaseApiServiceModule;
import com.example.a90678.lkx_common_17_05_17_16_45.common.presenter.BasePresenter;
import com.example.a90678.lkx_common_17_05_17_16_45.common.service.DownUploadService;
import com.example.a90678.lkx_common_17_05_17_16_45.common.upload.DownUploadProgressListener;
import com.example.a90678.lkx_common_17_05_17_16_45.common.upload.ProgressRequestBody;
import com.example.a90678.lkx_common_17_05_17_16_45.common.utils.DirUtil;
import com.example.a90678.lkx_common_17_05_17_16_45.common.utils.FileUtil;
import com.example.a90678.lkx_common_17_05_17_16_45.common.utils.TipUtils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.io.SequenceInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 描述说明  <br/>
 * Author : luokaixuan <br/>
 * CreateDate : 2016/10/27 13:35 <br/>
 * Modified : luokaixuan <br/>
 * ModifiedDate : 2016/10/27 13:35 <br/>
 * Email : 1005949566@qq.com <br/>
 * Version 1.0
 */
public class DownUpLoadPresenter3 extends BasePresenter {

    Activity mContext;

    int errorTimes;

    int copyTimes = 0;

    public DownUpLoadPresenter3(Activity context) {
        mContext = context;

    }

    public void download(final DownInfo downInfo) {

         /*正在下载不处理*/
//        if (downInfo == null || subMap.get(downInfo.getUrl()) != null) {
//            return;
//        }
        if (downInfo == null) {
            TipUtils.showTip(mContext, "downinfo is null");
            return;
        }
        final File downloadFile = downInfo.getDownloadFile();
        if (downloadFile.length() != 0 && downloadFile.length() == downInfo.getCountLength()) {
            downInfo.getListener().onComplete();
            return;
        }

        if (downloadFile.length() != 0 && downloadFile.length() > downInfo.getCountLength()) {
            String path = downloadFile.getAbsolutePath();
            downloadFile.delete();
            downInfo.setDownloadFile(new File(path));
        }

        String absolutePath = downloadFile.getAbsolutePath();

        long blockSize = 1024 * 1024;
        double blockCountDouble = ((double) downInfo.getCountLength()) / ((double) blockSize);
        System.out.println("分块" + blockCountDouble);
        int blockCount = (int) Math.ceil(blockCountDouble);
        long position = (int) downloadFile.length();

        int blockIndex = 0;
        for (int i = 0; i < blockCount; i++) {
            if (position >= i * blockSize && position < (i + 1) * blockSize) {
                blockIndex = i;
            }
        }
        final Observable<ResponseBody> downloadObservable = BaseApiServiceModule.provideRetrofit()
                .create(DownUploadService.class)
                .download(666,
                        blockIndex, downInfo.getUrlSub());
        downloadObservable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(new Func1<ResponseBody, DownInfo>() {
                         @Override
                         public DownInfo call(ResponseBody responseBody) {
                             InputStream inputStream = responseBody.byteStream();
                             File downloadFile = downInfo.getDownloadFile();
//                             if (!downloadFile.exists()) {
//                                 return downInfo;
//                             }
                             if (inputStream == null) {
                                 return downInfo;
                             }

                             copyTimes++;
                             Log.e("合并文件第几个 ", copyTimes + " 第几个 文件名 " + downloadFile.getName());

                             try {
                                 FileOutputStream output = FileUtils.openOutputStream(downloadFile, true);
                                 try {
                                     IOUtils.copy(inputStream, output);
                                     output.close();
                                 } finally {
                                     IOUtils.closeQuietly(output);
                                 }
                             } catch (IOException e) {
                                 e.printStackTrace();
                             }

                             return downInfo;
                         }
                     }
                )
                .observeOn(AndroidSchedulers.mainThread())
//                .retryWhen(new RetryWithDelay(100, 5000))
                .subscribe(new Subscriber<DownInfo>() {
                    @Override
                    public void onCompleted() {

                        if (downInfo.getDownloadFile().length() == 0) {
                            onError(new Throwable());
                            return;
                        }

                        downInfo.getListener().updateProgress(downInfo.getDownloadFile().length());

                        Log.e("complete 次数 ", "complete 次数" + copyTimes);
                        if (downInfo.getDownloadFile().length() == downInfo.getCountLength()) {
                            downInfo.getListener().onComplete();
                        } else if (downInfo.getDownloadFile().length() < downInfo.getCountLength()) {
                            Observable.timer(1000, TimeUnit.MILLISECONDS)
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Action1<Long>() {
                                        @Override
                                        public void call(Long aLong) {
                                            download(downInfo);
                                        }
                                    });
                        } else if (downInfo.getDownloadFile().length() > downInfo.getCountLength()) {
                            downInfo.getListener().onError(new Exception());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        downInfo.getListener().onError(e);
                        if (downInfo.getDownloadFile().length() < downInfo.getCountLength()) {
                            Observable.timer(1000, TimeUnit.MILLISECONDS)
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Action1<Long>() {
                                        @Override
                                        public void call(Long aLong) {
                                            errorTimes++;
                                            if (errorTimes > 10) {
                                                downInfo.getListener().onError(new Throwable());
                                            } else {
                                                download(downInfo);
                                            }
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onNext(DownInfo downInfo) {
                        Log.e("next", "next");
                        downInfo.getListener().onNext(downInfo);
                    }
                });
    }

    public void upload(final File file, final HttpProgressOnNextListener listener) {
        Retrofit retrofit = BaseApiServiceModule.provideRetrofit();
        DownUploadService downUploadService = retrofit.create(DownUploadService.class);
        final RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(),
                new ProgressRequestBody(requestBody, new DownUploadProgressListener() {
                    @Override
                    public void onProgress(long currentBytesCount, InputStream inputStream, long totalBytesCount) {
//                        Log.e("上传进度 ",totalBytesCount+" 上传进度 " + currentBytesCount);
                        listener.updateProgress(currentBytesCount);
                    }
                }));

        Map<String, RequestBody> params = new HashMap<>();

        params.put("file\"; filename=\"" + file.getName() + "", requestBody);

        downUploadService.uploadFile(part, "upload/" + file.getName())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .retryWhen(new RetryWithDelay(100, 5000))
                .subscribe(new Subscriber<UploadResult>() {
                    @Override
                    public void onCompleted() {
                        Log.e(" onCompleted ", " onCompleted ");
                        listener.onComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(" onError 错误 ", " onError 错误 " + e);
                        listener.onError(e);
//                        Log.e("错误",e.getMessage()+"错误" + e);
                        if (e.getMessage().equals("timeout")) {
                            upload(file, listener);
                        }
                    }

                    @Override
                    public void onNext(UploadResult uploadResult) {

                        Log.e("提示:上传完成", "提示:上传完成");
                        listener.onNext(uploadResult);
                    }
                });

        String downloadUrl = retrofit.baseUrl() + "DownUploadServlet";
        Log.e(" 获取 upload info ", " upload info " + downloadUrl);

    }

}
