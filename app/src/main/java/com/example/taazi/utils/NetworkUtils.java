/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.taazi.utils;

import android.net.Uri;
import android.util.Log;

import com.example.taazi.entity.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static List<News> getSimpleWeatherStringsFromJson(String jsonString)
            throws JSONException {

        JSONObject jsonStr = new JSONObject(jsonString);
        JSONArray articles = jsonStr.getJSONArray("articles");

        List<News> list = new ArrayList<News>();

        for (int i = 0; i < articles.length(); i++) {

            JSONObject dayForecast = articles.getJSONObject(i);

            String source = dayForecast.getJSONObject("source").getString("name");
            String title = dayForecast.getString("title");
            String urlToImage = dayForecast.getString("urlToImage");
            String url = dayForecast.getString("url");

            News news = new News(source,title,url,urlToImage);

            list.add(news);

        }

        return list;
    }
}