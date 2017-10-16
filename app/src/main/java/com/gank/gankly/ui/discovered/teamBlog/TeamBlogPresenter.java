package com.gank.gankly.ui.discovered.teamBlog;

import com.gank.gankly.bean.JianDanBean;
import com.gank.gankly.mvp.FetchPresenter;
import com.gank.gankly.mvp.source.remote.TeamBlogDataSource;
import com.gank.gankly.ui.discovered.technology.TechnologyContract;
import com.socks.library.KLog;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Create by LingYan on 2016-11-23
 * Email:137387869@qq.com
 */

public class TeamBlogPresenter extends FetchPresenter implements TechnologyContract.Presenter {
    private TeamBlogDataSource mTask;
    private TechnologyContract.View mView;

    public TeamBlogPresenter(TeamBlogDataSource task, TechnologyContract.View view) {
        mTask = task;
        mView = view;
    }

    @Override
    public void fetchNew() {
        fetchData(getInitPage());
    }

    private void fetchData(int page) {
        mTask.fetchData(page).subscribe(new Observer<Document>() {
            @Override
            public void onComplete() {
                mView.showContent();
                mView.hideProgress();
                int nextPage = getFetchPage() + 1;
                setFetchPage(nextPage);
            }

            @Override
            public void onError(Throwable e) {
                mView.hideProgress();
                KLog.e(e);
            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Document document) {
                List<JianDanBean> list = parseDocument(document);
                if (list.size() > 0) {
                    if (getFetchPage() > 1) {
                        mView.appendData(list);
                    } else {
                        mView.refillData(list);
                    }
                }
            }
        });
    }

    private List<JianDanBean> parseDocument(Document document) {
        List<JianDanBean> jiandanBeen = new ArrayList<>();
        if (document != null) {
            Elements hrefs = document.select(".xiandu_left a");
            Elements imgs = document.select(".xiandu_right img");
            Elements titles = document.select(".xiandu_item");
            if (hrefs != null) {
                String href;
                String title;
                String img;
                String type = null;
                for (int i = 0; i < hrefs.size(); i++) {
                    href = hrefs.get(i).attr("href");
                    img = imgs.get(i).attr("src");
                    title = titles.get(i).text();
                    jiandanBeen.add(new JianDanBean(href, title, type, img));
                }
            }
        }
        return jiandanBeen;
    }

    @Override
    public void fetchMore() {
        if (hasMore()) {
            mView.showProgress();
            fetchData(getFetchPage());
        }
    }

    @Override
    public void unSubscribe() {

    }
}
