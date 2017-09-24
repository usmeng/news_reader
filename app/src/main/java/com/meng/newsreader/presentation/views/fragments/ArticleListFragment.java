package com.meng.newsreader.presentation.views.fragments;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meng.newsreader.R;
import com.meng.newsreader.presentation.beans.Article;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengzhou on 9/21/17.
 */
public class ArticleListFragment extends Fragment {

    public static final String TAG = ArticleListFragment.class.getSimpleName();
    private ArticleListCallback articleListCallback;
    private ArticleListAdapter mArticleListAdapter;
    private List<Article> mData = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager manager;
    private static final int SPAN_COUNT = 2;

    public interface ArticleListCallback {
        void onArticleItemClick(int id);
        void onLoadMorePage(int page);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ArticleListCallback) {
            articleListCallback = (ArticleListCallback) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement ArticleListCallback");
        }
    }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_article_list, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.article_recycleview);
        mArticleListAdapter = new ArticleListAdapter(getContext(), mData);
        mArticleListAdapter.setOnArticleItemClickListener(articleListCallback);

        mRecyclerView.setAdapter(mArticleListAdapter);
        manager = new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
    }

    public void updateData(List<Article> articles, boolean load) {
        if(!load) {
            mData.clear();
            mRecyclerView.addOnScrollListener(new EndlessScrollListener(manager) {
                @Override
                public void onLoadMore(int page, int totalItemsCount) {
                    articleListCallback.onLoadMorePage(page);
                }
            });
        }
        mData.addAll(articles);
        mArticleListAdapter.notifyDataSetChanged();
    }

    public List<Article> getData() {
        return mData;
    }

}
