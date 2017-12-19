package com.example.a90678.lkx_common_17_05_17_16_45.common.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by 90678 on 2017/6/1.
 */

//在子类复写这个方法
//    到这里我们只是写好了Fragment，在FragmentActivity中还需要对ViewPager设置一下，
// 让它每次只加载一个Fragment，ViewPager.setOffscreenPageLimit(int limit)，
// 其中参数可以设为0或者1，参数小于1时，会默认用1来作为参数，未设置之前，
// ViewPager会默认加载两个Fragment。所以，我们只需要调用下它，设置下加载Fragment个数即可。
//@Override
//protected void lazyLoad() {
//        if (!isPrepared || !isVisible || mHasLoadedOnce) {
//        return;
//        }

public abstract class BaseLazyFragment3 extends BaseFragment {

    /** Fragment当前状态是否可见 */
    protected boolean isVisible;

    boolean isLoaded;

    boolean isActivityCreate;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }

        if (getUserVisibleHint() && !isLoaded && isActivityCreate) {
            lazyLoad();
        }

        if (getUserVisibleHint() && isActivityCreate) {
            isVisiableAndActivityCreated();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isActivityCreate = true;

        if(getUserVisibleHint() && !isLoaded) {
            lazyLoad();
        }

        if (getUserVisibleHint()) {
            isVisiableAndActivityCreated();
        }
    }

    /**
     * 视图销毁的时候讲Fragment是否初始化的状态变为false
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isLoaded = false;
        isActivityCreate = false;

    }

    /**
     * 可见
     */
    protected void onVisible() {

    }


    /**
     * 不可见
     */
    protected void onInvisible() {


    }


    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    protected void lazyLoad() {
        isLoaded = true;
    }

    protected void isVisiableAndActivityCreated () {}
}