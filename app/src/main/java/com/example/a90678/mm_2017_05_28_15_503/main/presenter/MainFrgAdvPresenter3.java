package com.example.a90678.mm_2017_05_28_15_503.main.presenter;

import com.example.a90678.lkx_common_17_05_17_16_45.common.presenter.BaseListPresenter;
import com.example.a90678.lkx_common_17_05_17_16_45.common.status.BaseListStatus;
import com.example.a90678.lkx_common_17_05_17_16_45.common.status.BaseLoadingStatus;
import com.example.a90678.mm_2017_05_28_15_503.main.data.MainFrgAdvResult;
import com.example.a90678.mm_2017_05_28_15_503.main.module.MainFrgAdvModule;

import rx.Observable;

/**
 * Created by 90678 on 2017/6/2.
 */

public class MainFrgAdvPresenter3 extends BaseListPresenter<MainFrgAdvResult.MainFrgAdv, MainFrgAdvResult> {

    private String mType;
    public MainFrgAdvPresenter3(String type, BaseListStatus<MainFrgAdvResult.MainFrgAdv> baseListStatus,
                                BaseLoadingStatus<MainFrgAdvResult> baseLoadingStatus) {
        super(baseListStatus, baseLoadingStatus);
        mType = type;
    }

    @Override
    public void loadData() {
        super.loadData();
        observer();
    }

    @Override
    public void loadMore(MainFrgAdvResult.MainFrgAdv data) {
        super.loadMore(data);
        observer();
    }

    private void observer() {
//        Observable<MainFrgAdvResult> obv = MainFrgAdvModule.getMainFrgAdvService().advList3(page, 20, "hg","fp1");
        Observable<MainFrgAdvResult> obv = MainFrgAdvModule.getMainFrgAdvService().advList5(mType+"", page, 20);
        baseLoadingObv(obv);
    }
}
