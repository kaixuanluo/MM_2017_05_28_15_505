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
import com.example.a90678.lkx_common_17_05_17_16_45.common.upload.ProgressResponseBody;
import com.example.a90678.lkx_common_17_05_17_16_45.common.utils.DirUtil;
import com.example.a90678.lkx_common_17_05_17_16_45.common.utils.FileUtil;
import com.example.a90678.lkx_common_17_05_17_16_45.common.utils.TipUtils;
import com.google.gson.GsonBuilder;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.SequenceInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
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
public class DownUpLoadPresenter2 extends BasePresenter {

    Activity mContext;

    File tempFileContent;
    List<File> tempFiles;
    int errorTimes;

    File destination = new File(DirUtil.getSDCardAppDownloadDir(), "3333333.mp4");

    public DownUpLoadPresenter2(Activity context) {
        mContext = context;
        tempFiles = new ArrayList<>();
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
        tempFileContent = new File(absolutePath + "_temp");

        if (!tempFileContent.exists()) {
            tempFileContent.mkdirs();
        }

        File[] files = tempFileContent.listFiles();
        int length = files == null ? 0 : files.length;
        if (length > (int) Math.ceil((double)downInfo.getCountLength() / ((double)1024*1024))) {
            Log.e("数据错误","数据错误");
            try {
                FileUtils.deleteDirectory(tempFileContent);
            } catch (IOException e) {
                e.printStackTrace();
            }
            tempFileContent.delete();
            return;
        }

        Log.e(" dedao tempFIleContent  ", FileUtil.getTotalSizeOfFilesInDir(tempFileContent)+" tempFileCOntent " + Formatter.formatFileSize(mContext, FileUtil.getTotalSizeOfFilesInDir(tempFileContent)));

        downInfo.getListener().updateProgress(FileUtil.getTotalSizeOfFilesInDir(tempFileContent));

        if (FileUtil.getTotalSizeOfFilesInDir(tempFileContent) == downInfo.getCountLength()) {
            fileGeneration2(downInfo);
        }

//        final DownUploadService downUploadService;
//        if (downInfo.getDownUploadService() != null) {
//            downUploadService = downInfo.getDownUploadService();
//        } else {
//            OkHttpClient client = new OkHttpClient.Builder()
//                    .retryOnConnectionFailure(true)//错误重连
//                    .writeTimeout(5000, TimeUnit.MILLISECONDS)
//                    .readTimeout(5000, TimeUnit.MILLISECONDS)
//                    .connectTimeout(5000, TimeUnit.MILLISECONDS)
//                    .addNetworkInterceptor(new Interceptor() {
//                        @Override
//                        public Response intercept(Chain chain) throws IOException {
//                            Response originalResponse = chain.proceed(chain.request());
//
//                            return originalResponse.newBuilder()
//                                    .body(new ProgressResponseBody(originalResponse.body(), new DownUploadProgressListener() {
//                                        @Override
//                                        public void onProgress(long currentBytesCount, InputStream inputStream, long totalBytesCount) {
//
//                                            currentBytesCount = downInfo.getReadLength() + currentBytesCount;
//                                            downInfo.setReadLength(currentBytesCount);
//
//                                        }
//                                    }))
//                                    .build();
//                        }
//                    })
////                    .addInterceptor(new HttpLoggingInterceptor().setLevel(
////                            HttpLoggingInterceptor.Level.BODY))
//                    .build();
//
//            Retrofit retrofit = new Retrofit.Builder()
//                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()))
//                    .client(client)
//                    .baseUrl(BaseApiServiceModule.getWebRoot() + BaseApiServiceModule.getServlet())
//                    .build();
//
//            downUploadService = retrofit.create(DownUploadService.class);
//            downInfo.setDownUploadService(downUploadService);
//        }

//        Log.e("开始位置"," 开始位置 " + downInfo.getReadLength());

        final Observable<ResponseBody> downloadObservable = BaseApiServiceModule.provideRetrofit()
                .create(DownUploadService.class)
                .download(666,
                length, downInfo.getUrlSub());
        downloadObservable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(new Func1<ResponseBody, DownInfo>() {
                         @Override
                         public DownInfo call(ResponseBody responseBody) {
                             InputStream inputStream = responseBody.byteStream();
                             File downloadFile = downInfo.getDownloadFile();
                             if (inputStream == null) {
                                 return downInfo;
                             }

                             try {
                                 int length1 = tempFileContent.listFiles() == null ? 0 : tempFileContent.listFiles().length;
                                 File tempSubFile = new File(tempFileContent, length1+"");
                                 FileUtils.copyInputStreamToFile(inputStream, tempSubFile);

                             } catch (IOException e) {
                                 e.printStackTrace();
                             }

                             if (downloadFile.length() < downInfo.getCountLength()) {

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

                        int length1 = tempFileContent.listFiles() == null ? 0 : tempFileContent.listFiles().length;
                        Log.e("copy 3333 ", length1 + " copy33333333  " + FileUtil.getTotalSizeOfFilesInDir(tempFileContent));

                        downInfo.getListener().updateProgress(FileUtil.getTotalSizeOfFilesInDir(tempFileContent));

                        fileGeneration2(downInfo);

                    }

                    @Override
                    public void onError(Throwable e) {
                        downInfo.getListener().onError(e);
                        Log.e("erron错误", e + "下载错误");
                    }

                    @Override
                    public void onNext(DownInfo downInfo) {
                        Log.e("next", "next");
                        downInfo.getListener().onNext(downInfo);
                    }
                });

    }

    private void fileGeneration2(final DownInfo downInfo) {
        if (FileUtil.getTotalSizeOfFilesInDir(tempFileContent) == downInfo.getCountLength()) {
            File[] files = tempFileContent.listFiles();
            if (files == null) {
                Log.e("files is null ", " files is null ");
                return;
            }

            for (int i = 0; i < files.length; i++) {

                File file = new File(tempFileContent, i + "");
                Log.e("合并文件第几个 "," 第几个 文件名 " + file.getName());
                try {
                    FileInputStream inputStream2 = new FileInputStream(file);
                    FileOutputStream output =  FileUtils.openOutputStream(destination, true);
                    try {
                        IOUtils.copy(inputStream2, output);
                        output.close();
                        if (i == files.length -1) {
                            downInfo.getListener().onComplete();
                        }
                    } finally {
                        IOUtils.closeQuietly(output);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else {
            Log.e("已下载长度 ", FileUtil.getTotalSizeOfFilesInDir(tempFileContent) + " 总长度 " + downInfo.getCountLength());
            Observable.timer(500, TimeUnit.MILLISECONDS)
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

    private void fileGeneration(final DownInfo downInfo) {
        if (FileUtil.getTotalSizeOfFilesInDir(tempFileContent) == downInfo.getCountLength()) {
            List<FileInputStream> list = new ArrayList<FileInputStream>();
            File[] files = tempFileContent.listFiles();
            if (files == null) {
                Log.e("files is null ", " files is null ");
                return;
            }
            for (int i = 0; i < files.length; i++) {
                try {
                    Log.e("合并文件第几个 "," 第几个 " + i);
                    FileInputStream inputStream = new FileInputStream(files[i]);
                    list.add(inputStream);


//                                 FileUtils.copyInputStreamToFile(inputStream, destination);

                    FileInputStream inputStream2 = new FileInputStream(files[i]);
                    try {
                        FileOutputStream output =  FileUtils.openOutputStream(destination, true);
                        try {
                            IOUtils.copy(inputStream2, output);
                            output.close();
                        } finally {
                            IOUtils.closeQuietly(output);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

//            //使用 Enumeration（列举） 将文件全部列举出来
//            Enumeration<FileInputStream> eum = Collections.enumeration(list);
//            //SequenceInputStream合并流 合并文件
//            SequenceInputStream sis = new SequenceInputStream(eum);
//            try {
//                FileOutputStream fos = new FileOutputStream(downInfo.getDownloadFile());
//                byte[] by = new byte[100];
//                int len;
//                while ((len = sis.read(by)) != -1) {
//                    fos.write(by, 0, len);
//                }
//                fos.flush();
//                fos.close();
//                sis.close();
//                tempFileContent.delete();
//                Log.e("合并完成！", "合并完成！");
//                downInfo.getListener().onComplete();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            downInfo.getListener().onComplete();
            Log.e("completed ", " completed ");
        } else {
            Log.e("已下载长度 ", FileUtil.getTotalSizeOfFilesInDir(tempFileContent) + " 总长度 " + downInfo.getCountLength());
            Observable.timer(5000, TimeUnit.MILLISECONDS)
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

    /**
     * 写入文件
     *
     * @param file
     * @throws IOException
     */
    public void writeCache(InputStream is, File file, long readLength, long contentLength) throws IOException {
//        if (!file.getParentFile().exists()) {
//            file.getParentFile().mkdirs();
//        }
        long allLength;
//        if (info.getCountLength() == 0) {
//            allLength = responseBody.contentLength();
//        } else {
//            allLength = info.getCountLength();
//        }
        FileChannel channelOut = null;
        RandomAccessFile randomAccessFile = null;
        randomAccessFile = new RandomAccessFile(file, "rwd");
        channelOut = randomAccessFile.getChannel();
        MappedByteBuffer mappedBuffer = channelOut.map(FileChannel.MapMode.READ_WRITE,
                readLength, contentLength - readLength);
        byte[] buffer = new byte[1024 * 8];
        int len;
        int record = 0;
        while ((len = is.read(buffer)) != -1) {
            mappedBuffer.put(buffer, 0, len);
            record += len;
        }
        is.close();
        if (channelOut != null) {
            channelOut.close();
        }
        if (randomAccessFile != null) {
            randomAccessFile.close();
        }
    }

    public class RetryWithDelay implements
            Func1<Observable<? extends Throwable>, Observable<?>> {

        private final int maxRetries;//重试多少次
        private final int retryDelayMillis;//延迟多少秒
        private int retryCount;

        public RetryWithDelay(int maxRetries, int retryDelayMillis) {
            this.maxRetries = maxRetries;
            this.retryDelayMillis = retryDelayMillis;
        }

        @Override
        public Observable<?> call(Observable<? extends Throwable> attempts) {
            return attempts
                    .flatMap(new Func1<Throwable, Observable<?>>() {
                        @Override
                        public Observable<?> call(Throwable throwable) {
                            if (++retryCount <= maxRetries) {
                                // When this Observable calls onNext, the original Observable will be retried (i.e. re-subscribed).
                                Log.e("retry" + retryDelayMillis, "retry  "
                                        + " fenhou " + retryCount);
                                return Observable.timer(retryDelayMillis,
                                        TimeUnit.MILLISECONDS);
                            }
                            // Max retries hit. Just pass the error along.
                            return Observable.error(throwable);
                        }
                    });
        }
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
