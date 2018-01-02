package com.anonymous.anonymous.News.Utility;

import me.toptas.rssconverter.RssConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by pan on 2017/12/18.
 */

public class RetrofitClient {
    private static Retrofit retrofit = null;
    public static Retrofit getClient(String baseUrl)
    {
        if(retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(RssConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
