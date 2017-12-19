package com.example.a90678.lkx_common_17_05_17_16_45.common.download;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.UiThread;
import android.text.format.Formatter;
import android.util.Log;
import android.util.TimeUtils;
import android.widget.Toast;

import com.example.a90678.lkx_common_17_05_17_16_45.common.bean.UploadResult;
import com.example.a90678.lkx_common_17_05_17_16_45.common.module.BaseApiServiceModule;
import com.example.a90678.lkx_common_17_05_17_16_45.common.presenter.BasePresenter;
import com.example.a90678.lkx_common_17_05_17_16_45.common.service.DownUploadService;
import com.example.a90678.lkx_common_17_05_17_16_45.common.upload.DownUploadProgressListener;
import com.example.a90678.lkx_common_17_05_17_16_45.common.upload.ProgressRequestBody;
import com.example.a90678.lkx_common_17_05_17_16_45.common.upload.ProgressResponseBody;
import com.example.a90678.lkx_common_17_05_17_16_45.common.utils.FileUtil;
import com.example.a90678.lkx_common_17_05_17_16_45.common.utils.TipUtils;
import com.google.gson.GsonBuilder;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.SequenceInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.text.Format;
import java.util.Enumeration;
import java.util.HashMap;
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
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
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
public class DownUpLoadPresenter extends BasePresenter {

    Activity mContext;

    public DownUpLoadPresenter(Activity context) {
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
        File downloadFile = downInfo.getDownloadFile();
        if (downloadFile.length() != 0 && downloadFile.length() == downInfo.getCountLength()) {
            downInfo.getListener().onComplete();
            return;
        }

        if (downloadFile.length() != 0&& downloadFile.length() > downInfo.getCountLength()) {
            String path = downloadFile.getAbsolutePath();
            downloadFile.delete();
           downInfo.setDownloadFile(new File(path));
        }

        final DownUploadService downUploadService;
        if (downInfo.getDownUploadService() != null) {
            downUploadService = downInfo.getDownUploadService();
        } else {
            OkHttpClient client = new OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)//错误重连
                    .writeTimeout(5000, TimeUnit.MILLISECONDS)
                    .readTimeout(5000, TimeUnit.MILLISECONDS)
                    .connectTimeout(5000, TimeUnit.MILLISECONDS)
                    .addNetworkInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Response originalResponse = chain.proceed(chain.request());

                            return originalResponse.newBuilder()
                                    .body(new ProgressResponseBody(originalResponse.body(), new DownUploadProgressListener() {
                                        @Override
                                        public void onProgress(long currentBytesCount, InputStream inputStream, long totalBytesCount) {

                                            currentBytesCount = downInfo.getReadLength() + currentBytesCount;
                                            Log.e("开始位 置 " + currentBytesCount, " 总共 "+
                                                    Formatter.formatFileSize(mContext, currentBytesCount) + totalBytesCount);
//                                            if (downInfo.getCountLength() > totalBytesCount) {
//                                                currentBytesCount = downInfo.getCountLength() - totalBytesCount + currentBytesCount;
//                                            } else {
////                                                downInfo.setCountLength(totalBytesCount);
//                                            }
                                            downInfo.setReadLength(currentBytesCount);
//
                                            downInfo.getListener().updateProgress(currentBytesCount);

//                                            inputstreamtofile(inputStream, downInfo.getDownloadFile());

//                                            Observable.just(inputStream)
//                                                    .observeOn(AndroidSchedulers.mainThread())
//                                                    .subscribe(new Action1<InputStream>() {
//                                                        @Override
//                                                        public void call(InputStream inputStream) {
////                                                                FileUtils.copyInputStreamToFile(inputStream, downInfo.getDownloadFile());
//                                                              randomFile(inputStream, downInfo.getDownloadFile());
//                                                            downInfo.getListener().updateProgress(downInfo.getDownloadFile().length(), downInfo.getCountLength());
//                                                        }
//                                                    });
//

                                        }
                                    }))
                                    .build();
                        }
                    })
//                    .addInterceptor(new HttpLoggingInterceptor().setLevel(
//                            HttpLoggingInterceptor.Level.BODY))
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()))
                    .client(client)
                    .baseUrl(BaseApiServiceModule.getWebRoot() + BaseApiServiceModule.getServlet())
                    .build();

            downUploadService = retrofit.create(DownUploadService.class);
            downInfo.setDownUploadService(downUploadService);
        }

//        Log.e("开始位置"," 开始位置 " + downInfo.getReadLength());

        final Observable<ResponseBody> downloadObservable = downUploadService.download(666,
                downInfo.getReadLength(), downInfo.getUrlSub());
        downloadObservable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
//                .onErrorResumeNext(
//                        downUploadService.download(666,
//                                downInfo.getDownloadFile().length(), downInfo.getUrlSub()))
//                .onExceptionResumeNext(downUploadService.download(666,
//                        downInfo.getDownloadFile().length(), downInfo.getUrlSub()))
//                        .defer(new Func0<Observable<ResponseBody>>() {
//                            @Override
//                            public Observable<ResponseBody> call() {
//                                return downUploadService.download(666,
//                                        downInfo.getReadLength(), downInfo.getUrlSub());
//                            }
//                        })
                .map(new Func1<ResponseBody, DownInfo>() {
                         @Override
                         public DownInfo call(ResponseBody responseBody) {
                             Log.e("call ", " call ");
                             long contentLength = responseBody.contentLength();
//                             final File downloadFile = new File(downInfo.getSavePath(), downInfo.getName());
//                             if (downloadFile.exists() && downloadFile.length() != contentLength) {
//                                 downloadFile.delete();
//                             }
                             Log.e("写入 ", " 写入 ");
                             InputStream inputStream = responseBody.byteStream();
                             File downloadFile = downInfo.getDownloadFile();
                             if (inputStream == null) {
                                 return downInfo;
                             }
//                             randomFile(readLength, inputStream, downloadFile);

                             try {
                                 Log.e("copy ", " copy " + downloadFile.length());
                                 FileUtils.copyInputStreamToFile(inputStream, downloadFile);
//////                                 downInfo.setDownloadFile(downloadFile);
//                                 writeCache(responseBody, downloadFile, readLength, contentLength);
////
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
//                .subscribe(subscriber);
                .subscribe(new Subscriber<DownInfo>() {
                    @Override
                    public void onCompleted() {
//                        if (downInfo.getDownloadFile().length() < downInfo.getCountLength()) {
//                            download(downInfo);
//                            Log.e("继续 ", " 继续 ");
//                        } else {
                            downInfo.getListener().onComplete();
                            Log.e("completed ", " completed ");
//                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        downInfo.getListener().onError(e);
                        Log.e("erron错误", e + "下载错误");
////                        if (e.getMessage().equals("timeout")) {
                        if (downInfo.getDownloadFile().length() == downInfo.getCountLength()) {
                            onCompleted();
                            return;
                        }

                        Observable.timer(5000, TimeUnit.MILLISECONDS)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Action1<Long>() {
                                    @Override
                                    public void call(Long aLong) {
                                        download(downInfo);
                                    }
                                });

                    }

                    @Override
                    public void onNext(DownInfo downInfo) {
                        Log.e("next", "next");
                        downInfo.getListener().onNext(downInfo);
                    }
                });

    }

    public void download2(DownInfo downInfo) {
        OkHttpClient okHttpClient = BaseApiServiceModule.provideOkHttpClient();
        DownloadSubscribe downloadSubscribe = new DownloadSubscribe(downInfo, okHttpClient);
        io.reactivex.Observable observable = io.reactivex.Observable.create(downloadSubscribe);
        observable.subscribe();
    }

    private class DownloadSubscribe implements ObservableOnSubscribe<DownInfo> {
        private DownInfo downloadInfo;
        private OkHttpClient mClient;

        public DownloadSubscribe(DownInfo downloadInfo, OkHttpClient client) {
            this.downloadInfo = downloadInfo;
            this.mClient = client;
        }

        @Override
        public void subscribe(final ObservableEmitter<DownInfo> e) throws Exception {
//            String url = downloadInfo.getUrl();
            Log.e("sub "," sub ");
            final long[] downloadLength = {downloadInfo.getDownloadFile().length()};//已经下载好的长度
            final long contentLength = downloadInfo.getCountLength();//文件的总长度
            //初始进度信息 
            e.onNext(downloadInfo);
            String url = BaseApiServiceModule.getWebRootUrlWithServlet() + "DownUploadServlet"
                    + "?readLength=" + downloadLength[0]
                    + "&downSession=666"
                    + "&fileAbsoloutPath=" + downloadInfo.getUrlSub();

            final Request request = new Request.Builder()
                    //确定下载的范围,添加此头,则服务器就可以跳过已经下载好的部分 
//                    .addHeader("RANGE", "bytes=" + downloadLength + "-" + contentLength)
                    .url(url)
                    .get()
                    .build();
//            Call call = mClient.newCall(request);
//            downCalls.put(url, call);//把这个添加到call里,方便取消

            Observable.create(new Observable.OnSubscribe<Response>() {

                @Override
                public void call(Subscriber<? super Response> subscriber) {
                    Call call = mClient.newCall(request);
                    Response response = null;
                    try {
                        response = call.execute();
                        Log.e("call sub ","call sub " + response.toString());
                        subscriber.onNext(response);

                    } catch (IOException e1) {
                        e1.printStackTrace();
                        Log.e("call sub ","call sub error ");
                    }
                }
            })
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Response>() {
                        @Override
                        public void call(Response response) {
                            File file = downloadInfo.getDownloadFile();
                            InputStream is = null;
                            FileOutputStream fileOutputStream = null;
                            try {
                                is = response.body().byteStream();
                                fileOutputStream = new FileOutputStream(file, true);
                                byte[] buffer = new byte[2048];//缓冲数组2kB
                                int len;
                                while ((len = is.read(buffer)) != -1) {
                                    fileOutputStream.write(buffer, 0, len);
                                    downloadLength[0] += len;
                                    downloadInfo.getListener().updateProgress(downloadLength[0]);
                                    Log.e("download length "," downloadlength " + downloadLength[0]);
//                                    downloadInfo.setReadLength(downloadLength[0]);
                                    e.onNext(downloadInfo);
                                }
                                fileOutputStream.flush();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
//                downCalls.remove(url);
                            finally {
                                try {
                                    is.close();
                                    fileOutputStream.flush();
                                    fileOutputStream.close();
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }

                            }
                            e.onComplete();//完成
                        }
                    });

//            File file = new File(MyApp.sContext.getFilesDir(), downloadInfo.getFileName());

        }
    }

    public void inputstreamtofile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void randomFile(InputStream inputStream, File file) {

//        byte[] buffer = new byte[1024];
        int offset = 0;
        try {
            int count = 1024;
//            while (count == 0) {
//                count = inputStream.available();
//            }
            byte[] buffer = new byte[count];
            RandomAccessFile threadfile = new RandomAccessFile(file, "rwd");
            threadfile.seek(file.length());

            while ((offset = inputStream.read(buffer, 0, 1024)) != -1) {
                threadfile.write(buffer, 0, offset);
            }

            threadfile.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入文件
     *
     * @param file
     * @throws IOException
     */
    public void writeCache(ResponseBody responseBody, File file, long readLength, long contentLength) throws IOException {
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
        while ((len = responseBody.byteStream().read(buffer)) != -1) {
            mappedBuffer.put(buffer, 0, len);
            record += len;
        }
        responseBody.byteStream().close();
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
