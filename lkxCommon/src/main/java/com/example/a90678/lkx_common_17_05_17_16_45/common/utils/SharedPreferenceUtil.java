package com.example.a90678.lkx_common_17_05_17_16_45.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * 描述说明  <br/>
 * Author : luokaixuan <br/>
 * CreateDate : 2016/10/12 13:39 <br/>
 * Modified : luokaixuan <br/>
 * ModifiedDate : 2016/10/12 13:39 <br/>
 * Email : 1005949566@qq.com <br/>
 * Version 1.0
 */
public class SharedPreferenceUtil {

    public static final String THEM = "them";
    public static final String THEM_TYPE = "themType";
    public static final String RED = "red";
    public static final String BLUE = "blue";
    public static final String GREY = "grey";
    public static final String PURPLE = "populer";
    public static final String GREEN = "green";
    public static final String DEFAULT = "default";

    public static SharedPreferences getThemePrefres (Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                THEM, MODE_PRIVATE);

        return sharedPreferences;
    }
}
