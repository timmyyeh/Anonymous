package com.anonymous.anonymous.News.Model;

import com.anonymous.anonymous.Account;

import java.io.Serializable;

/**
 * Created by pan on 2017/12/22.
 */

public class Comment implements Serializable{

    private String user;
    private long timeCreated;
    private String comment;

    public Comment() {

    }

    public Comment(String user, long timeCreated, String comment) {
        this.user = user;
        this.timeCreated = timeCreated;
        this.comment = comment;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setTimeCreated(long timeCreated) {
        this.timeCreated = timeCreated;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUser() {
        return user;
    }

    public long getTimeCreated() {
        return timeCreated;
    }

    public String getComment() {
        return comment;
    }
}
