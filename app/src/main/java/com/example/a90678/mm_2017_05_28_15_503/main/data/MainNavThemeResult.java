package com.example.a90678.mm_2017_05_28_15_503.main.data;

import com.example.a90678.lkx_common_17_05_17_16_45.common.bean.BaseListResult;
import com.example.a90678.lkx_common_17_05_17_16_45.common.utils.SharedPreferenceUtil;
import com.example.a90678.mm_2017_05_28_15_503.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 90678 on 2017/6/6.
 */

public class MainNavThemeResult extends BaseListResult<MainNavThemeResult.MainNavTheme> {

    @Override
    public List<MainNavTheme> getList() {
        ArrayList<MainNavTheme> mainNavThemes = new ArrayList<>();
        mainNavThemes.add(new MainNavTheme(SharedPreferenceUtil.RED, R.color.colorPrimaryRed, R.style.AppTheme_NoActionBar_Red));
        mainNavThemes.add(new MainNavTheme(SharedPreferenceUtil.GREEN, R.color.colorPrimaryGreen, R.style.AppTheme_NoActionBar_Green));
        mainNavThemes.add(new MainNavTheme(SharedPreferenceUtil.BLUE, R.color.colorPrimaryBlue, R.style.AppTheme_NoActionBar_Blue));
        mainNavThemes.add(new MainNavTheme(SharedPreferenceUtil.PURPLE, R.color.colorPrimaryPurple, R.style.AppTheme_NoActionBar_Purple));
        mainNavThemes.add(new MainNavTheme(SharedPreferenceUtil.GREY, R.color.colorPrimaryGrey, R.style.AppTheme_NoActionBar_Grey));
        mainNavThemes.add(new MainNavTheme(SharedPreferenceUtil.DEFAULT, R.color.colorPrimary, R.style.AppTheme_NoActionBar_Default));
        return mainNavThemes;
    }

    public static class MainNavTheme {
        int themeResId;
        int colorResId;
        String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public MainNavTheme(String type, int colorResId, int themeResId) {
            this.themeResId = themeResId;
            this.colorResId = colorResId;
            this.type = type;
        }

        public int getThemeResId() {
            return themeResId;
        }

        public void setThemeResId(int themeResId) {
            this.themeResId = themeResId;
        }

        public int getColorResId() {
            return colorResId;
        }

        public void setColorResId(int colorResId) {
            this.colorResId = colorResId;
        }
    }
}
