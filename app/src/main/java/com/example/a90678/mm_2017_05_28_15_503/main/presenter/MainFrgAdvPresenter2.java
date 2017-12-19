package com.example.a90678.mm_2017_05_28_15_503.main.presenter;

import com.example.a90678.lkx_common_17_05_17_16_45.common.presenter.BaseLoadingPresenter;
import com.example.a90678.lkx_common_17_05_17_16_45.common.status.BaseLoadingStatus;
import com.example.a90678.mm_2017_05_28_15_503.main.data.MainFrgAdvResult;
import com.example.a90678.mm_2017_05_28_15_503.main.module.MainFrgAdvModule;

import rx.Observable;

/**
 * Created by 90678 on 2017/6/2.
 */

public class MainFrgAdvPresenter2 extends BaseLoadingPresenter<MainFrgAdvResult> {

    public MainFrgAdvPresenter2(BaseLoadingStatus<MainFrgAdvResult> baseLoadingStatus) {
        super( baseLoadingStatus);
    }

    @Override
    public void loadData() {
        super.loadData();
        observer();
    }

    private void observer() {
        Observable<MainFrgAdvResult> obv = MainFrgAdvModule.getMainFrgAdvService().advList2(3, 15);
        baseLoadingObv(obv);
    }
}
