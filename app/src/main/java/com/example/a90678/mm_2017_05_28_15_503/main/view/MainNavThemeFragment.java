package com.example.a90678.mm_2017_05_28_15_503.main.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.a90678.lkx_common_17_05_17_16_45.common.presenter.BaseListPresenter;
import com.example.a90678.lkx_common_17_05_17_16_45.common.status.BaseListStatus;
import com.example.a90678.lkx_common_17_05_17_16_45.common.status.BaseLoadingStatus;
import com.example.a90678.lkx_common_17_05_17_16_45.common.view.activity.BaseListFragment;
import com.example.a90678.mm_2017_05_28_15_503.R;
import com.example.a90678.mm_2017_05_28_15_503.main.data.MainNavThemeResult;

/**
 * Created by 90678 on 2017/6/6.
 */

public class MainNavThemeFragment extends BaseListFragment<MainNavThemeResult.MainNavTheme,
        MainNavThemeResult, MainNavThemeFragment.MainNavThemePresenter, MainNavThemeFragment.MainNavThemeHolder> {

    public static MainNavThemeFragment newInstance() {

        Bundle args = new Bundle();

        MainNavThemeFragment fragment = new MainNavThemeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        setLayoutManager(gridLayoutManager);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public MainNavThemePresenter getPresenter() {
        return new MainNavThemePresenter(this, this);
    }

    @Override
    protected MainNavThemeHolder createItemViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.main_nav_theme_item, parent, false);
        MainNavThemeHolder themeHolder = new MainNavThemeHolder(view);
        return themeHolder;
    }

    @Override
    protected void bindItemViewHolder(MainNavThemeHolder holder, MainNavThemeResult.MainNavTheme mainNavTheme, int position) {

        int color = getResources().getColor(mainNavTheme.getColorResId());
//        holder.mainNavItemIv.setImageResource(mainNavTheme.getColorResId());
        holder.mainNavItemIv.setBackgroundColor(color);
//        ViewUtil.setImageCircle(this.getContext(), holder.mainNavItemIv, color);
    }

    public static class MainNavThemePresenter extends BaseListPresenter<MainNavThemeResult.MainNavTheme, MainNavThemeResult> {

        public MainNavThemePresenter(BaseListStatus<MainNavThemeResult.MainNavTheme> baseListStatus,
                                     BaseLoadingStatus<MainNavThemeResult> baseLoadingStatus) {
            super(baseListStatus, baseLoadingStatus);
        }
    }

    public static class MainNavThemeHolder extends RecyclerView.ViewHolder {

        ImageView mainNavItemIv;

        public MainNavThemeHolder(View itemView) {
            super(itemView);
            mainNavItemIv = (ImageView) itemView.findViewById(R.id.main_nav_item_iv);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        hideLoadStatus();
        setSwipeRefreshEnable(false);

        MainNavThemeResult navThemeResult = new MainNavThemeResult();
        success(navThemeResult);
    }

    @Override
    public void onItemClickListener(MainNavThemeResult.MainNavTheme mainNavTheme) {
        super.onItemClickListener(mainNavTheme);

        FragmentActivity activity = getActivity();
        if (activity instanceof MainNavigatorActivity){
            MainNavigatorActivity mainActivity = (MainNavigatorActivity) activity;
            mainActivity.setBaseTheme(mainNavTheme.getType());
            mainActivity.recreateSetTheme();
        }
    }
}
