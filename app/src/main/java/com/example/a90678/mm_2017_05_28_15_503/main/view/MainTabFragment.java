package com.example.a90678.mm_2017_05_28_15_503.main.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a90678.lkx_common_17_05_17_16_45.common.utils.AnimateUtil;
import com.example.a90678.lkx_common_17_05_17_16_45.common.view.activity.BaseTabFragment2;
import com.example.a90678.lkx_common_17_05_17_16_45.common.view.activity.ItemTab;
import com.example.a90678.mm_2017_05_28_15_503.R;
import com.example.a90678.mm_2017_05_28_15_503.main.data.TabTitleTotal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 90678 on 2017/6/2.
 */

public class MainTabFragment extends BaseTabFragment2 {

    public static MainTabFragment newInstance() {

        Bundle args = new Bundle();

        MainTabFragment fragment = new MainTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public List<ItemTab> getTabList() {

        ItemTab mItemTab1 = new ItemTab("精选", R.drawable.home_tab_good2, MainTabSubFragment.newInstance(TabTitleTotal.getTab1()));
        ItemTab mItemTab2 = new ItemTab("写真", R.drawable.home_tab_video, MainTabSubFragment.newInstance(TabTitleTotal.getTab2()));
        ItemTab mItemTab3 = new ItemTab("私拍", R.drawable.home_tab_girl, MainTabSubFragment.newInstance(TabTitleTotal.getTab3()));
        ItemTab mItemTab4 = new ItemTab("韩国", R.drawable.home_tab_hg, MainTabSubFragment.newInstance(TabTitleTotal.getTab4()));
        ItemTab mItemTab5 = new ItemTab("街拍", R.drawable.home_tab_jp, MainTabSubFragment.newInstance(TabTitleTotal.getTab5()));

        List<ItemTab> itemTabs = new ArrayList<>();
        itemTabs.add(mItemTab1);
        itemTabs.add(mItemTab2);
        itemTabs.add(mItemTab3);
        itemTabs.add(mItemTab4);
        itemTabs.add(mItemTab5);

        return itemTabs;
    }

    @Override
    protected void initTab(List<ItemTab> tabList) {
        super.initTab(tabList);
        for (int i = 0; i < tabList.size(); i++) {
            ItemTab itemTab = tabList.get(i);
            View viewTab = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.main_item_tab_layout, null);
            ImageView ivTab = (ImageView) viewTab.findViewById(R.id.tab_iv);
            TextView tvTab = (TextView) viewTab.findViewById(R.id.tab_tv);
            ivTab.setImageResource(itemTab.getmImgSrc());
            tvTab.setText(itemTab.getTitle());
            getMainTl().getTabAt(i).setCustomView(viewTab);
        }
        AnimateUtil.scaleTabBig(getMainTl().getTabAt(0));
    }
}