package com.gank.gankly.mvp.source.remote;

import android.support.annotation.Nullable;

import com.gank.gankly.mvp.source.BaseDataSourceModel;

import org.jsoup.nodes.Document;

import rx.Observable;

/**
 * Create by LingYan on 2016-11-23
 * Email:1373878q.com
 */

public class TechnologyDataSource extends BaseDataSourceModel {
    private static final String BASE_URL = "http://gank.io/xiandu/wow/page/";

    @Nullable
    private static TechnologyDataSource INSTANCE = null;

    public static TechnologyDataSource getInstance() {
        if (INSTANCE == null) {
            synchronized (TechnologyDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TechnologyDataSource();
                }
            }
        }
        return INSTANCE;
    }

    public Observable<Document> fetchData(int page) {
        String url = BASE_URL + page;
        return toObservable(jsoupUrlData(url));
    }
}