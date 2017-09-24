package com.meng.newsreader.presentation.presenters;

import com.meng.newsreader.presentation.beans.QueryMap;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by mengzhou on 9/24/17.
 */
public class MainPresenterTest {
    @Test
    public void showArticles() throws Exception {
        MainPresenter presenter = new MainPresenter(null);
        Map<String, String> map = new HashMap<>();
        map.put(QueryMap.QUERY, "california");
        map.put(QueryMap.SORT, "newest");
        map.put(QueryMap.FILTER_QUERY, "Arts");
        map.put(QueryMap.BEGIN_DATE, "20160801");
        map.put(QueryMap.PAGE, "1");
        presenter.showArticles(map, false);
    }

}