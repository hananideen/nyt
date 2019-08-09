package com.nyt.hanani.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nyt.hanani.R;
import com.nyt.hanani.activity.ArticlesActivity;

import java.util.ArrayList;

import static com.nyt.hanani.utils.Constant.ARTICLE_TYPE;
import static com.nyt.hanani.utils.Constant.TYPE_EMAILED;
import static com.nyt.hanani.utils.Constant.TYPE_SHARED;
import static com.nyt.hanani.utils.Constant.TYPE_VIEWED;

public class MostPopularAdapter extends RecyclerView.Adapter<MostPopularAdapter.ViewHolder> {
    private ArrayList<String> stringList;
    private Context context;

    public MostPopularAdapter(ArrayList<String> stringList, Context context) {
        this.stringList = stringList;
        this.context = context;
    }

    @Override
    public MostPopularAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_most_popular, parent, false);

        return new MostPopularAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MostPopularAdapter.ViewHolder holder, final int position) {
        String title = stringList.get(position);

        holder.tTitle.setText(title);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ArticlesActivity.class);

                if (position==0){
                    intent.putExtra(ARTICLE_TYPE, TYPE_VIEWED);
                } else if (position==1){
                    intent.putExtra(ARTICLE_TYPE, TYPE_SHARED);
                } else if (position==2){
                    intent.putExtra(ARTICLE_TYPE, TYPE_EMAILED);
                }
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tTitle;

        ViewHolder(View itemView) {
            super(itemView);
            this.tTitle = (TextView) itemView.findViewById(R.id.tTitle);
        }
    }
}
