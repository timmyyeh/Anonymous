package com.anonymous.anonymous.News.Model;

import java.util.List;

/**
 * Created by pan on 2017/12/17.
 */

public class News {

    private String status;
    private int totalResults;

    private List<Article> articles;

    public News(String status, int totalResults, List<Article> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public List<Article> getArticles() {
        return articles;
    }
}
