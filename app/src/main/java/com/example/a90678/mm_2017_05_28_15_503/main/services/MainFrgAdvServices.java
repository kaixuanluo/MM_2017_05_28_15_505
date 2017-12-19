package com.example.a90678.mm_2017_05_28_15_503.main.services;

import com.example.a90678.mm_2017_05_28_15_503.main.data.MainFrgAdvResult;
import com.example.a90678.mm_2017_05_28_15_503.main.data.ModuleResult;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by 90678 on 2017/6/2.
 */

public interface MainFrgAdvServices {

    //
    @GET("getAdvList")
    Observable<MainFrgAdvResult> advList(@Query("MinId") String minid,
                                         @Query("PageSize") int pageSize);

    //
    @GET("ReadAllVideoServlet2")
    Observable<MainFrgAdvResult> advList2(@Query("page") int minid,
                                          @Query("size") int pageSize);

    //
    @GET("ReadAllVideoServlet2")
    Observable<MainFrgAdvResult> advList3(@Query("page") int minid,
                                          @Query("size") int pageSize,
                                          @Query("mode") String mode,
                                          @Query("type") String type);

    @GET
    Observable<MainFrgAdvResult> youkuList(@Url String url, int page, int size);

    //
    @GET("{type}")
    Observable<MainFrgAdvResult> advList5(@Path("type") String type, @Query("page") int minid,
                                          @Query("size") int pageSize);
//    //
//    @GET("Web_17_06_19_01_42/{type}")
//    Observable<MainFrgAdvResult> advList5(@Path("type") String type, @Query("page") int minid,
//                                          @Query("size") int pageSize);

    @GET("Module")
    Observable<ModuleResult> getModule();

    @GET("ModuleSub")
    Observable<ModuleResult> getModuleSub(@Query("module") String module);

    @GET("ModuleSubSub")
    Observable<ModuleResult> getModuleSubSub(@Query("module") String module,
                                             @Query("moduleSub") String moduleSub);

    @GET("ModuleSubSubSub")
    Observable<MainFrgAdvResult> getModuleSubSubSub(@Query("module") String module,
                                                    @Query("module") String moduleSub, @Query("module") String moduleSubSub);

}
