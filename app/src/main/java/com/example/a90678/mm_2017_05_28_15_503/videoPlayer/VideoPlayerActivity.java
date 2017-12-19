package com.example.a90678.mm_2017_05_28_15_503.videoPlayer;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.a90678.lkx_common_17_05_17_16_45.common.view.activity.BaseNavigatorActivity;
import com.example.a90678.mm_2017_05_28_15_503.main.constant.Constanst;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * Created by 90678 on 2017/6/4.
 */

public class VideoPlayerActivity extends BaseNavigatorActivity {

    public static Intent launchIntent(Context context, String videoUrl, String title, String thumbUrl) {
        Intent intent = new Intent(context, VideoPlayerActivity.class);
        intent.putExtra(Constanst.VIDEO_URL, videoUrl);
        intent.putExtra(Constanst.TITLE, title);
        intent.putExtra(Constanst.THUMB_URL, thumbUrl);
        return intent;
    }

    @Override
    public Fragment getFragment() {
        return VideoPlayerMoreFragment.newInstance();
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
