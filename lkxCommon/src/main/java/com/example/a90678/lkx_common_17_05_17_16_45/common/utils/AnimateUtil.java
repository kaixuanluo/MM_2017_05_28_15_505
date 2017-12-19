package com.example.a90678.lkx_common_17_05_17_16_45.common.utils;

import android.support.design.widget.TabLayout;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

/**
 * Created by 90678 on 2017/6/13.
 */

public class AnimateUtil {

    public static void scaleTabBig (TabLayout.Tab tab) {
        View customView = tab.getCustomView();
        if (customView == null) {
            return;
        }

//                //初始化
        Animation scaleAnimation = new ScaleAnimation(1.0f, 1.2f,1.0f,1.2f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//设置动画时间
        scaleAnimation.setDuration(150);
        scaleAnimation.setFillAfter(true);
        customView.startAnimation(scaleAnimation);
    }

    public static void scaleTabSmall (TabLayout.Tab tab) {
        View customView = tab.getCustomView();
        if (customView == null) {
            return;
        }

//                //初始化
        Animation scaleAnimation = new ScaleAnimation(1.2f, 1.0f,1.2f,1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//设置动画时间
        scaleAnimation.setDuration(150);
        scaleAnimation.setFillAfter(true);
        customView.startAnimation(scaleAnimation);
    }

}
