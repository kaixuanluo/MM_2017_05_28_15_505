package com.example.a90678.lkx_common_17_05_17_16_45.common.permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

/**
 * Created by 90678 on 2017/6/19.
 */

public class CheckPermission {

    private static boolean checkSdPermission(Context context, String permission) {
        return !isMarshmallow() || isGranted_(context, permission);
    }

    private static boolean isMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    private static boolean isGranted_(Context context, String permission) {
        int checkSelfPermission = ActivityCompat.checkSelfPermission(context, permission);
        return checkSelfPermission == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermission(Activity context, String permission, int requestCode) {
        if (!checkSdPermission(context, permission)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {

            } else {
                ActivityCompat.requestPermissions(context, new String[]{permission}, requestCode);
            }
        } else {
            //直接执行相应操作了
        }
    }
}
