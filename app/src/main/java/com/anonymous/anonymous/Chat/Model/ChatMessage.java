package com.anonymous.anonymous.Chat.Model;

import java.util.Date;

/**
 * Created by timmy on 2017/12/20.
 */

public class ChatMessage {
    private String mMessage;
    private String mName;
    private long mTime;

    public ChatMessage(){

    }
    public ChatMessage(String msgTxt, String user){
        this.mMessage = msgTxt;
        this.mName = user;
        mTime = new Date().getTime();
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String msgTxt) {
        this.mMessage = msgTxt;
    }

    public String getUser() {
        return mName;
    }

    public void setUser(String user) {
        this.mName = user;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long msgTime) {
        this.mTime = msgTime;
    }
}
