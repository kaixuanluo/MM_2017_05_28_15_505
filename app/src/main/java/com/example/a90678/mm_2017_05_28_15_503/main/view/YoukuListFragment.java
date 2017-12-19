package com.example.a90678.mm_2017_05_28_15_503.main.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a90678.lkx_common_17_05_17_16_45.common.module.BaseApiServiceModule;
import com.example.a90678.lkx_common_17_05_17_16_45.common.utils.ViewUtil;
import com.example.a90678.mm_2017_05_28_15_503.main.data.MainFrgAdvResult;
import com.example.a90678.mm_2017_05_28_15_503.main.presenter.MainFrgAdvPresenter;
import com.example.a90678.mm_2017_05_28_15_503.main.presenter.YoukuPresenter;

/**
 * Created by 90678 on 2017/6/10.
 */

public class YoukuListFragment extends MainFragment1_1_adv3 {

    YoukuPresenter youkuPresenter;

    public static YoukuListFragment newInstance() {

        Bundle args = new Bundle();

        YoukuListFragment fragment = new YoukuListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        setLayoutManager(gridLayoutManager);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public MainFrgAdvPresenter getPresenter() {
        youkuPresenter = new YoukuPresenter(this, this);
        return youkuPresenter;
    }

    @Override
    protected void bindItemViewHolder(RecyclerView.ViewHolder holder, MainFrgAdvResult.MainFrgAdv mainFrgAdv, int position) {
//        super.bindItemViewHolder(holder, mainFrgAdv, position);

        if (holder instanceof MainFrgAdvViewHolder) {
            String urlImg = mainFrgAdv.getUrlImg();
            urlImg = urlImg.replaceAll(BaseApiServiceModule.getWebRoot(),"");
            ViewUtil.setImage(this.getContext(), ((MainFrgAdvViewHolder) holder).mainFrg11AdvIv, urlImg);
            ViewUtil.setText(((MainFrgAdvViewHolder) holder).mainFrg11AdvTv, mainFrgAdv.getTitle());
        }
    }
}
