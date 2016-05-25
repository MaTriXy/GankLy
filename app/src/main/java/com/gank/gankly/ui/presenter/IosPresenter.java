package com.gank.gankly.ui.presenter;

import android.app.Activity;

import com.gank.gankly.bean.GankResult;
import com.gank.gankly.bean.ResultsBean;
import com.gank.gankly.network.GankRetrofit;
import com.gank.gankly.ui.view.IIosView;
import com.socks.library.KLog;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Create by LingYan on 2016-05-24
 */
public class IosPresenter extends BasePresenter<IIosView> {
    private int limit = 20;
    private int mPage;

    public IosPresenter(Activity mActivity, IIosView view) {
        super(mActivity, view);
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void fetchDate(final int page) {
        this.mPage = page;
        Observable<GankResult> video = GankRetrofit.getInstance()
                .getGankService().fetchIosGoods(limit, page);
        Observable<GankResult> image = GankRetrofit.getInstance()
                .getGankService().fetchBenefitsGoods(limit, page);

        Observable.zip(video, image, new Func2<GankResult, GankResult, GankResult>() {
            @Override
            public GankResult call(GankResult gankResult, GankResult gankResult2) {
                List<ResultsBean> list = gankResult2.getResults();
//                MeiziArrayList.getInstance().addBeanAndPage(list, mPage);
                return gankResult;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GankResult>() {
                    @Override
                    public void onCompleted() {
                        mIView.hideRefresh();
                        mIView.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mIView.hideRefresh();
                        mIView.onError(e);
                    }

                    @Override
                    public void onNext(GankResult gankResult) {
                        toNext(gankResult);
                    }
                });
    }


    public void fetchBenefitsGoods(int page) {
        KLog.d("page：" + page);
        this.mPage = page;
        mIView.showRefresh();
        GankRetrofit.getInstance().fetchWelfare(limit, page, new Subscriber<GankResult>() {
            @Override
            public void onCompleted() {
                mIView.hideRefresh();
                mIView.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                mIView.hideRefresh();
                mIView.onError(e);
            }

            @Override
            public void onNext(GankResult gankResult) {
                toNext(gankResult);
            }
        });
    }

    private void toNext(GankResult gankResult) {
        if (!gankResult.isEmpty()) {
            if (mPage == 1) {
                mIView.clear();
                mIView.refillDate(gankResult.getResults());
            } else {
                mIView.appendMoreDate(gankResult.getResults());
            }
            if (gankResult.getSize() < limit) {
                mIView.hasNoMoreDate();
            }
        } else {
            if (mPage <= 1) {
                mIView.showEmpty();
            } else {
                mIView.hasNoMoreDate();
            }
        }
    }
}
