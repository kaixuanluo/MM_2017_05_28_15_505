package com.example.a90678.mm_2017_05_28_15_503.main.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a90678.mm_2017_05_28_15_503.main.constant.Constanst;

/**
 * Created by 90678 on 2017/6/12.
 */

public class MainSubFragment extends MainFragmentListAndVideo {

    public static MainSubFragment newInstance(String type) {

        Bundle args = new Bundle();
        args.putString(Constanst.TYPE, type);
        MainSubFragment fragment = new MainSubFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        if (arguments != null) {
            type = arguments.getString(Constanst.TYPE);
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        setLayoutManager(gridLayoutManager);

        View createView = super.onCreateView(inflater, container, savedInstanceState);

        return createView;
    }

}
