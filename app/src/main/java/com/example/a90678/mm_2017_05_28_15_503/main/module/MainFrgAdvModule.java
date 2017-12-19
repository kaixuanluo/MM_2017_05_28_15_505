package com.example.a90678.mm_2017_05_28_15_503.main.module;

import com.example.a90678.lkx_common_17_05_17_16_45.common.module.BaseApiServiceModule;
import com.example.a90678.mm_2017_05_28_15_503.main.services.MainFrgAdvServices;

/**
 * Created by 90678 on 2017/6/2.
 */

public class MainFrgAdvModule {

    public static MainFrgAdvServices getMainFrgAdvService () {
        return BaseApiServiceModule.provideRetrofit().create(MainFrgAdvServices.class);
    }
}
