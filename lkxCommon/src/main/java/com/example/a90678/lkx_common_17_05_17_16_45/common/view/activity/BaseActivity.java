package com.example.a90678.lkx_common_17_05_17_16_45.common.view.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.a90678.lkx_common_17_05_17_16_45.R;
import com.example.a90678.lkx_common_17_05_17_16_45.common.utils.SharedPreferenceUtil;
import com.example.a90678.lkx_common_17_05_17_16_45.common.utils.ViewUtil;
import com.example.a90678.lkx_common_17_05_17_16_45.common.view.StatusBarCompat;

/**
 * Created by 90678 on 2017/6/10.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public abstract Fragment getFragment();

    protected SharedPreferences themePrefres;

    protected Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        themePrefres = SharedPreferenceUtil.getThemePrefres(this);
        setBaseTheme();

        setContentView(R.layout.layout_navigat);

        //沉浸式状态栏
        StatusBarCompat.compat(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//
//        TextView tvTitle = (TextView) findViewById(R.id.toolbar_content_tv);
//        ViewUtil.setText(tvTitle, getTitle()+"");
    }

    protected void setBaseTheme() {
        String themeType = themePrefres.getString(SharedPreferenceUtil.THEM_TYPE, "");
        setBaseTheme(themeType);
    }

    public void setBaseTheme(String themeType) {

        int themeId = 0;
        switch (themeType) {
            case SharedPreferenceUtil.GREEN:
                themeId = R.style.AppTheme_NoActionBar_Green;
                break;
            case SharedPreferenceUtil.BLUE:
                themeId = R.style.AppTheme_NoActionBar_Blue;
                break;
//            case THEME_ORANGE:
//                themeId = R.style.AppTheme_Base_Orange;
//                break;
//            case THEME_TEAL:
//                themeId = R.style.AppTheme_Base_Teal;
//                break;
//            case THEME_BROWN:
//                themeId = R.style.AppTheme_Base_Brown;
//                break;
            case SharedPreferenceUtil.GREY:
                themeId = R.style.AppTheme_NoActionBar_Grey;
                break;
            case SharedPreferenceUtil.PURPLE:
                themeId = R.style.AppTheme_NoActionBar_Purple;
                break;
            case SharedPreferenceUtil.DEFAULT:
                themeId = R.style.AppTheme_NoActionBar_Default;
                break;

            case SharedPreferenceUtil.RED:
                themeId = R.style.AppTheme_NoActionBar_Red;
                break;

//            default:
//                themeId = R.style.AppTheme_Base_Default;
        }
        if (themeId != 0) {
            setTheme(themeId);
        }
        themePrefres.edit().putString(SharedPreferenceUtil.THEM_TYPE, themeType).commit();

    }
}
