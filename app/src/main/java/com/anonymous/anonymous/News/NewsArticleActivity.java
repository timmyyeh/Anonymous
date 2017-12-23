package com.anonymous.anonymous.News;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.anonymous.anonymous.R;

import dmax.dialog.SpotsDialog;

public class NewsArticleActivity extends AppCompatActivity {

    WebView webView;
    SpotsDialog dialog;
    FloatingActionButton comment;
    CardView commentPad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_web);

        dialog = new SpotsDialog(this);
        dialog.show();

        //WebView
        webView = (WebView)findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                dialog.dismiss();
            }
        });

        //comment button
        comment = findViewById(R.id.fab);
        comment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //commentPad.setVisibility(View.VISIBLE);
                //comment.setVisibility(View.GONE);
            }
        });

        /*//comment pad view
        commentPad = (CardView)findViewById(R.id.comment_pad);
        commentPad.setVisibility(View.GONE);

        if(getIntent() != null)
        {
            if(!getIntent().getStringExtra("webURL").isEmpty())
                webView.loadUrl(getIntent().getStringExtra("webURL"));
        }*/

    }

}
