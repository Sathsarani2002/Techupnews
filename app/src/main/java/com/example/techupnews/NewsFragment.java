package com.example.techupnews;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {

    private String category;

    public NewsFragment(String category) {
        this.category = category;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.newsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<NewsItem> newsList = getDummyNews(category);
        NewsAdapter adapter = new NewsAdapter(getContext(), newsList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private List<NewsItem> getDummyNews(String category) {
        List<NewsItem> list = new ArrayList<>();
        if (category.equals("sports")) {
            list.add(new NewsItem(R.drawable.sample_sports, "Football match on Friday."));
            list.add(new NewsItem(R.drawable.sample_sports, "Cricket tournament starts next week."));
        } else if (category.equals("academic")) {
            list.add(new NewsItem(R.drawable.sample_academic, "Mid-term exams schedule released."));
            list.add(new NewsItem(R.drawable.sample_academic, "Science fair registration open."));
        } else if (category.equals("events")) {
            list.add(new NewsItem(R.drawable.sample_event, "Annual Tech Fest on June 25."));
            list.add(new NewsItem(R.drawable.sample_event, "Guest lecture by Prof. Silva."));
        }
        return list;
    }
}
