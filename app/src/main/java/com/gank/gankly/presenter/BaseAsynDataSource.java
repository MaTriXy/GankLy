package com.gank.gankly.presenter;

import android.app.Activity;

/**
 * Create by LingYan on 2016-07-15
 * Email:137387869@qq.com
 */
public abstract class BaseAsynDataSource<T> extends BasePresenter<T> implements IBaseRefreshPresenter {
    private boolean hasMore;
    private int mLimit = 20;
    private boolean isFirst = true;

    public BaseAsynDataSource(Activity mActivity, T view) {
        super(mActivity, view);
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public int getLimit() {
        return mLimit;
    }

    public void setLimit(int limit) {
        mLimit = limit;
    }

    @Override
    public void fetchNew(final int page) {

    }

    @Override
    public void fetchMore(final int page) {

    }
}
