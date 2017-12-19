package com.example.a90678.mm_2017_05_28_15_503.main.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a90678.lkx_common_17_05_17_16_45.common.utils.AnimateUtil;
import com.example.a90678.lkx_common_17_05_17_16_45.common.view.activity.BaseTabFragment1;
import com.example.a90678.lkx_common_17_05_17_16_45.common.view.activity.ItemTab;
import com.example.a90678.mm_2017_05_28_15_503.R;
import com.example.a90678.mm_2017_05_28_15_503.main.constant.Constanst;
import com.example.a90678.mm_2017_05_28_15_503.main.data.TabTitleAndType;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * Created by 90678 on 2017/6/12.
 */

public class MainTabSubFragment extends BaseTabFragment1 {

    ArrayList<TabTitleAndType> mTabTitleAndTypes;

    public static MainTabSubFragment newInstance(ArrayList<TabTitleAndType> tabTitleAndTypes) {

        Bundle args = new Bundle();
        args.putParcelableArrayList(Constanst.TITLE_AND_TYPE, tabTitleAndTypes);
        MainTabSubFragment fragment = new MainTabSubFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        if (arguments == null) {
            return;
        }
        mTabTitleAndTypes = arguments.getParcelableArrayList(Constanst.TITLE_AND_TYPE);

        super.onActivityCreated(savedInstanceState);

    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();

        if (mTabTitleAndTypes.size() == 1) {
            setTabHeight((int) getResources().getDimension(0));
        } else {
            if (mTabTitleAndTypes.size() <= 5) {
                getMainTl().setTabMode(TabLayout.MODE_FIXED);
            } else {
                getMainTl().setTabMode(TabLayout.MODE_SCROLLABLE);
            }
            setTabHeight((int) getResources().getDimension(R.dimen.tab_height_35));
        }
    }

    @Override
    public List<ItemTab> getTabList() {
        if (mTabTitleAndTypes == null) {
            return null;
        }
        List<ItemTab> itemTabs = new ArrayList<>();
        for (TabTitleAndType mTabTitleAndType : mTabTitleAndTypes) {
            String title = mTabTitleAndType.getTitle();
            String type = mTabTitleAndType.getType2();
            itemTabs.add(new ItemTab(title, MainSubFragment.newInstance(type)));

        }
        return itemTabs;
    }

    @Override
    protected void initTab(List<ItemTab> tabList) {
        super.initTab(tabList);
        for (int i = 0; i < tabList.size(); i++) {
            ItemTab itemTab = tabList.get(i);
            View viewTab = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.main_item_tab_layout2, null);
            TextView tvTab = (TextView) viewTab.findViewById(R.id.main_item_tab2_tv);

//            TextPaint tpaint = tvTab.getPaint();

            tvTab.setText(itemTab.getTitle());
            LinearLayout.LayoutParams layoutParams = new
                    LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMarginStart((int) getResources().getDimension(R.dimen.space_8));
            layoutParams.setMarginEnd((int) getResources().getDimension(R.dimen.space_8));
            tvTab.setLayoutParams(layoutParams);

            getMainTl().getTabAt(i).setCustomView(viewTab);
        }
        AnimateUtil.scaleTabBig(getMainTl().getTabAt(0));
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (!getUserVisibleHint()) {
            JCVideoPlayer.releaseAllVideos();
        }
    }
}
