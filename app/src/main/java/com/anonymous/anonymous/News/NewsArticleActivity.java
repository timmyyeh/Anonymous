package com.anonymous.anonymous.News;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.anonymous.anonymous.News.Adapter.ListCommentsAdapter;
import com.anonymous.anonymous.News.Model.Comment;
import com.anonymous.anonymous.R;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class NewsArticleActivity extends AppCompatActivity {

    WebView webView;
    SpotsDialog dialog;
    FloatingActionButton commentButton;
    CardView commentPad;
    String webUrl;
    RecyclerView commentList;
    ImageButton sendButton;
    EditText commentField;
    DatabaseReference newsArticleDataRef;
    DatabaseReference commentsRef;
    DatabaseReference numOfComRef;
    ListCommentsAdapter commentAdapter;
    List<Comment> comments;
    android.support.v7.widget.Toolbar commentToolBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_web);

        //initialize webview
        webView = (WebView)findViewById(R.id.webView);
        commentButton = (FloatingActionButton)findViewById(R.id.fab);

        //initialize invisibe comment pad view
        commentPad = (CardView)findViewById(R.id.comment_pad);
        commentPad.setVisibility(View.GONE);

        commentList = (RecyclerView)findViewById(R.id.comments_list);
        commentField = (EditText)findViewById(R.id.editComment);
        commentField.setHint("Type comment...");
        sendButton = (ImageButton)findViewById(R.id.send_button);
        commentToolBar = (android.support.v7.widget.Toolbar) findViewById(R.id.top_toolbar_comment);
        commentToolBar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_downward_black_24dp));
        commentToolBar.setTitle("Comments");
        commentToolBar.setTitleTextColor(Color.WHITE);


        // ref to the news database
        newsArticleDataRef = FirebaseDatabase.getInstance().getReference("news");


        // show loading dialog
        dialog = new SpotsDialog(this);
        dialog.show();

        //load web
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                dialog.dismiss();
            }
        });

        if(getIntent() != null)
        {
            if(!getIntent().getStringExtra("webURL").isEmpty()) {
                webUrl = getIntent().getStringExtra("webURL");
                webView.loadUrl(webUrl);
            }
        }

        // ref to the database of comments of this news
        commentsRef = newsArticleDataRef.child(webUrl.replace('.', ',')).child("comments");

        // ref to the number of comment
        numOfComRef = newsArticleDataRef.child(webUrl.replace('.', ',')).child("numOfComment");

        //set up send comment button
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
                    commentsRef.push().setValue(new Comment(user, time, comment));

                    progressDialog.dismiss();
                    commentField.setText("");

                }

            }
        });

        // set up toolbar
        commentToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commentButton.setVisibility(View.VISIBLE);
                commentPad.setVisibility(View.GONE);
            }
        });

        //set up comment button to show comment pad view
        commentButton.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if(!webUrl.isEmpty()) {
                    commentPad.setVisibility(View.VISIBLE);
                    commentButton.setVisibility(View.GONE);


                    // populate comments list
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
                    commentList.setLayoutManager(layoutManager);

                    // comments for display
                    comments = new ArrayList<Comment>();

                    commentsRef.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            Comment comment = dataSnapshot.getValue(Comment.class); // get the newly added comment
                            comments.add(0,comment);

                            commentAdapter = new ListCommentsAdapter(comments, getBaseContext());
                            commentList.setAdapter(commentAdapter);

                            numOfComRef.runTransaction(new Transaction.Handler() {
                                @Override
                                public Transaction.Result doTransaction(MutableData mutableData) {
                                    int num = 0;
                                    if(mutableData.getValue() != null)
                                        num = mutableData.getValue(Integer.class);
                                    mutableData.setValue(num + 1);
                                    return Transaction.success(mutableData);
                                }

                                @Override
                                public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                                    dialog.dismiss();
                                    Log.d("CommentNum", "comment number:" + dataSnapshot.getValue());
                                }
                            });
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

                    // alternative -- use firebase recycler adapter(not able to update comment
                    /*Query query = newsArticleDataRef.child(webUrl.replace('.', ','));

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




    }

}

/**
 * Holder for an comment item.
 */
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
