package com.example.a90678.lkx_common_17_05_17_16_45.common.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.a90678.lkx_common_17_05_17_16_45.R;
import com.ufreedom.floatingview.Floating;
import com.ufreedom.floatingview.FloatingBuilder;
import com.ufreedom.floatingview.FloatingElement;
import com.ufreedom.floatingview.effect.ScaleFloatingTransition;

/**
 * Created by 90678 on 2017/6/13.
 */

public class FloatingViewUtil2 {

    /**
     * 开始动画
     * @param activity
     * @param imgSrc
     * @param viewRoot 目标view
     */
    public static void floatStart1(Activity activity, int imgSrc, View viewRoot) {

        ImageView imageView = new ImageView(activity);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(viewRoot.getMeasuredWidth(),
                viewRoot.getMeasuredHeight()));
        imageView.setImageResource(imgSrc);

        FloatingViewUtil.PlaneFloating floatingTransition = new FloatingViewUtil.PlaneFloating(activity);
        final FloatingElement builder = new FloatingBuilder()
                .anchorView(viewRoot)
                .targetView(imageView)
//                        .offsetX(600)
//                        .offsetY(600)
                .floatingTransition(floatingTransition)
                .build();

        Floating floating = new Floating(activity);
        floating.startFloating(builder);

    }

    public static void floatStart2 (Activity activity, int imgSrc, View viewRoot) {

        ImageView imageView = new ImageView(activity);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(viewRoot.getMeasuredWidth(),
                viewRoot.getMeasuredHeight()));
        imageView.setImageResource(imgSrc);

        final FloatingElement builder = new FloatingBuilder()
                .anchorView(viewRoot)
                .targetView(imageView)
//                        .offsetX(600)
//                        .offsetY(-v.getMeasuredHeight())
                .floatingTransition(new ScaleFloatingTransition())
                .build();

        Floating floating = new Floating(activity);
        floating.startFloating(builder);
    }
}
