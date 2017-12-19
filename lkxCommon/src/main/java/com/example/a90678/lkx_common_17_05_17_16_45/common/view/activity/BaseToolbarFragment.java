package com.example.a90678.lkx_common_17_05_17_16_45.common.view.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a90678.lkx_common_17_05_17_16_45.R;

/**
 * 描述说明  <br/>
 * Author : luokaixuan <br/>
 * CreateDate : 2017/1/10 14:59 <br/>
 * Modified : luokaixuan <br/>
 * ModifiedDate : 2017/1/10 14:59 <br/>
 * Email : 1005949566@qq.com <br/>
 * Version 1.0
 */
public class BaseToolbarFragment extends BaseFragment {

    Toolbar mToolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ViewGroup viewGroup = addToolbarView(inflater, container, savedInstanceState);
        return viewGroup;
    }

    private ViewGroup addToolbarView(LayoutInflater inflater, @Nullable ViewGroup container,
                                @Nullable Bundle savedInstanceState) {
        View toolbarLayout = inflater.inflate(R.layout.layout_toolbar, container, false);
        LinearLayout llContainer = (LinearLayout) toolbarLayout.findViewById(R.id.toolbar_content);
        llContainer.addView(container);
        mToolbar = (Toolbar) toolbarLayout.findViewById(R.id.toolbar);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);

        //这里需要写在下面
        mToolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

//        setTitleCenter(mToolbar);

        return (ViewGroup) toolbarLayout;
    }

    protected void setToolbarVisiable(int visiable) {
        if (mToolbar != null) {
            mToolbar.setVisibility(visiable);
        }
    }

    protected void setHomeVisiable(boolean visiable) {
        ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(visiable);
        }
    }

//    private void setTitleCenter(Toolbar toolbar) {
//        int childCount = toolbar.getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            View child = toolbar.getChildAt(i);
//            if (child instanceof TextView) {
//                TextView childTitle = (TextView) child;
//                if (childTitle.getText().equals(toolbar.getTitle())) {
//                    int deviceWidth = getActivity().getWindowManager().getDefaultDisplay().getWidth();
//                    Paint p = childTitle.getPaint();
//                    float textWidth = p.measureText(childTitle.getText().toString());
//                    float tx = (deviceWidth - textWidth) / 2.0f - toolbar.getContentInsetLeft();
//                    childTitle.setTranslationX(tx);
//                    break;
//                }
//            }
//        }
//    }
}
