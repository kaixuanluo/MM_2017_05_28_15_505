package com.example.a90678.lkx_common_17_05_17_16_45.common.view.DialogUtil;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.a90678.lkx_common_17_05_17_16_45.common.utils.DirUtil;
import com.example.a90678.lkx_common_17_05_17_16_45.common.utils.FileUtil;

/**
 * Created by 90678 on 2017/6/19.
 */

public class DialogCommon {

    public static void showDialog (Context context) {
        Dialog dialog = new Dialog(context);
        dialog.show();
    }

    public static void showDownloadDialog (Context context, String fileName,
                                    DialogInterface.OnClickListener onClickListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("文件将会保存在 凯旋视频 文件夹"+"\n"+"是否下载"+fileName);
        builder.setPositiveButton("下载", onClickListener);
        builder.setNegativeButton("取消", null);
        builder.show();
    }
}
