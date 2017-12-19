package com.example.a90678.mm_2017_05_28_15_503.videoPlayer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.example.a90678.lkx_common_17_05_17_16_45.common.utils.ViewUtil;
import com.example.a90678.lkx_common_17_05_17_16_45.common.view.activity.BaseNavigatorActivity;
import com.example.a90678.mm_2017_05_28_15_503.R;
import com.example.a90678.mm_2017_05_28_15_503.main.constant.Constanst;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * Created by 90678 on 2017/6/10.
 */

public class VideoPlayerActivity2 extends BaseNavigatorActivity {

    @Override
    public Fragment getFragment() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setContentView(R.layout.);

        setFinishOnTouchOutside(true);

//        WindowManager m = getWindowManager();
//        Display d = m.getDefaultDisplay(); // 涓鸿幏鍙栧睆骞曞銆侀珮
//        android.view.WindowManager.LayoutParams p = getWindow().getAttributes();
//        p.height = (int) (d.getHeight() * 0.5); // 楂樺害璁剧疆涓哄睆骞曠殑0.8
//        p.width = (int) (d.getWidth() * 1); // 瀹藉害璁剧疆涓哄睆骞曠殑0.7
//        p.gravity = Gravity.CENTER;// 鍦ㄥ簳閮ㄦ樉绀?
//        getWindow().setAttributes(p);
//
        Intent intent = getIntent();
        final String videoUrl = intent.getStringExtra(Constanst.VIDEO_URL);
        String title = intent.getStringExtra(Constanst.TITLE);
        String thumbUrl = intent.getStringExtra(Constanst.THUMB_URL);

        setContentView(R.layout.video_play_activity);
//        getSupportFragmentManager().beginTransaction().replace(R.id.video_play_activity_frl,
//                VideoPlayerFragment.newInstance(videoUrl, title, thumbUrl));

        ViewUtil.setFragment(this, R.id.video_play_activity_frl,
                VideoPlayerFragment.newInstance(videoUrl, title, thumbUrl));
    }

    public static Intent launchIntent(Context context, String videoUrl, String title, String thumbUrl) {
        Intent intent = new Intent(context, VideoPlayerActivity2.class);
        intent.putExtra(Constanst.VIDEO_URL, videoUrl);
        intent.putExtra(Constanst.TITLE, title);
        intent.putExtra(Constanst.THUMB_URL, thumbUrl);
        return intent;
    }

//    @Override
//    public void onBackPressed() {
//        if (JCVideoPlayer.backPress()) {
//            return;
//        }
//        super.onBackPressed();
//    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

}
