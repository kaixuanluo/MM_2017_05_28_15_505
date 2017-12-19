package com.example.a90678.mm_2017_05_28_15_503.mainList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a90678.lkx_common_17_05_17_16_45.common.utils.ViewUtil;
import com.example.a90678.lkx_common_17_05_17_16_45.common.view.activity.BaseNavigatorActivity;
import com.example.a90678.lkx_common_17_05_17_16_45.common.view.activity.BaseListFragment;
import com.example.a90678.mm_2017_05_28_15_503.R;

/**
 * Created by 90678 on 2017/5/28.
 */

public class MainListNavigatorActivity extends BaseNavigatorActivity {

    @Override
    public Fragment getFragment() {
        return MainListFragment.newInstance();
    }

    public static class MainListFragment extends BaseListFragment<MainListResult.Result.MainList, MainListResult,
            MainListPresenter, MainListFragment.MainListViewHolder> {

        public static MainListFragment newInstance() {

            Bundle args = new Bundle();

            MainListFragment fragment = new MainListFragment();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public MainListPresenter getPresenter() {
            return new MainListPresenter(this, this);
        }

        @Override
        protected MainListViewHolder createItemViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
            return new MainListViewHolder(LayoutInflater.from(this.getContext()).inflate(R.layout.item_main, parent, false));
        }

        @Override
        protected void bindItemViewHolder(MainListViewHolder holder, MainListResult.Result.MainList mainList, int position) {

            ViewUtil.setText(holder.mainTvTitle, mainList.getTitle());
            ViewUtil.setText(holder.mainContentTv1, mainList.getContent());
            ViewUtil.setText(holder.mainContentTv2, mainList.getNId() + "");
            ViewUtil.setText(holder.mainContentTv3, mainList.getDate());
        }

        static class MainListViewHolder extends RecyclerView.ViewHolder {
            TextView mainTvTitle;
            TextView mainContentTv1;
            TextView mainContentTv2;
            TextView mainContentTv3;

            MainListViewHolder(View view) {
                super(view);
                mainTvTitle = (TextView) view.findViewById(R.id.main_tv_title);
                mainContentTv1 = (TextView) view.findViewById(R.id.main_content_tv_1);
                mainContentTv2 = (TextView) view.findViewById(R.id.main_content_tv_2);
                mainContentTv3 = (TextView) view.findViewById(R.id.main_content_tv_3);
            }
        }

    }
}
