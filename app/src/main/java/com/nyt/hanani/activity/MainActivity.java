package com.nyt.hanani.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;

import com.nyt.hanani.R;
import com.nyt.hanani.adapter.MostPopularAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FrameLayout lSearch;
    private RecyclerView rPopular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        setPopularList();
    }

    /**
     * Initialize UI.
     */
    private void initUI(){
        lSearch = (FrameLayout) findViewById(R.id.lSearch);
        rPopular = (RecyclerView) findViewById(R.id.rPopular);

        lSearch.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ArticlesActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Set recycler view for Popular list.
     */
    private void setPopularList(){
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rPopular.setLayoutManager(mLayoutManager);

        ArrayList<String> popularList = new ArrayList<>();
        popularList.add(getString(R.string.articles_most_viewed));
        popularList.add(getString(R.string.articles_most_shared));
        popularList.add(getString(R.string.articles_most_emailed));
        MostPopularAdapter adapter = new MostPopularAdapter(popularList, this);
        rPopular.setAdapter(adapter);
    }
}
