package com.anonymous.anonymous.News;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.anonymous.anonymous.Account;
import com.anonymous.anonymous.News.Adapter.ListCommentsAdapter;
import com.anonymous.anonymous.News.Adapter.ListNewsAdapter;
import com.anonymous.anonymous.News.Model.Comment;
import com.anonymous.anonymous.News.Utility.ISO8601DateParser;
import com.anonymous.anonymous.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class NewsArticleActivity extends AppCompatActivity {

    WebView webView;
    SpotsDialog dialog;
    FloatingActionButton comment;
    CardView commentPad;
    String webUrl;
    RecyclerView commentList;
    ImageButton sendButton;
    EditText commentField;
    DatabaseReference webRef;
    RecyclerView.LayoutManager layoutManager;
    ListCommentsAdapter commentAdapter;
    List<Comment> comments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_web);

        webRef = FirebaseDatabase.getInstance().getReference("webs");

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
                if(!webUrl.isEmpty()) {
                    commentPad.setVisibility(View.VISIBLE);
                    comment.setVisibility(View.GONE);

                    //initialize comment view
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
                    commentList.setLayoutManager(layoutManager);

                    comments = new ArrayList<Comment>();
                    webRef.child(webUrl.replace('.',',')).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            Comment comment = dataSnapshot.getValue(Comment.class);
                            comments.add(comment);

                            commentAdapter = new ListCommentsAdapter(comments, getBaseContext());
                            commentList.setAdapter(commentAdapter);
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    /*Query query = webRef.child(webUrl.replace('.', ','));

                    FirebaseRecyclerOptions<Comment> options = new FirebaseRecyclerOptions.Builder<Comment>()
                            .setQuery(query, Comment.class)
                            .build();
                    layoutManager = new LinearLayoutManager(view.getContext());
                    commentList.setLayoutManager(layoutManager);
                    commentAdapter = new FirebaseRecyclerAdapter<Comment, CommentHolder>(options)
                    {
                        @Override
                        public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                            // create a new instance of the view holder
                            View view = LayoutInflater.from(parent.getContext())
                                    .inflate(R.layout.layout_comment, parent, false);
                            return new CommentHolder(view);
                        }

                        @Override
                        protected void onBindViewHolder(@NonNull CommentHolder holder, int position, @NonNull Comment model) {
                            holder.comment_user.setText(model.getUser());
                            holder.comment_content.setText(model.getComment());

                            Date date = new Date(model.getTimeCreated());
                            holder.comment_time.setReferenceTime(date.getTime());
                        }
                    };*/


                }
            }
        });

        //comment pad view
        commentPad = (CardView)findViewById(R.id.comment_pad);
        commentPad.setVisibility(View.GONE);

        //comment list
        commentList = (RecyclerView)findViewById(R.id.comments_list);

        if(getIntent() != null)
        {
            if(!getIntent().getStringExtra("webURL").isEmpty()) {
                webUrl = getIntent().getStringExtra("webURL");
                webView.loadUrl(webUrl);
            }
        }

        // comment textfield
        commentField = findViewById(R.id.editComment);

        //send comment button
        sendButton = findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog = new ProgressDialog(NewsArticleActivity.this);
                progressDialog.setMessage("Sending comment...");
                progressDialog.setCancelable(true);
                progressDialog.setIndeterminate(true);
                progressDialog.show();

                final String comment = commentField.getText().toString();
                if(!comment.isEmpty()) {
                    final String user = FirebaseAuth.getInstance().getUid();
                    final Long time = System.currentTimeMillis();

                    // push object to webs node
                    webRef.child(webUrl.replace('.', ',')).push().setValue(new Comment(user, time, comment));

                    commentField.setText("");
                    progressDialog.dismiss();
                }

        }
        });


    }

}

class CommentHolder extends RecyclerView.ViewHolder{

    TextView comment_user;
    TextView comment_content;
    RelativeTimeTextView comment_time;

    public CommentHolder(View itemView) {
        super(itemView);

        comment_user = (TextView) itemView.findViewById(R.id.comment_user);
        comment_content = (TextView) itemView.findViewById(R.id.comment);
        comment_time = (RelativeTimeTextView) itemView.findViewById(R.id.comment_time);
    }
}
