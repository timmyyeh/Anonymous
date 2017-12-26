package com.anonymous.anonymous.News.Model;

import com.anonymous.anonymous.Account;

import java.io.Serializable;

/**
 * Created by pan on 2017/12/22.
 */

public class Comment implements Serializable{

    private Account user;
    private String commentId;
    private long timeCreated;
    private String comment;

    public Comment(Account user, String commentId, long timeCreated, String comment) {
        this.user = user;
        this.commentId = commentId;
        this.timeCreated = timeCreated;
        this.comment = comment;
    }

    public Account getUser() {
        return user;
    }

    public String getCommentId() {
        return commentId;
    }

    public long getTimeCreated() {
        return timeCreated;
    }

    public String getComment() {
        return comment;
    }
}
