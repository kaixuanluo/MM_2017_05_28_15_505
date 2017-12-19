package com.example.a90678.lkx_common_17_05_17_16_45.common.view.activity;

import android.support.v4.app.Fragment;

/**
 * Created by 90678 on 2017/6/1.
 */

public class ItemTab {

    private String mTitle;
    private Fragment mFragment;
    private int mImgSrc;

    public ItemTab(String mTitle, Fragment mFragment) {
        this.mTitle = mTitle;
        this.mFragment = mFragment;
    }

    public ItemTab(String title, int imgSrc, Fragment fragment) {
        mTitle = title;
        mFragment = fragment;
        mImgSrc = imgSrc;
    }

    public int getmImgSrc() {
        return mImgSrc;
    }

    public void setmImgSrc(int mImgSrc) {
        this.mImgSrc = mImgSrc;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Fragment getFragment() {
        return mFragment;
    }

    public void setFragment(Fragment fragment) {
        mFragment = fragment;
    }

}
