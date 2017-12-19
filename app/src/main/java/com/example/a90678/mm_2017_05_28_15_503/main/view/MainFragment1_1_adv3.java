package com.example.a90678.mm_2017_05_28_15_503.main.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a90678.lkx_common_17_05_17_16_45.common.utils.ViewUtil;
import com.example.a90678.lkx_common_17_05_17_16_45.common.view.activity.BaseListFragment;
import com.example.a90678.lkx_common_17_05_17_16_45.common.view.layout.RecyclerViewPlus;
import com.example.a90678.mm_2017_05_28_15_503.R;
import com.example.a90678.mm_2017_05_28_15_503.main.data.MainFrgAdvResult;
import com.example.a90678.mm_2017_05_28_15_503.main.presenter.MainFrgAdvPresenter;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerManager;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

import static fm.jiecao.jcvideoplayer_lib.JCVideoPlayer.SCREEN_WINDOW_FULLSCREEN;

/**
 * Created by 90678 on 2017/6/2.
 */

public class MainFragment1_1_adv3 extends BaseListFragment<MainFrgAdvResult.MainFrgAdv,
        MainFrgAdvResult, MainFrgAdvPresenter, RecyclerView.ViewHolder> {

    MainFrgAdvPresenter mainFrgAdvPresenter;

    boolean isVideoPlayerView;

    RecyclerView.ViewHolder clickHolder;

    int clickPosition;

    public static MainFragment1_1_adv3 newInstance() {

        Bundle args = new Bundle();

        MainFragment1_1_adv3 fragment = new MainFragment1_1_adv3();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        //监听back必须设置的
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        //然后在写这个监听器
        view.setOnKeyListener(new MyKeyListner());
        return view;
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
////        setToolbarVisiable(View.GONE);
////        loadData();
//
//        videoPlayerViewScroll();
//    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        getPresenter().loadData();
    }

    @Override
    protected RecyclerView.ViewHolder createItemViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        if (isVideoPlayerView) {
            return new MainFrgVideoPlayViewHolder(LayoutInflater.from(MainFragment1_1_adv3.this.getContext())
                    .inflate(R.layout.main_video_play_item, parent, false));
        } else {
            return new MainFrgAdvViewHolder(inflater.inflate(R.layout.main_frg_1_1_adv_item, parent, false));
        }
    }

    @Override
    protected void bindItemViewHolder(RecyclerView.ViewHolder holder, MainFrgAdvResult.MainFrgAdv mainFrgAdv, int position) {

        if (isVideoPlayerView) {
            if (holder instanceof MainFrgVideoPlayViewHolder) {
                MainFrgVideoPlayViewHolder holder1 = (MainFrgVideoPlayViewHolder) holder;
                JCVideoPlayerStandard jcUserActionStandard = holder1.jcUserActionStandard;
                ViewUtil.setImage(MainFragment1_1_adv3.this.getContext(),
                        jcUserActionStandard.thumbImageView, mainFrgAdv.getUrlImg());

                jcUserActionStandard.setUp(mainFrgAdv.getUrlVideo(), JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL);

                if (clickPosition == position) {
                    clickHolder = holder;
                    jcUserActionStandard.startVideo();
                }
            }
        } else {
            if (holder instanceof MainFrgAdvViewHolder) {
                ViewUtil.setImage(this.getContext(), ((MainFrgAdvViewHolder) holder).mainFrg11AdvIv, mainFrgAdv.getUrlImg());
                ViewUtil.setText(((MainFrgAdvViewHolder) holder).mainFrg11AdvTv, mainFrgAdv.getTitle());
            }
        }

    }

    @Override
    public MainFrgAdvPresenter getPresenter() {
        mainFrgAdvPresenter = new MainFrgAdvPresenter(this, this);
        return mainFrgAdvPresenter;
    }

    public class MainFrgAdvViewHolder extends RecyclerView.ViewHolder {
        public ImageView mainFrg11AdvIv;
        public TextView mainFrg11AdvTv;

        MainFrgAdvViewHolder(View view) {
            super(view);
            mainFrg11AdvIv = (ImageView) view.findViewById(R.id.main_frg_1_1_adv_iv);
            mainFrg11AdvTv = (TextView) view.findViewById(R.id.main_frg_1_1_adv_tv);
        }
    }

//    @Override
//    public void onItemClickListener(MainFrgAdvResult.MainFrgAdv mainFrgAdv) {
//        super.onItemClickListener(mainFrgAdv);
//
//        NavigatorHelper.launchVideoPlayerActivity2(this.getContext(),
//                mainFrgAdv.getUrlVideo(), mainFrgAdv.getTitle(), mainFrgAdv.getUrlImg());
//
//    }

    @Override
    public void onItemClickListener(MainFrgAdvResult.MainFrgAdv mainFrgAdv, int position) {
        super.onItemClickListener(mainFrgAdv, position);

        clickPosition = position;

        if (isVideoPlayerView) {

        } else {
            isVideoPlayerView = true;
            setVideoPlayerManager();
            initView();
//            getRcv().scrollToPosition(position);
        }

        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (layoutManager != null && layoutManager instanceof LinearLayoutManager) {
            ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(position,
                    (int) getResources().getDimension(R.dimen.video_center));
        }

    }

    void setVideoPlayerManager () {
        if (isVideoPlayerView) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
            setLayoutManager(linearLayoutManager);
        } else {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
            setLayoutManager(gridLayoutManager);
        }
    }

    public class MainFrgVideoPlayViewHolder extends RecyclerView.ViewHolder {

        JCVideoPlayerStandard jcUserActionStandard;

        public MainFrgVideoPlayViewHolder(View itemView) {
            super(itemView);
            jcUserActionStandard = (JCVideoPlayerStandard) itemView.findViewById(R.id.main_video_play_item);
            jcUserActionStandard.backButton.setVisibility(View.GONE);
            jcUserActionStandard.tinyBackImageView.setVisibility(View.GONE);
        }
    }

    public class MyKeyListner implements View.OnKeyListener {
        @Override
        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                //这边判断,如果是back的按键被点击了   就自己拦截实现掉
                if (i == KeyEvent.KEYCODE_BACK) {
//                    Toast.makeText(getContext(), "BACK拦截", Toast.LENGTH_SHORT).show();

//                    return true;//表示处理了

                    RecyclerViewPlus rcv = getRcv();
                    if (rcv == null) {
                        return false;
                    }
                    JCVideoPlayer currentJcvd = JCVideoPlayerManager.getCurrentJcvd();
                    if (currentJcvd != null && currentJcvd.currentScreen == SCREEN_WINDOW_FULLSCREEN){
                        JCVideoPlayer.backPress();
                        return true;
                    }
                    if (isVideoPlayerView) {

                        isVideoPlayerView = false;

                        JCVideoPlayer.releaseAllVideos();
                        JCVideoPlayer.backPress();

                        setVideoPlayerManager();
                        initView();
                        rcv.scrollToPosition(rcv.getLastVisibleItem()-4);
                        return true;
                    } else {
                        return false;
                    }
                }
            }
            return false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        JCVideoPlayer.releaseAllVideos();
        JCVideoPlayer.backPress();
    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (!getUserVisibleHint()) {
            JCVideoPlayer.releaseAllVideos();
        }
    }

    @Override
    protected void onInvisible() {
        super.onInvisible();

        JCVideoPlayer.releaseAllVideos();
    }
}

