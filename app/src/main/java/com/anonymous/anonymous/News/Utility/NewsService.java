package com.anonymous.anonymous.News.Utility;

import com.anonymous.anonymous.News.Model.News;

import me.toptas.rssconverter.RssFeed;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by pan on 2017/12/18.
 */

public interface NewsService {

    @GET
    Call<RssFeed> getNews(@Url String url);
}
