package com.example.taazi.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taazi.R;
import com.example.taazi.RecyclerViewAdapter;
import com.example.taazi.entity.News;
import com.example.taazi.utils.NetworkUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class HeadlinesFragment extends Fragment {

    View v;
    RecyclerView myRecyclerView;
    RecyclerViewAdapter myRecyclerViewAdapter;
    List<News> newsList;
    AsyncTask myTask;

    public HeadlinesFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.headlines,container,false);
        myRecyclerView = (RecyclerView) v.findViewById(R.id.rv_headlines);
        return v;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new FetchNewsTask().execute();

    }

    public class FetchNewsTask extends AsyncTask<String, Void, List<News>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<News> doInBackground(String... params) {

            URL weatherRequestUrl = buildUrl();

            try {
                String jsonWeatherResponse = NetworkUtils.getResponseFromHttpUrl(weatherRequestUrl);
                List<News> newsList1 = NetworkUtils.getSimpleWeatherStringsFromJson(jsonWeatherResponse);
                return newsList1;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<News> news) {
            newsList = news;
            myRecyclerViewAdapter = new RecyclerViewAdapter(newsList,getContext());
            myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            myRecyclerView.setAdapter(myRecyclerViewAdapter);        }


    }

    private static final String TOP_HEADLINES = "https://newsapi.org/v2/top-headlines";

    public static URL buildUrl() {
        Uri builtUri = Uri.parse(TOP_HEADLINES).buildUpon()
                .appendQueryParameter("apiKey", "74188e1dc8154400909cd823f2d6ed61")
                .appendQueryParameter("sources","nbc-news,cnn,fox-news,abc-news,cbc-news,the-new-york-times,the-washington-post,usa-today,vice-news")
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v("HEADLINES:", "Built URI " + url);

        return url;
    }
}
