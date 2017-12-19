package com.example.a90678.lkx_common_17_05_17_16_45.common.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a90678.lkx_common_17_05_17_16_45.R;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.MaskTransformation;

/**
 * Created by 90678 on 2017/5/28.
 */

public class ViewUtil {

    public static void setText(TextView tv, String text) {
        if (tv != null) {
            if (!TextUtils.isEmpty(text)) {
                tv.setText(text);
            } else {
                tv.setText("");
            }
        }
    }

    public static void setFragment(FragmentActivity context, int resId, Fragment frg) {
        if (context == null) {
            return;
        }
        if (resId == 0) {
            return;
        }
        if (frg == null) {
            return;
        }
        context
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(resId, frg)
                .commit();
    }

    public static void setImage (Context context, ImageView iv, String imageUrl) {

        Glide
                .with(context)
                .load(imageUrl)
//                .fitCenter()
                .centerCrop()
                .placeholder(R.drawable.data_empty2)
                .into(iv);
    }

    public static void setImageBlur (Context context, ImageView iv, String imageUrl) {

        Glide
                .with(context)
                .load(imageUrl)
//                .fitCenter()
                .centerCrop()
                .placeholder(R.drawable.data_empty2)
                .bitmapTransform(new BlurTransformation(context, 25, 1))
                .into(iv);
    }

    public static void setImage2 (Context context, ImageView iv, String imageUrl) {

        Glide
                .with(context)
                .load(imageUrl)
//                .fitCenter()
//                .centerCrop()
                .placeholder(R.drawable.data_empty2)
                .into(iv);
    }

    public static void setImageCircle(Context context, ImageView iv, int imgSrc) {

        Glide
                .with(context)
                .load(imgSrc)
//                .fitCenter()
//                .centerCrop()
//                .placeholder(R.drawable.data_empty)
                .bitmapTransform(new CropCircleTransformation(context))
                .into(iv);
    }

    /**
     Current Activity instance will go through its lifecycle to onDestroy() and a new instance then created after it.
     */
    @SuppressLint("NewApi")
    public static final void recreateActivityCompat(final Activity a) {
        Log.e("recreate ","recrete ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            a.recreate();
        } else {
            final Intent intent = a.getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            a.finish();
            a.overridePendingTransition(0, 0);
            a.startActivity(intent);
            a.overridePendingTransition(0, 0);
        }
    }

    public static int dip2px(Context context, float dp) {
        Resources r = context.getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        return Math.round(px);
    }

    public static int getScreenWidth(Context context){
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;

    }
    public static int getScreenHeight(Context context){
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    public static void setViewVisiable (View view, boolean visiable) {
        if (view != null) {
            view.setVisibility(visiable? View.VISIBLE : View.GONE);
        }
    }

    public static void setViewVisiable (View view, int visiable) {
        if (view != null) {
            view.setVisibility(visiable);
        }
    }
}
