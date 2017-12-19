package com.example.a90678.lkx_common_17_05_17_16_45.common.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 90678 on 2017/6/2.
 */

public class TipUtils {

    public static void showTip (Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
