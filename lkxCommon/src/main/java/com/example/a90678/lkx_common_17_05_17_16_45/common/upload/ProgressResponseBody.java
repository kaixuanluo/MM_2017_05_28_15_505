package com.example.a90678.lkx_common_17_05_17_16_45.common.upload;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by 90678 on 2017/6/21.
 */

public class ProgressResponseBody extends ResponseBody {

    private final ResponseBody responseBody;
    private final DownUploadProgressListener progressListener;
    private BufferedSource bufferedSource;

    public ProgressResponseBody(ResponseBody responseBody, DownUploadProgressListener progressListener) {
        this.responseBody = responseBody;
        this.progressListener = progressListener;
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    public void getStream () {
        OutputStream outputStream = source().buffer().outputStream();
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                // read() returns the number of bytes read, or -1 if this source is exhausted.
//                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                totalBytesRead = bytesRead != -1 ? bytesRead : 0;
////                progressListener.onProgress(totalBytesRead, responseBody.contentLength(), bytesRead == -1);
////                progressListener.onProgress(totalBytesRead, responseBody.contentLength());
//                Observable.just(totalBytesRead).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
//                    @Override
//                    public void call(Long aLong) {
//                        progressListener.onProgress(aLong, contentLength());
//                    }
//                });

                final InputStream inputStream = sink.inputStream();
                final Map<Long, InputStream> inputStreamMap = new HashMap<>();
                inputStreamMap.put(totalBytesRead, inputStream);
                Observable.just(inputStreamMap).observeOn(AndroidSchedulers.mainThread()).subscribe
                        (new Action1<Map<Long, InputStream>>() {
                    @Override
                    public void call(Map<Long, InputStream> longInputStreamMap) {
                        Long aLong = 0L;
                        InputStream inputStream1 = null;
                        for (Long key : longInputStreamMap.keySet()) {
                            aLong = key;
                            inputStream1 = longInputStreamMap.get(key);
                        }
//                        try {
//                            Log.e("inputStream1 length "," lenth " + inputStream1.read());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
                        progressListener.onProgress(aLong, inputStream1, contentLength());
                    }
                });
                return bytesRead;
            }
        };
    }
}