package com.example.a90678.mm_2017_05_28_15_503.videoPlayer;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.a90678.lkx_common_17_05_17_16_45.common.view.adapter.BaseHFAdapter;
import com.example.a90678.mm_2017_05_28_15_503.main.data.MainFrgAdvResult;
import com.example.a90678.mm_2017_05_28_15_503.main.view.MainFragment1_1_adv3;

/**
 * Created by 90678 on 2017/6/4.
 */

public class VideoPlayerMoreListFragment extends MainFragment1_1_adv3 {

    public static VideoMoreItemClick mVideoMoreItemClick;

    public static VideoPlayerMoreListFragment newInstance(VideoMoreItemClick videoMoreItemClick) {

        mVideoMoreItemClick = videoMoreItemClick;

        Bundle args = new Bundle();

        VideoPlayerMoreListFragment fragment = new VideoPlayerMoreListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initRecyclerView(BaseHFAdapter baseHFAdapter, RecyclerView.LayoutManager layoutManager) {
        if (getRcv() != null) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 2);
            gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            setLayoutManager(gridLayoutManager);
            getRcv().initRecyclerView(this.getContext(), getBaseHFAdapter(), getLayoutManager());
        }
    }

//    @Override
//    protected void bindItemViewHolder(MainFrgAdvViewHolder holder, MainFrgAdvResult.MainFrgAdv mainFrgAdv, int position) {
//
////        ViewUtil.setImage(this.getContext(), holder.mainFrg11AdvIv, mainFrgAdv.getUrlImg());
//
//        Glide
//                .with(VideoPlayerMoreListFragment.this.getActivity())
//                .load(mainFrgAdv.getUrlImg())
////                .fitCenter()
//                .centerCrop()
////                .thumbnail(0.3f)
//                .placeholder(com.example.a90678.lkx_common_17_05_17_16_45.R.drawable.data_empty)
//                .bitmapTransform(new RoundedCornersTransformation(getContext(), 10, 0))
//                .into(holder.mainFrg11AdvIv);
//
//        ViewUtil.setText(holder.mainFrg11AdvTv, mainFrgAdv.getTitle());
//
//    }

    @Override
    public void onItemClickListener(MainFrgAdvResult.MainFrgAdv mainFrgAdv) {
//        super.onItemClickListener(mainFrgAdv);

        String title = mainFrgAdv.getTitle();
        String urlImg = mainFrgAdv.getUrlImg();
        String urlVideo = mainFrgAdv.getUrlVideo();

        Log.e("video ", urlVideo);
        Log.e("title ", title);
        Log.e("thumburl ", urlImg);

        mVideoMoreItemClick.onClick(urlVideo, title, urlImg);
//        setToolbarVisiable(View.GONE);
    }

    public interface VideoMoreItemClick {
        void onClick(String urlVideo, String title, String urlImg);
    }

}
