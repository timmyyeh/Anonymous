package com.anonymous.anonymous.News.Utility;

/**
 * Created by pan on 2017/12/18.
 */

public class Common {
    private static final String BASE_URL = "https://news.google.com/";

    public static NewsService getNewsService()
    {
        return RetrofitClient.getClient(BASE_URL).create(NewsService.class);
    }

    //Lynnwood,%20WA,%20United%20States/Lynnwood,%20Washington?ned=us&hl=en&gl=US
    public static String getRssFeed(String city, String state, String country)
    {
        StringBuilder rssFeed = new StringBuilder("https://news.google.com/news/rss/local/section/geo/");
        return rssFeed.append(city)
                .append(",%20")
                .append(state)
                .append(",%20")
                .append(country.replace(" ", "%20"))
                .append("?ned=us&hl=en&gl=US")
                .toString();
    }

}
