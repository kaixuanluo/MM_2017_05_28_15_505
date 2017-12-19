package com.example.a90678.lkx_common_17_05_17_16_45.common.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a90678.lkx_common_17_05_17_16_45.R;

/**
 * Created by 90678 on 2017/6/7.
 */

public class BaseSwipeRefreshLayout {

    SwipeRefreshLayout mSrl;

    public SwipeRefreshLayout getSrl() {
        return mSrl;
    }

    public void setSrl(SwipeRefreshLayout srl) {
        mSrl = srl;
    }

    protected void setSwipeRefreshEnable(SwipeRefreshLayout srl, boolean enable) {
        if (srl != null) {
            srl.setEnabled(enable);
        }
    }

    protected void setSwipeRefreshEnable(boolean enable) {
        SwipeRefreshLayout srl = getSrl();
        if (srl != null) {
            srl.setEnabled(enable);
        }
    }

    protected void setOnSwipeRefreshListener (SwipeRefreshLayout.OnRefreshListener listener) {
        SwipeRefreshLayout srl = getSrl();
        if (srl != null) {
            srl.setOnRefreshListener(listener);
        }
    }

    protected void setSwipeRefreshing(boolean refreshing) {
        SwipeRefreshLayout srl = getSrl();
        if (srl != null) {
            srl.setRefreshing(refreshing);
        }
    }

    protected View addSwipeRefreshView (Context context, View subView) {
        View swipeView = LayoutInflater.from(context).inflate(R.layout.layout_swipe_refresh, null);
        mSrl = (SwipeRefreshLayout) swipeView.findViewById(R.id.srl);
        mSrl.addView(subView);
//        mSrl.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                loadData();
//            }
//        });
        setSwipeRefreshEnable(getSrl(), false);
        return swipeView;
//        return container;
    }

    public BaseSwipeRefreshLayout() {

    }

    public void initSwipeLayout () {

    }
}
