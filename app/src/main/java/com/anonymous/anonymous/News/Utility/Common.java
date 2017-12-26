package com.anonymous.anonymous.News.Utility;

/**
 * Created by pan on 2017/12/18.
 */

public class Common {
    private static final String BASE_URL = "https://newsapi.org/";

    public static final String API_Key = "870d1bef744e4ec1bba6a2ed0edd0a3e";

    public static NewsService getNewsService()
    {
        return RetrofitClient.getClient(BASE_URL).create(NewsService.class);
    }

    //https://newsapi.org/v2/everything?q=location&apiKey=870d1bef744e4ec1bba6a2ed0edd0a3e
    public static String getAPIUrl(String location, String sortBy, String apiKey)
    {
        StringBuilder apiUrl = new StringBuilder("https://newsapi.org/v2/everything?q=");
        return apiUrl.append(location)
                .append("&sortBy=")
                .append(sortBy)
                .append("&apiKey=")
                .append(apiKey)
                .toString();
    }

}
