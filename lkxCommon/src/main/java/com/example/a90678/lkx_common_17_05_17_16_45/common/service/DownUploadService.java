package com.example.a90678.lkx_common_17_05_17_16_45.common.service;

import com.example.a90678.lkx_common_17_05_17_16_45.common.bean.UploadResult;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 90678 on 2017/6/19.
 */

public interface DownUploadService {

    @GET("DownUploadServlet")
    Observable<ResponseBody>
    download(@Query("downSession") long session, @Query("readLength") long readLength, @Query("fileAbsoloutPath") String fileAbsoloutPath);

    @Multipart
    @POST("DownUploadServlet")
    Observable<UploadResult> uploadFile(@Part MultipartBody.Part file, @Query("fileAbsoloutPath") String fileAbsoloutPath);

    @Multipart
    @POST("DownUploadServlet")
    retrofit2.Call<String> uploadFile2(@Part MultipartBody.Part file, @Query("fileAbsoloutPath") String fileAbsoloutPath);

    @Multipart
    @POST("DownUploadServlet")
    Observable<UploadResult> uploadFile(@PartMap Map<String, RequestBody> map, @Query("fileAbsoloutPath") String fileAbsoloutPath);

}
