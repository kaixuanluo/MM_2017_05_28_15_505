package com.example.a90678.lkx_common_17_05_17_16_45.common.presenter;

import com.example.a90678.lkx_common_17_05_17_16_45.common.bean.BaseLoadingResult;
import com.example.a90678.lkx_common_17_05_17_16_45.common.bean.EmailListBean;
import com.example.a90678.lkx_common_17_05_17_16_45.common.status.BaseListStatus;
import com.example.a90678.lkx_common_17_05_17_16_45.common.status.BaseLoadingStatus;

import java.util.List;

/**
 * 描述说明  <br/>
 * Author : luokaixuan <br/>
 * CreateDate : 2017/1/18 15:50 <br/>
 * Modified : luokaixuan <br/>
 * ModifiedDate : 2017/1/18 15:50 <br/>
 * Email : 1005949566@qq.com <br/>
 * Version 1.0
 */
public class BaseListPresenter<ITEM, DATA extends BaseLoadingResult>
        extends BaseLoadingPresenter<DATA> implements BaseListStatus<ITEM> {
    protected int page = 0;
    BaseListStatus<ITEM> mBaseListStatus;
    public BaseListPresenter(BaseListStatus<ITEM> baseListStatus, BaseLoadingStatus<DATA> baseLoadingStatus) {
        super(baseLoadingStatus);
        mBaseListStatus = baseListStatus;
    }

    @Override
    public void success(DATA data) {
        super.success(data);
        page++;
    }

    @Override
    public void loadData() {
        super.loadData();
        page = 1;
    }

    @Override
    public void loadMore(ITEM data) {
        loading();
        mBaseListStatus.loadMore(data);
    }

    @Override
    public void getListData(List<ITEM> list) {

    }

    @Override
    public void getListMoreData(List<ITEM> list) {

    }

    @Override
    public void noMore() {
        mBaseListStatus.noMore();
    }

    @Override
    public void hasMore() {
        mBaseListStatus.hasMore();
    }

    @Override
    public void hideLoadStatus() {
        mBaseListStatus.hideLoadStatus();
    }
}
