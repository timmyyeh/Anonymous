package com.anonymous.anonymous;

/**
 * Created by timmy on 2017/12/14.
 */

public class Account {
    private String account;
    private String email;
    private String password;

    public Account(String account, String email, String password) {
        this.account = account;
        this.email = email;
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
