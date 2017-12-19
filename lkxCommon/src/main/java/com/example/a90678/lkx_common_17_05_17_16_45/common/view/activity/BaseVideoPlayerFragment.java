package com.example.a90678.lkx_common_17_05_17_16_45.common.view.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.a90678.lkx_common_17_05_17_16_45.R;
import com.example.a90678.lkx_common_17_05_17_16_45.common.utils.ViewUtil;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by 90678 on 2017/6/4.
 */

public class  BaseVideoPlayerFragment extends BaseFragment {

    protected JCVideoPlayerStandard mVp;

    public JCVideoPlayerStandard getmVp() {
        return mVp;
    }

    public void setmVp(JCVideoPlayerStandard mVp) {
        this.mVp = mVp;
    }

    public void initVideo (String videoUrl, int playStandard, String title, String thumbImageUrl, boolean isAutoPlay) {
//        getmVp().setUp(videoUrl, playStandard, title);
        getmVp().setUp(videoUrl, playStandard);
        ImageView thumbImageView = mVp.thumbImageView;
        ViewUtil.setImage(this.getContext(), thumbImageView, thumbImageUrl);
//        mVp.thumbImageView.setImageURI(Uri.parse(thumbImageUrl));
        if (isAutoPlay) {
            startVideo();
        }
    }

    public void initVideo (String videoUrl, String title, String thumbImageUrl, boolean isAutoPlay) {
        initVideo(videoUrl, JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, title, thumbImageUrl, isAutoPlay);
    }

    public void initVideo (String videoUrl, int playStandard, String title, String thumbImageUrl) {
        initVideo(videoUrl, playStandard, title, thumbImageUrl, true);
    }

    public void initVideo (String videoUrl, String title, String thumbImageUrl) {
        initVideo(videoUrl, JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, title, thumbImageUrl, true);
    }

    public void startVideo() {
        if (mVp != null) {
            mVp.startVideo();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frg_video_base, container, false);
        mVp = (JCVideoPlayerStandard) view.findViewById(R.id.base_video_player);

        return view;
    }
}
