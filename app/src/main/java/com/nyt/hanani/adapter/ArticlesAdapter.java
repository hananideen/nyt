package com.nyt.hanani.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nyt.hanani.model.Article;
import com.nyt.hanani.R;

import java.util.ArrayList;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder> {
    private ArrayList<Article> articles;
    private Context context;

    public ArticlesAdapter(ArrayList<Article> models, Context context) {
        this.articles = models;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_article, parent, false);

        return new ArticlesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Article article = articles.get(position);

        holder.tTitle.setText(article.getTitle());
        holder.tDate.setText(article.getPubDate());
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(article.getWebUrl()));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tTitle, tDate;

        ViewHolder(View itemView) {
            super(itemView);
            this.tTitle = (TextView) itemView.findViewById(R.id.tTitle);
            this.tDate = (TextView) itemView.findViewById(R.id.tDate);
        }
    }
}
