package com.meng.newsreader.data;


import com.google.gson.Gson;
import com.meng.newsreader.data.response.SearchArticleResponse;
import com.meng.newsreader.excutor.API;
import com.meng.newsreader.excutor.NetRequest;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.functions.Function;

/**
 * Created by mengzhou on 9/21/17.
 */

public class Repository {
    private static Repository instance = new Repository();

    private Repository(){}

    public static Repository getInstance() {
        return instance;
    }

    public Flowable<SearchArticleResponse> searchArticles(final String keyword) {
        return Flowable.create((FlowableOnSubscribe<String>) e -> {
            String json = NetRequest.get(new API().getUrl(keyword));
            e.onNext(json);
            e.onComplete();
        }, BackpressureStrategy.BUFFER).map(s -> new Gson().fromJson(s, SearchArticleResponse.class));
    }

}
