package com.nyt.hanani.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nyt.hanani.model.Article;
import com.nyt.hanani.adapter.ArticlesAdapter;
import com.nyt.hanani.presenter.ArticlesPresenter;
import com.nyt.hanani.R;

import java.util.ArrayList;

import static com.nyt.hanani.utils.Constant.ARTICLE_TYPE;
import static com.nyt.hanani.utils.Constant.POPULAR_EMAILED_URL;
import static com.nyt.hanani.utils.Constant.POPULAR_SHARED_URL;
import static com.nyt.hanani.utils.Constant.POPULAR_VIEWED_URL;
import static com.nyt.hanani.utils.Constant.TYPE_EMAILED;
import static com.nyt.hanani.utils.Constant.TYPE_SHARED;
import static com.nyt.hanani.utils.Constant.TYPE_VIEWED;

public class ArticlesActivity extends AppCompatActivity implements ArticlesPresenter.View {

    private ArticlesPresenter presenter;
    private RecyclerView rArticle;
    private ProgressBar pLoading;
    private EditText eSearch;
    private Button bSearch;
    private LinearLayout lSearch;
    private TextView tError;
    private boolean pressedOnce = false;
    private boolean searchMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        initUI();
        checkType();
    }

    /**
     * Initialize UI.
     */
    private void initUI(){
        tError = (TextView) findViewById(R.id.tError);
        pLoading = (ProgressBar) findViewById(R.id.pLoading);
        lSearch = (LinearLayout) findViewById(R.id.lSearch);

        eSearch = (EditText) findViewById(R.id.eSearch);
        eSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch();
                return true;
            }
            return false;
        });

        bSearch = (Button) findViewById(R.id.bSearch);
        bSearch.setOnClickListener(view -> performSearch());

        rArticle = (RecyclerView) findViewById(R.id.rArticle);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rArticle.setLayoutManager(mLayoutManager);

        presenter = new ArticlesPresenter(this);
    }

    /**
     * Check articles type selected by user.
     * Get article if user select from "Popular".
     * Show search view if user choose to search.
     */
    private void checkType(){
        if (getIntent().getStringExtra(ARTICLE_TYPE)!=null){
            hideSearch();
            String type = getIntent().getStringExtra(ARTICLE_TYPE);

            switch (type) {
                case TYPE_VIEWED:
                    presenter.getPopular(POPULAR_VIEWED_URL);
                    break;
                case TYPE_SHARED:
                    presenter.getPopular(POPULAR_SHARED_URL);
                    break;
                case TYPE_EMAILED:
                    presenter.getPopular(POPULAR_EMAILED_URL);
                    break;
            }
        } else {
            searchMode = true;
            showSearch();
        }
    }

    /**
     * Perform search action if clicked by user.
     * Get keyword from user input and search articles from api.
     */
    private void performSearch(){
        pLoading.setVisibility(View.VISIBLE);
        pressedOnce = false;

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(eSearch.getWindowToken(), 0);

        String search = eSearch.getText().toString();
        if (!TextUtils.isEmpty(search))
            presenter.getBySearch(search);
    }

    /**
     * Hide search view.
     */
    private void hideSearch(){
        getSupportActionBar().setTitle(R.string.title_articles);
        tError.setVisibility(View.GONE);
        lSearch.setVisibility(View.GONE);
    }

    /**
     * Show search view.
     */
    private void showSearch(){
        getSupportActionBar().setTitle(R.string.title_search);
        pLoading.setVisibility(View.GONE);
        lSearch.setVisibility(View.VISIBLE);
    }

    @Override
    public void updateArticleList(ArrayList<Article> articles) {
        ArticlesAdapter articlesAdapter = new ArticlesAdapter(articles, ArticlesActivity.this);
        rArticle.setAdapter(articlesAdapter);
        rArticle.setVisibility(android.view.View.VISIBLE);
        pLoading.setVisibility(android.view.View.GONE);
        hideSearch();
    }

    @Override
    public void searchNotFound() {
        tError.setVisibility(android.view.View.VISIBLE);
        showSearch();
    }

    @Override
    public void onBackPressed() {
        if (pressedOnce || !searchMode) {
            super.onBackPressed();
            return;
        }

        pressedOnce = true;
        showSearch();
        rArticle.setVisibility(View.GONE);
    }
}
