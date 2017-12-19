package com.example.a90678.mm_2017_05_28_15_503.main.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a90678.lkx_common_17_05_17_16_45.common.utils.ViewUtil;
import com.example.a90678.lkx_common_17_05_17_16_45.common.view.activity.BaseFragment;
import com.example.a90678.mm_2017_05_28_15_503.R;

/**
 * Created by 90678 on 2017/6/2.
 */

public class MainFragment extends BaseFragment {

    public static MainFragment newInstance() {

        Log.e("mainFragment newi"," mainFragment newinstance");

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frame_layout_empty, container, false);

        ViewUtil.setFragment(getActivity(), R.id.frl, MainTabFragment.newInstance());

        return super.onCreateView(inflater, (ViewGroup) view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
