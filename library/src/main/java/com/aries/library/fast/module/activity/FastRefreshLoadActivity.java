package com.aries.library.fast.module.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aries.library.fast.delegate.FastRefreshLoadDelegate;
import com.aries.library.fast.i.IFastRefreshLoadView;
import com.aries.library.fast.i.IHttpRequestControl;
import com.aries.library.fast.i.IMultiStatusView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.marno.easystatelibrary.EasyStatusView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

/**
 * Created: AriesHoo on 2017/7/24 17:12
 * Function: 下拉刷新及上拉加载更多
 * Desc:
 */
public abstract class FastRefreshLoadActivity<T>
        extends FastTitleActivity implements IFastRefreshLoadView<T> {
    protected SmartRefreshLayout mRefreshLayout;
    protected RecyclerView mRecyclerView;
    protected EasyStatusView mEasyStatusView;
    protected int DEFAULT_PAGE = 0;
    protected int DEFAULT_PAGE_SIZE = 10;

    protected FastRefreshLoadDelegate<T> mFastRefreshLoadDelegate;

    @Override
    public void beforeInitView() {
        super.beforeInitView();
        mFastRefreshLoadDelegate = new FastRefreshLoadDelegate<>(mContentView, this);
        mRecyclerView = mFastRefreshLoadDelegate.mRecyclerView;
        mRefreshLayout = mFastRefreshLoadDelegate.mRefreshLayout;
        mEasyStatusView = mFastRefreshLoadDelegate.mStatusView;
    }

    @Override
    public RefreshHeader getRefreshHeader() {
        return null;
    }

    @Override
    public LoadMoreView getLoadMoreView() {
        return null;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(mContext);
    }

    @Override
    public IMultiStatusView getMultiStatusView() {
        return null;
    }

    @Override
    public IHttpRequestControl getIHttpRequestControl() {
        return new IHttpRequestControl() {
            @Override
            public SmartRefreshLayout getRefreshLayout() {
                return mRefreshLayout;
            }

            @Override
            public BaseQuickAdapter getRecyclerAdapter() {
                return getAdapter();
            }

            @Override
            public EasyStatusView getStatusView() {
                return mEasyStatusView;
            }

            @Override
            public int getCurrentPage() {
                return DEFAULT_PAGE;
            }

            @Override
            public int getPageSize() {
                return DEFAULT_PAGE_SIZE;
            }
        };
    }

    @Override
    public void onItemClicked(BaseQuickAdapter<T, BaseViewHolder> adapter, View view, int position) {

    }

    @Override
    public boolean isItemClickEnable() {
        return true;
    }

    @Override
    public boolean isRefreshEnable() {
        return true;
    }

    @Override
    public boolean isLoadMoreEnable() {
        return true;
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        DEFAULT_PAGE = 0;
        mFastRefreshLoadDelegate.setLoadMore(isLoadMoreEnable());
        loadData(DEFAULT_PAGE);
    }

    @Override
    public void onLoadMoreRequested() {
        loadData(++DEFAULT_PAGE);
    }

    @Override
    public void loadData() {
        loadData(DEFAULT_PAGE);
    }
}
