package com.example.a90678.mm_2017_05_28_15_503.mainList;

import com.example.a90678.lkx_common_17_05_17_16_45.common.module.BaseApiServiceModule;

/**
 * Created by 90678 on 2017/5/28.
 */

public class MainListModule {

    public static MainListService provideMainList(){
        return BaseApiServiceModule.provideRetrofit().create(MainListService.class);
    }
}
