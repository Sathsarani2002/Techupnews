// NewsAdapter.java
package com.example.techupnews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private final Context context;
    private ArrayList<News> newsList;

    public NewsAdapter(Context context, ArrayList<News> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    public void updateList(ArrayList<News> list) {
        this.newsList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        News news = newsList.get(position);
        holder.image.setImageResource(news.getImageResId());
        holder.description.setText(news.getDescription());
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView description;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.newsImage);
            description = itemView.findViewById(R.id.newsDescription);
        }
    }
}
