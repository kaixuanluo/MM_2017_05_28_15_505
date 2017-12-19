package com.example.a90678.mm_2017_05_28_15_503.navigator;

import android.content.Context;
import android.content.Intent;

import com.example.a90678.mm_2017_05_28_15_503.videoPlayer.VideoPlayerActivity;
import com.example.a90678.mm_2017_05_28_15_503.videoPlayer.VideoPlayerActivity2;

/**
 * Created by 90678 on 2017/6/4.
 */

public class NavigatorHelper {

    public static void launchVideoPlayerActivity(Context context, String videoUrl, String title, String thumbUrl) {
        Intent intent = VideoPlayerActivity.launchIntent(context, videoUrl, title, thumbUrl);
        context.startActivity(intent);
    }

    public static void launchVideoPlayerActivity2(Context context, String videoUrl, String title, String thumbUrl) {
        Intent intent = VideoPlayerActivity2.launchIntent(context, videoUrl, title, thumbUrl);
        context.startActivity(intent);
    }
}
