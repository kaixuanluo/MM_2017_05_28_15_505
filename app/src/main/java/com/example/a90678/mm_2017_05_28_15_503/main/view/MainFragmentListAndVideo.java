package com.example.a90678.mm_2017_05_28_15_503.main.view;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a90678.lkx_common_17_05_17_16_45.common.utils.NotificationUtil;
import com.example.a90678.lkx_common_17_05_17_16_45.common.utils.TipUtils;
import com.example.a90678.lkx_common_17_05_17_16_45.common.utils.ViewUtil;
import com.example.a90678.lkx_common_17_05_17_16_45.common.view.DialogUtil.DialogCommon;
import com.example.a90678.lkx_common_17_05_17_16_45.common.view.activity.BaseListFragment;
import com.example.a90678.lkx_common_17_05_17_16_45.common.view.layout.RecyclerViewPlus;
import com.example.a90678.mm_2017_05_28_15_503.R;
import com.example.a90678.mm_2017_05_28_15_503.main.constant.Constanst;
import com.example.a90678.mm_2017_05_28_15_503.main.data.MainFrgAdvResult;
import com.example.a90678.mm_2017_05_28_15_503.main.presenter.MainFrgAdvPresenter3;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerManager;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

import static fm.jiecao.jcvideoplayer_lib.JCVideoPlayer.SCREEN_WINDOW_FULLSCREEN;

/**
 * Created by 90678 on 2017/6/2.
 */

public class MainFragmentListAndVideo extends BaseListFragment<MainFrgAdvResult.MainFrgAdv,
        MainFrgAdvResult, MainFrgAdvPresenter3, RecyclerView.ViewHolder> {

    protected String type;

    MainFrgAdvPresenter3 mainFrgAdvPresenter;

    boolean isVideoPlayerView;

    int clickPosition;

    private MainFrgAdvResult.MainFrgAdv downloadMainFrgAdv;

    NotificationUtil notificationUtil;

    public static MainFragmentListAndVideo newInstance() {

        Bundle args = new Bundle();

        MainFragmentListAndVideo fragment = new MainFragmentListAndVideo();
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
        notificationUtil = new NotificationUtil(MainFragmentListAndVideo.this.getActivity());
    }

    @Override
    protected RecyclerView.ViewHolder createItemViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        if (isVideoPlayerView) {
            return new MainFrgVideoPlayViewHolder(LayoutInflater.from(MainFragmentListAndVideo.this.getContext())
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
                ViewUtil.setImage(MainFragmentListAndVideo.this.getContext(),
                        jcUserActionStandard.thumbImageView, mainFrgAdv.getUrlImg());
                jcUserActionStandard.setUp(mainFrgAdv.getUrlVideo(), JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL);

                ViewUtil.setViewVisiable(holder1.tvGg, mainFrgAdv.isAd());

                if (clickPosition == position) {
                    if (mainFrgAdv.isAd()) {
//                        jcUserActionStandard.thumbImageView.setOnClickListener(null);
//                        jcUserActionStandard.startButton.setVisibility(View.GONE);
//                        TipUtils.showTip(this.getContext(), "开始下载。。。");
                    }
                    jcUserActionStandard.thumbImageView.performClick();
                }
            }
        } else {
            if (holder instanceof MainFrgAdvViewHolder) {
                ViewUtil.setImage(this.getContext(), ((MainFrgAdvViewHolder) holder).mainFrg11AdvIv, mainFrgAdv.getUrlImg());
                ViewUtil.setText(((MainFrgAdvViewHolder) holder).mainFrg11AdvTv, mainFrgAdv.getTitle());
                ViewUtil.setText(((MainFrgAdvViewHolder) holder).mainFrg11SizeTv,
                        Formatter.formatFileSize(getContext(), mainFrgAdv.getFileLength()));
                ViewUtil.setViewVisiable(((MainFrgAdvViewHolder) holder).tvGg, mainFrgAdv.isAd());
            }
        }

    }

    @Override
    public MainFrgAdvPresenter3 getPresenter() {
        mainFrgAdvPresenter = new MainFrgAdvPresenter3(type, this, this);
        return mainFrgAdvPresenter;
    }

    public class MainFrgAdvViewHolder extends RecyclerView.ViewHolder {
        public ImageView mainFrg11AdvIv;
        public TextView mainFrg11AdvTv;
        public TextView mainFrg11SizeTv;
        public TextView tvGg;

        MainFrgAdvViewHolder(View view) {
            super(view);
            mainFrg11AdvIv = (ImageView) view.findViewById(R.id.main_frg_1_1_adv_iv);
            mainFrg11AdvTv = (TextView) view.findViewById(R.id.main_frg_1_1_adv_tv);
            mainFrg11SizeTv = (TextView) view.findViewById(R.id.main_frg_1_1_size_tv);
            tvGg = (TextView) itemView.findViewById(R.id.main_video_play_item_gg_tv);
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
            setVideoPlayerManagerAndInitView();
//            getRcv().scrollToPosition(position);
        }

        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (layoutManager != null && layoutManager instanceof LinearLayoutManager) {
            DisplayMetrics outMetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
            ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(position,
//                    (int) getResources().getDimension(R.dimen.video_center));
                    outMetrics.heightPixels/5);
        }

    }

    @Override
    public void onItemLongClickListener(final MainFrgAdvResult.MainFrgAdv mainFrgAdv) {
        super.onItemLongClickListener(mainFrgAdv);

        downloadMainFrgAdv = mainFrgAdv;

        if (downloadMainFrgAdv == null) {
            return;
        }

        Log.e("show dialog "," show dialog ");

        DialogCommon.showDownloadDialog(getContext(), mainFrgAdv.getTitle(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                CheckPermission.requestPermission(MainFragmentListAndVideo.this.getActivity(),
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                        Constanst.CHECK_PERMISSION_WRITE_EXTERNAL_SD_CARD);

//                startDownload();
                new DownloadUtil().startDownload(MainFragmentListAndVideo.this.getActivity(),
                        mainFrgAdv.getTitle(), mainFrgAdv.getUrlVideo(), mainFrgAdv.getUrlVideoSub(),
                        mainFrgAdv.getFileLength());
//                startUpload();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == Constanst.CHECK_PERMISSION_WRITE_EXTERNAL_SD_CARD) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                startDownload();
            } else {
                // Permission Denied
                TipUtils.showTip(this.getContext(), "您没有授权该权限，请在设置中打开授权");
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void setVideoPlayerManagerAndInitView () {

        if (isVideoPlayerView) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
            setLayoutManager(linearLayoutManager);
            initView();
        } else {
            int lastVisibleItem = getRcv().getLastVisibleItem();
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
            setLayoutManager(gridLayoutManager);
            initView();
            gridLayoutManager.scrollToPosition(lastVisibleItem - 3);
        }
        JCVideoPlayer.releaseAllVideos();
        JCVideoPlayer.backPress();
    }

    public class MainFrgVideoPlayViewHolder extends RecyclerView.ViewHolder {

        JCVideoPlayerStandard jcUserActionStandard;
        TextView tvGg;

        public MainFrgVideoPlayViewHolder(View itemView) {
            super(itemView);
            jcUserActionStandard = (JCVideoPlayerStandard) itemView.findViewById(R.id.main_video_play_item);
            tvGg = (TextView) itemView.findViewById(R.id.main_video_play_item_gg_tv);
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

                        setVideoPlayerManagerAndInitView();

                        return true;
                    } else {
                        return false;
                    }
                }
            }
            return false;
        }
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        if (isVideoPlayerView) {
//            inflater.inflate(R.menu.main_sort_video, menu);
//        } else {
//            inflater.inflate(R.menu.main_sort_list, menu);
//        }
//
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        Log.e("d " +item.getItemId()," enu_set " + R.id.menu_set);
//        switch (item.getItemId()) {
//            case R.id.menu_sort_list:
//                isVideoPlayerView = true;
//                setVideoPlayerManagerAndInitView();
//                break;
//            case R.id.menu_sort_video:
//                isVideoPlayerView = false;
//                setVideoPlayerManagerAndInitView();
//                break;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

}

