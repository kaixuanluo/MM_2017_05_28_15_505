package com.example.a90678.lkx_common_17_05_17_16_45.common.view.activity;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.a90678.lkx_common_17_05_17_16_45.common.status.BaseLoadingStatus;

/**
 * Created by 90678 on 2017/6/2.
 */

public class BaseLoadingDialog<DATA> extends ProgressDialog implements BaseLoadingStatus<DATA>{

    public BaseLoadingDialog(Context context) {
        super(context);
    }

    public BaseLoadingDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void startLoad() {

    }

    @Override
    public void loading() {
        this.show();
    }

    @Override
    public void success(DATA data) {

    }

    @Override
    public void empty() {

    }

    @Override
    public void failure(int code, String msg) {

    }

    @Override
    public void error(String msg) {

    }

    @Override
    public void complete() {
        this.dismiss();
    }

    @Override
    public void reLoad() {

    }

    @Override
    public void offLine() {

    }

    @Override
    public void unLogin() {

    }
}
