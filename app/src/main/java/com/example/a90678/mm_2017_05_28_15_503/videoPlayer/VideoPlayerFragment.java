package com.example.a90678.mm_2017_05_28_15_503.videoPlayer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.a90678.lkx_common_17_05_17_16_45.common.view.activity.BaseVideoPlayerFragment;
import com.example.a90678.mm_2017_05_28_15_503.main.constant.Constanst;

/**
 * Created by 90678 on 2017/6/4.
 */

public class VideoPlayerFragment extends BaseVideoPlayerFragment {

    public static VideoPlayerFragment newInstance(String videoUrl, String title, String thumbUrl) {

        Bundle args = new Bundle();
        args.putString(Constanst.VIDEO_URL, videoUrl);
        args.putString(Constanst.TITLE, title);
        args.putString(Constanst.THUMB_URL, thumbUrl);
        VideoPlayerFragment fragment = new VideoPlayerFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//
//        mVp.setUp("http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4"
//                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "嫂子闭眼睛");
//        mVp.thumbImageView.setImageURI(Uri.parse("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640"));
//
//        mVp.startVideo();

        Bundle arguments = getArguments();
        if (arguments != null) {
            String videoUrl = arguments.getString(Constanst.VIDEO_URL);
            String title = arguments.getString(Constanst.TITLE);
            String thumbUrl = arguments.getString(Constanst.THUMB_URL);
            initVideo(videoUrl, title, thumbUrl);
        } else {
            Log.e(" 得到 argum kong "," dedao kong ");
        }

        getmVp().startWindowFullscreen();

//        initVideo("http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4",
//                "嫂子闭眼睛",
//                "http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640");
    }
}
