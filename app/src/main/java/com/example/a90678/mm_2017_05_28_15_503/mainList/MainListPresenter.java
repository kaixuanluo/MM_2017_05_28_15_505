package com.example.a90678.mm_2017_05_28_15_503.mainList;

import com.example.a90678.lkx_common_17_05_17_16_45.common.presenter.BaseListPresenter;
import com.example.a90678.lkx_common_17_05_17_16_45.common.rx.BaseLoadingSubscriber;
import com.example.a90678.lkx_common_17_05_17_16_45.common.status.BaseListStatus;
import com.example.a90678.lkx_common_17_05_17_16_45.common.status.BaseLoadingStatus;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 90678 on 2017/5/28.
 */

public class MainListPresenter extends BaseListPresenter<MainListResult.Result.MainList, MainListResult> {

    public MainListPresenter(BaseListStatus<MainListResult.Result.MainList> baseListStatus,
                             BaseLoadingStatus<MainListResult> baseLoadingStatus) {
        super(baseListStatus, baseLoadingStatus);
    }

    @Inject
    protected MainListService mainListService;

    @Override
    public void loadData() {
        super.loadData();

        observ(0+"");
    }

    @Override
    public void loadMore(MainListResult.Result.MainList data) {
        super.loadMore(data);

        observ(data.getNId()+"");
    }

    private void observ (String id) {
        Observable<MainListResult> emailObservable = MainListModule.provideMainList().mailList(id, 10);
        emailObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseLoadingSubscriber<>(this));
    }

}
