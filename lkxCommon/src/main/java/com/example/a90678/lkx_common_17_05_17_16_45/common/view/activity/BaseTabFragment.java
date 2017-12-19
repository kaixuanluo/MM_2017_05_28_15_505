package com.example.a90678.lkx_common_17_05_17_16_45.common.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.Tab;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.a90678.lkx_common_17_05_17_16_45.R;
import com.example.a90678.lkx_common_17_05_17_16_45.common.utils.AnimateUtil;
import com.example.a90678.lkx_common_17_05_17_16_45.common.utils.TipUtils;

import java.util.List;


/**
 * 描述说明  <br/>
 * Author : luokaixuan <br/>
 * CreateDate : 2017/1/11 14:25 <br/>
 * Modified : luokaixuan <br/>
 * ModifiedDate : 2017/1/11 14:25 <br/>
 * Email : 1005949566@qq.com <br/>
 * Version 1.0
 */
public abstract class BaseTabFragment extends BaseLazyFragment3 {

    LinearLayout mLlContent;

    protected TabLayout mMainTl;

    protected ViewPager mViewPager;

    protected View mViewLine;

    public TabLayout getMainTl() {
        return mMainTl;
    }

    public abstract List<ItemTab> getTabList();

    public ViewPager getmViewPager() {
        return mViewPager;
    }

    protected void initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        mLlContent = (LinearLayout)
                inflater.inflate(R.layout.layout_linear_layout, container, false).findViewById(R.id.sub_ll);

        mLlContent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        View viewTab = inflater.inflate(R.layout.layout_tab_layout, container, false);
        mMainTl = (TabLayout) viewTab.findViewById(R.id.sub_tl);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mMainTl.setLayoutParams(layoutParams);

        mViewLine = inflater.inflate(R.layout.view_line_h_1, container, false);
        LinearLayoutCompat.LayoutParams layoutParams1 = new LinearLayoutCompat.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                (int) getResources().getDimension(R.dimen.line_height_1));
        mViewLine.setLayoutParams(layoutParams1);
//        layoutParams1.setMargins(0, 0.3, 0, 0.3);

        View viewPager = inflater.inflate(R.layout.layout_view_pager, container, false);
        mViewPager = (ViewPager) viewPager.findViewById(R.id.sub_vp);

        mViewPager.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                 LinearLayout.LayoutParams.MATCH_PARENT, 1));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getMainTl().addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                AnimateUtil.scaleTabBig(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                AnimateUtil.scaleTabSmall(tab);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();

        List<ItemTab> tabList = getTabList();
        if (tabList != null) {
            initTab(tabList);
        } else {
            TipUtils.showTip(this.getContext(), "tablist+为空");
        }
    }

    protected void initTab(final List<ItemTab> tabList) {
        if (tabList != null && tabList.size() > 0) {
            TabLayout mainTl = getMainTl();

            for (int i = 0; i < tabList.size(); i++) {
                ItemTab itemTab = tabList.get(i);
                Tab tab = mainTl.newTab();
                mainTl.addTab(tab.setText(itemTab.getTitle()));
            }

            mViewPager.setAdapter(new TabAdapter(getChildFragmentManager(), tabList));
            mViewPager.setOffscreenPageLimit(100);
//            mainTl.addOnTabSelectedListener(new TLSelectedListener(tabList));
//            mainTl.addOnTabSelectedListener(new MyTabSelectListener(getmViewPager()));

            mainTl.setupWithViewPager(mViewPager);
            mainTl.setSelectedTabIndicatorHeight(0);
            mainTl.setTabMode(TabLayout.MODE_FIXED);
            mainTl.setTabGravity(TabLayout.GRAVITY_FILL);
        }
    }

    protected void setTabVisiable(int visiblity) {
        if (mMainTl != null) {
            mMainTl.setVisibility(visiblity);
        }
    }

    protected void setTabHeight (int tabHeight) {
        if (mMainTl !=null) {
            mMainTl.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    tabHeight));
        }
    }

    protected class TabAdapter extends FragmentPagerAdapter {

        List<ItemTab> mTabList;

        public TabAdapter(FragmentManager fm, List<ItemTab> tabList) {
            super(fm);
            this.mTabList = tabList;
        }

        public TabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mTabList.get(position).getFragment();
        }

        @Override
        public int getCount() {
            return mTabList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabList.get(position).getTitle();
        }

    }

}
