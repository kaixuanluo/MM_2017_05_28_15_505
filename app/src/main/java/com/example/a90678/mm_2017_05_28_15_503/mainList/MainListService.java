package com.example.a90678.mm_2017_05_28_15_503.mainList;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 90678 on 2017/5/28.
 */

public interface MainListService {

    //邮件列表
    @GET("getJson")
    Observable<MainListResult> mailList(@Query("MinId") String minid,
                                       @Query("PageSize") int pageSize);
}
