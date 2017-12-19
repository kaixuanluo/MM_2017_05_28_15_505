package com.example.a90678.mm_2017_05_28_15_503.videoPlayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a90678.lkx_common_17_05_17_16_45.common.utils.ViewUtil;
import com.example.a90678.lkx_common_17_05_17_16_45.common.view.activity.BaseFragment;
import com.example.a90678.mm_2017_05_28_15_503.R;
import com.example.a90678.mm_2017_05_28_15_503.main.constant.Constanst;

/**
 * Created by 90678 on 2017/6/4.
 */

public class VideoPlayerMoreFragment extends BaseFragment {

    public static VideoPlayerMoreFragment newInstance() {

        Bundle args = new Bundle();

        VideoPlayerMoreFragment fragment = new VideoPlayerMoreFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.video_more_frg, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Intent intent = getActivity().getIntent();
        final String videoUrl = intent.getStringExtra(Constanst.VIDEO_URL);
        String title = intent.getStringExtra(Constanst.TITLE);
        String thumbUrl = intent.getStringExtra(Constanst.THUMB_URL);

        final VideoPlayerFragment videoPlayerFragment = VideoPlayerFragment.newInstance(videoUrl, title, thumbUrl);
        ViewUtil.setFragment(this.getActivity(), R.id.video_more_ll_1,
                videoPlayerFragment);
        VideoPlayerMoreListFragment moreListFragment = VideoPlayerMoreListFragment
                .newInstance(new VideoPlayerMoreListFragment.VideoMoreItemClick() {
                    @Override
                    public void onClick(String urlVideo, String title, String urlImg) {
                        videoPlayerFragment.initVideo(urlVideo, title, urlImg);
                    }
                });
        ViewUtil.setFragment(this.getActivity(), R.id.video_more_ll_2,
                moreListFragment);
    }
}
