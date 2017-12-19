package com.example.a90678.lkx_common_17_05_17_16_45.common.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a90678.lkx_common_17_05_17_16_45.common.bean.BaseListResult;
import com.example.a90678.lkx_common_17_05_17_16_45.common.presenter.BaseListPresenter;
import com.example.a90678.lkx_common_17_05_17_16_45.common.status.BaseListStatus;
import com.example.a90678.lkx_common_17_05_17_16_45.common.view.adapter.BaseHFAdapter;
import com.example.a90678.lkx_common_17_05_17_16_45.common.view.layout.RecyclerViewPlus;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述说明  <br/>
 * Author : luokaixuan <br/>
 * CreateDate : 2017/1/18 13:42 <br/>
 * Modified : luokaixuan <br/>
 * ModifiedDate : 2017/1/18 13:42 <br/>
 * Email : 1005949566@qq.com <br/>
 * Version 1.0
 */
public abstract class BaseListFragment<ITEM, DATA extends BaseListResult<ITEM>
        , P extends BaseListPresenter<ITEM, DATA>
        , HOLDER extends ViewHolder>
        extends BaseLoadingFragment<DATA, P>
        implements BaseListStatus<ITEM> {

    private RecyclerViewPlus<DATA, ITEM> mRcv;

    public RecyclerViewPlus getRcv() {
        return mRcv;
    }

    public void setRcv(RecyclerViewPlus<DATA, ITEM> rcv) {
        mRcv = rcv;
    }

    private List<ITEM> mBaseList;
    private BaseHFAdapter mBaseHFAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public List<ITEM> getBaseList() {
        return mBaseList;
    }

    public BaseHFAdapter getBaseHFAdapter() {
        return mBaseHFAdapter;
    }

    protected void setBaseHFAdapter(BaseHFAdapter baseHFAdapter) {
        mBaseHFAdapter = baseHFAdapter;
    }

    protected void setLayoutManager(LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    public LayoutManager getLayoutManager() {
        return layoutManager;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.layout_recycler_list, container, false);
//        mRcv = (RecyclerView) view.findViewById(R.id.rcv);

        mRcv = new RecyclerViewPlus(this.getContext());
        initView();
        return super.onCreateView(inflater, mRcv, savedInstanceState);
    }

    protected void initView() {
        BaseHFAdapter baseHFAdapter = new BaseHFAdapter(new BaseListAdapter2());
        setBaseHFAdapter(baseHFAdapter);

        if (getLayoutManager() == null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
//        StaggeredGridLayoutManager staggeredGridLayoutManager =
//                new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
            setLayoutManager(linearLayoutManager);
        }

        initRecyclerView(getBaseHFAdapter(), getLayoutManager());

        mRcv.setOnItemClickListener(new RecyclerViewPlus.OnRecyclerViewItemClickListener() {

            @Override
            public void onItemClick(int position) {
                onItemClickListener(getBaseList().get(position), position);
            }
        });

        mRcv.setonItemLongClickListener(new RecyclerViewPlus.OnRecyclerViewItemLongClickListener() {
            @Override
            public void onItemLongClick(int position) {
                onItemLongClickListener(getBaseList().get(position));
            }
        });

        mRcv.setOnRecyclerViewLoadMoreListener(new RecyclerViewPlus.OnRecyclerViewLoadMoreListener() {
            @Override
            public void onLoadMore() {
                ITEM lastItem = getLastItem();
                loadMore(lastItem);

                if (lastItem == null) {
//            loadListStartData();
                    getPresenter().loadData();

                } else {
//            loadListMoreData(lastItem);
                    getPresenter().loadMore(lastItem);
                }
                Log.e(" loadData 加载更多，", " loadData 加载更多，");
            }
        });

        mRcv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mRcv.getLastVisibleItem();

            }
        });
//        RecyclerViewUtil.bindBaseListFragment(this);
    }

    public void onItemClickListener(ITEM item) {

//        ArrayList<ITEM> oldList = new ArrayList<>(mBaseList);
//        mBaseList.remove(item);
//        notifyData(mRcv.getAdapter(), oldList, mBaseList);
    }

    public void onItemClickListener(ITEM item, int position) {
        onItemClickListener(item);
    }

    public void onItemLongClickListener(ITEM item) {

    }

    public void onScrollListener () {

    }

    protected void setRecyclerViewAdapter(RecyclerView.Adapter adapter) {
        mRcv.setAdapter(adapter);
    }

    //    protected abstract View initItemView();
    protected abstract HOLDER createItemViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType);

    protected abstract void bindItemViewHolder(HOLDER holder, ITEM item, int position);

    protected int getItemType(ITEM item, int position){
        return position;
    }

    public class BaseListAdapter2 extends RecyclerView.Adapter {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(BaseListFragment.this.getContext());
            return createItemViewHolder(inflater, parent, viewType);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            ITEM item = mBaseList.get(position);
            bindItemViewHolder((HOLDER) holder, item, position);
        }

        @Override
        public int getItemCount() {
            return mBaseList == null ? 0 : mBaseList.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            return getItemType(mBaseList.get(position), position);
        }
    }

    protected void initRecyclerView(BaseHFAdapter baseHFAdapter, LayoutManager layoutManager) {
        if (mRcv == null) {
            return;
        }
        if (baseHFAdapter == null) {
            return;
        }
        if (layoutManager == null) {
            return;
        }
        mRcv.initRecyclerView(this.getContext(), baseHFAdapter, layoutManager);
    }

    public ITEM getLastItem() {
        return mBaseList != null && mBaseList.size() > 0 ? mBaseList.get(mBaseList.size() - 1) : null;
    }

    public int getBaseListSize() {
//        return mBaseHFAdapter == null ? 0 : mBaseHFAdapter.getItemCount();
        return mBaseList == null ? 0 : mBaseList.size();
    }

    public void initBaseList() {
        mBaseList = new ArrayList<>();
    }

    public void setBaseList(List<ITEM> baseList) {
        mBaseList = baseList;
    }

    @Override
    public void loadData() {
//        super.loadData();//不能调用父类的

        mRcv.loadData();
        if (!getSrl().isRefreshing()) {
            getSrl().setRefreshing(true);
        }
//        loadListStartData();
    }

    @Override
    public void startLoad() {
        mRcv.startLoad();
    }

    @Override
    public void loading() {
        mRcv.loading();
    }

    @Override
    public void noMore() {
        mRcv.noMore();
    }

    @Override
    public void hasMore() {
        mRcv.hasMore();
    }

    @Override
    public void error(String msg) {
        if (!mRcv.isLoadMore()) {
            super.error(msg);
        }
        mRcv.error(msg);
    }

    @Override
    public void failure(int code, String msg) {
        if (!mRcv.isLoadMore()) {
            super.failure(code, msg);
        }
        mRcv.failure(code, msg);
    }

    @Override
    public void loadMore(ITEM lastItem) {
        mRcv.loadMore(lastItem);

//        if (lastItem == null) {
//            loadListStartData();
//            getPresenter().loadData();
//        } else {
//            loadListMoreData(lastItem);
//            getPresenter().loadMore(lastItem);
//        }
    }

    @Override
    public void complete() {
        super.complete();
        mRcv.complete();
    }

    @Override
    public void success(DATA data) {
        super.success(data);

        if (data == null) {
            noMore();
        } else {
            List<ITEM> list = data.getList();
            if (mRcv.isLoadMore()) {
                getListMoreData(list);
            } else {
                getListData(list);
            }

            getRcv().getAdapter().notifyDataSetChanged();
        }

    }

    @Override
    public void getListData(List<ITEM> list) {
        setBaseList(list);
        if (getBaseList() == null || getBaseList().size() == 0) {
            empty();
        }
    }

    @Override
    public void getListMoreData(List<ITEM> list) {

        if (list == null || list.size() == 0) {
            noMore();
        } else {
            getBaseList().addAll(list);
        }
    }

    @Override
    public void reLoad() {
        mRcv.reLoad();
    }

    @Override
    public void empty() {
        super.empty();
        mRcv.empty();
    }

    @Override
    public void hideLoadStatus() {
        mRcv.setFooterVisiable(View.GONE);
    }
}
