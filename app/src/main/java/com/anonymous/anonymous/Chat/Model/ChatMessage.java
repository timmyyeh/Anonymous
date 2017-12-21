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

    @Override
    public String toString() {
        return "ChatMessage{" +
                "mMessage='" + mMessage + '\'' +
                ", mName='" + mName + '\'' +
                ", mTime=" + mTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatMessage that = (ChatMessage) o;

        if (mTime != that.mTime) return false;
        if (mMessage != null ? !mMessage.equals(that.mMessage) : that.mMessage != null)
            return false;
        return mName != null ? mName.equals(that.mName) : that.mName == null;
    }

    @Override
    public int hashCode() {
        int result = mMessage != null ? mMessage.hashCode() : 0;
        result = 31 * result + (mName != null ? mName.hashCode() : 0);
        result = 31 * result + (int) (mTime ^ (mTime >>> 32));
        return result;
    }
}
