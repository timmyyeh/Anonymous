package com.anonymous.anonymous;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Database: anonymousdb.cpxrgclgvlwd.us-east-2.rds.amazonaws.com
 * Host on amazon RDS
 * Type: MySQL
 * master user: anonymous
 * Usage: mysql -h <hostname> -u <master> -p
 */
public class AnonymousMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anonymous_main);
    }
}
