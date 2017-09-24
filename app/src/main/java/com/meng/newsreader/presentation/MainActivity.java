package com.meng.newsreader.presentation;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.meng.newsreader.R;
import com.meng.newsreader.presentation.beans.Article;
import com.meng.newsreader.presentation.beans.QueryMap;
import com.meng.newsreader.presentation.presenters.MainPresenter;
import com.meng.newsreader.presentation.views.fragments.ArticleFragment;
import com.meng.newsreader.presentation.views.fragments.ArticleListFragment;
import com.meng.newsreader.presentation.views.IMainView;
import com.meng.newsreader.presentation.base.BaseActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends BaseActivity implements
        IMainView, ArticleListFragment.ArticleListCallback {

    public static final String TAG = MainActivity.class.getSimpleName();
    FragmentManager fragmentManager;
    ArticleListFragment articleListFragment;
    MainPresenter mainPresenter;
    Map<String, String> mapValues = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mainPresenter = new MainPresenter(this);
        fragmentManager = getFragmentManager();

        requestPermission(Manifest.permission.INTERNET).subscribe(integer -> {
            if (savedInstanceState == null) {
                articleListFragment = new ArticleListFragment();
                fragmentManager.beginTransaction()
                        .add(R.id.article_fragment, articleListFragment, ArticleListFragment.TAG).commit();
            }
        });
    }

    @Override
    public void showArticles(List<Article> articles, boolean loadMoreData) {
        ArticleListFragment listFragment = (ArticleListFragment) fragmentManager.findFragmentByTag(ArticleListFragment.TAG);
        if (listFragment == null) return;
        listFragment.updateData(articles, loadMoreData);
    }

    @Override
    public void onArticleItemClick(int article) {
        ArticleFragment articleDetailFragment = ArticleFragment.newInstance(article);
        fragmentManager.beginTransaction()
                .add(R.id.article_fragment, articleDetailFragment, ArticleFragment.TAG)
                .addToBackStack("").hide(articleListFragment).commit();
    }

    @Override
    public void onLoadMorePage(int page) {
        Toast.makeText(this, "page: " + page, Toast.LENGTH_LONG).show();

        mapValues.put(QueryMap.PAGE, String.valueOf(page));
        mainPresenter.showArticles(mapValues, true);
    }

    @Override
    public void showArticle(int articleId) {
        mainPresenter.showArticle(articleId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filter, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() == 0) return false;
                mapValues.put(QueryMap.QUERY, query);
                mapValues.remove(QueryMap.PAGE);
                mainPresenter.showArticles(mapValues, false);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_item_filter) {
            showFilterDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    String beginDate = "";
    String sortValue = "";
    Set<String> filterQuery = new HashSet<>();

    public void showFilterDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.filter_dialog, null);
        TextView beginDateTV = view.findViewById(R.id.tv_begin_date_value);
        if(mapValues.containsKey(QueryMap.BEGIN_DATE))
            beginDateTV.setText(mapValues.get(QueryMap.BEGIN_DATE));
        beginDateTV.setOnClickListener(listener -> {
            Calendar calendar = Calendar.getInstance();
            if(beginDate.length() != 0) {
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                try {
                    Date parse = format.parse(beginDate);
                    calendar.setTimeInMillis(parse.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                calendar.setTimeInMillis(System.currentTimeMillis());
            }
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, (picker, yrs, mon, d) -> {
                beginDateTV.setText(yrs + "/" + (++mon < 10 ? "0" + mon : mon) + "/" + (d < 10 ? "0" + d : d));
                beginDate = "" + year + (mon < 10 ? "0" + mon : mon) + (d < 10 ? "0" + d : d);
            }, year, month, day);
            datePickerDialog.show();
        });

        List<String> values = Arrays.asList("newest", "oldest");
        Spinner sortSpinner = view.findViewById(R.id.spinner_sort);
        sortSpinner.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, values));
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sortValue = values.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        CheckBox artBox = view.findViewById(R.id.checkbox_art);
        CheckBox artFashion = view.findViewById(R.id.checkbox_fashion);
        CheckBox artSport = view.findViewById(R.id.checkbox_sport);
        artBox.setOnCheckedChangeListener((listener, checked) -> {
            String art = artBox.getText().toString();
            if(checked) filterQuery.add(art);
            else filterQuery.remove(art);
        });

        artFashion.setOnCheckedChangeListener((listener, checked) -> {
            String fashion = artFashion.getText().toString();
            if(checked) filterQuery.add(fashion);
            else filterQuery.remove(fashion);
        });
        artSport.setOnCheckedChangeListener((listener, checked) -> {
            String sport = artSport.getText().toString();
            if(checked) filterQuery.add(sport);
            else filterQuery.remove(sport);
        });
        artBox.setChecked(filterQuery.contains(artBox.getText().toString()));
        artFashion.setChecked(filterQuery.contains(artFashion.getText().toString()));
        artSport.setChecked(filterQuery.contains(artSport.getText().toString()));

        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setView(view);
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
        view.findViewById(R.id.button_save).setOnClickListener(listener -> {
            if(beginDate.length() != 0) mapValues.put(QueryMap.BEGIN_DATE, beginDate);
            if(sortValue.length() != 0) mapValues.put(QueryMap.SORT, sortValue);
            if(!filterQuery.isEmpty()) {
                StringBuilder filters = new StringBuilder();
                for (String aFilterQuery : filterQuery) {
                    filters.append(aFilterQuery);
                    filters.append(" ");
                }
                filters.deleteCharAt(filters.length() - 1);
                mapValues.put(QueryMap.FILTER_QUERY, filters.toString());
            }
            mapValues.remove(QueryMap.PAGE);
            /*if(mapValues.containsKey(QueryMap.QUERY)) {
                mainPresenter.showArticles(mapValues, false);
            }*/
            alertDialog.dismiss();
        });
    }
}
