package com.example.a90678.mm_2017_05_28_15_503.main.view;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.a90678.lkx_common_17_05_17_16_45.common.utils.ViewUtil;
import com.example.a90678.lkx_common_17_05_17_16_45.common.view.activity.BaseLoadingLayout;
import com.example.a90678.mm_2017_05_28_15_503.R;
import com.example.a90678.mm_2017_05_28_15_503.main.data.MainFrgAdvResult;
import com.example.a90678.mm_2017_05_28_15_503.main.presenter.MainFrgAdvPresenter2;
import com.example.a90678.mm_2017_05_28_15_503.navigator.NavigatorHelper;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 90678 on 2017/6/7.
 */

public class MainAdvHeadLayout extends BaseLoadingLayout<MainFrgAdvResult, MainFrgAdvPresenter2>{

    private static List<MainFrgAdvResult.MainFrgAdv> mainFrgAdvs = new ArrayList<>();

    RollPagerView mRollPagerView;

    protected TabLayout mMainTl;

    TestLoopAdapter mLoopAdapter;

    Context mContext;

    public MainAdvHeadLayout(Context context) {
        mContext = context;
    }

    public void startLoadHead() {
        getPresenter().loadData();
    }

    @Override
    public void success(MainFrgAdvResult data) {
        super.success(data);
        mainFrgAdvs = data.getList();
        mLoopAdapter = new TestLoopAdapter(mContext, mRollPagerView);
        mRollPagerView.setAdapter(mLoopAdapter);
    }

    public View initHeardLayout() {

        View view = LayoutInflater.from(mContext).inflate(R.layout.main_frg_1_1_adv_1_3, null);
        mMainTl = (TabLayout) view.findViewById(R.id.main_tl);
        mRollPagerView = (RollPagerView) view.findViewById(R.id.main_vp);
//        mViewPager = mRollPagerView.getViewPager();

        Log.e(" 进入 "," 进入 ");
        mRollPagerView.setHintView(new ColorPointHintView(mContext, Color.YELLOW, Color.WHITE));

//        mLoopAdapter = new TestLoopAdapter(context, mRollPagerView);
//        mRollPagerView.setAdapter(mLoopAdapter);

//        rollViewPager.pause()
//        rollViewPager.resume()
//        rollViewPager.isPlaying()

        mRollPagerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                MainFrgAdvResult.MainFrgAdv adv = mainFrgAdvs.get(position);
                NavigatorHelper.launchVideoPlayerActivity(mContext,
                        adv.getUrlVideo(), adv.getTitle(), adv.getUrlImg());
            }
        });

        return addLoadingView(mContext, view);
    }

    @Override
    public MainFrgAdvPresenter2 getPresenter() {
        return new MainFrgAdvPresenter2(this);
    }

    private class TestLoopAdapter extends LoopPagerAdapter {

        Context mContext;
        public TestLoopAdapter(Context context, RollPagerView viewPager) {
            super(viewPager);
            this.mContext =context;
        }

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            ViewUtil.setImage(mContext, view, mainFrgAdvs.get(position).getUrlImg());
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }

        @Override
        public int getRealCount() {
            return mainFrgAdvs.size();
        }
    }
}
