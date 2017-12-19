package com.example.a90678.lkx_common_17_05_17_16_45.common.upload;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * 自定义回调加载速度类RequestBody
 * Created by WZG on 2016/10/20.
 */

public class ProgressRequestBody extends RequestBody {
    //实际的待包装请求体
    private final RequestBody requestBody;
    //进度回调接口
    private final DownUploadProgressListener progressListener;
    //包装完成的BufferedSink
    private BufferedSink bufferedSink;
    protected CountingSink mCountingSink;

    public ProgressRequestBody(RequestBody requestBody, DownUploadProgressListener progressListener) {
        this.requestBody = requestBody;
        this.progressListener = progressListener;
    }

    /**
     * 重写调用实际的响应体的contentType
     *
     * @return MediaType
     */
    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }

    /**
     * 重写调用实际的响应体的contentLength
     *
     * @return contentLength
     * @throws IOException 异常
     */
    @Override
    public long contentLength() throws IOException {
        try {
            long l = requestBody.contentLength();
            return l;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void writeTo(BufferedSink sink) throws IOException {
        mCountingSink = new CountingSink(sink);
        BufferedSink bufferedSink = Okio.buffer(mCountingSink);
        requestBody.writeTo(bufferedSink);
        bufferedSink.flush();
    }

    protected final class CountingSink extends ForwardingSink {
        private long bytesWritten = 0;
        public CountingSink(Sink delegate) {
            super(delegate);
        }
        @Override
        public void write(final Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);
            bytesWritten += byteCount;
//            progressListener.onProgress((int) (100F * bytesWritten / contentLength()));
//            progressListener.onProgress(bytesWritten , contentLength());
            Observable.just(bytesWritten).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        try {
                            progressListener.onProgress(aLong, source.inputStream(), contentLength());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
        }
    }

    public interface Listener {
        void onProgress(int progress);
    }
//
//    /**
//     * 重写进行写入
//     *
//     * @param sink BufferedSink
//     * @throws IOException 异常
//     */
//    @Override
//    public void writeTo(BufferedSink sink) throws IOException {
//        if (null == bufferedSink) {
//            bufferedSink = Okio.buffer(sink(sink));
//        }
//        requestBody.writeTo(bufferedSink);
//        //必须调用flush，否则最后一部分数据可能不会被写入
//        bufferedSink.flush();
//    }
//
//    /**
//     * 写入，回调进度接口
//     *
//     * @param sink Sink
//     * @return Sink
//     */
//    private Sink sink(Sink sink) {
//        Log.e("调用 sink ", " 调用 sink ");
//        return new ForwardingSink(sink) {
//            //当前写入字节数
//            long writtenBytesCount = 0L;
//            //总字节长度，避免多次调用contentLength()方法
//            long totalBytesCount = 0L;
//
//            @Override
//            public void write(Buffer source, long byteCount) throws IOException {
//                super.write(source, byteCount);
//                //增加当前写入的字节数
//                writtenBytesCount += byteCount;
//                //获得contentLength的值，后续不再调用
//                if (totalBytesCount == 0) {
//                    totalBytesCount = contentLength();
//                }
//                Log.e(" writbytecount ", " writbytcount " + writtenBytesCount);
//                Observable.just(writtenBytesCount).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
//                    @Override
//                    public void call(Long aLong) {
//                        progressListener.onProgress(writtenBytesCount, totalBytesCount);
//                    }
//                });
//            }
//        };
//    }
}