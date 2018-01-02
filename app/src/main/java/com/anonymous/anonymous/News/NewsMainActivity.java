package com.anonymous.anonymous.News;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.anonymous.anonymous.AnonymousBaseActivity;
import com.anonymous.anonymous.News.Adapter.ListNewsAdapter;
import com.anonymous.anonymous.News.Utility.Common;
import com.anonymous.anonymous.News.Utility.NewsService;
import com.anonymous.anonymous.R;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.github.florent37.diagonallayout.DiagonalLayout;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import dmax.dialog.SpotsDialog;
import me.toptas.rssconverter.RssFeed;
import me.toptas.rssconverter.RssItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * If running on emulator, send the location manually, or else the news won't load!
 */
public class NewsMainActivity extends AnonymousBaseActivity{
    DiagonalLayout diagonalLayout;
    KenBurnsView kbv;
    SpotsDialog dialog;
    NewsService mService;
    TextView top_author, top_title;
    SwipeRefreshLayout swipeRefreshLayout;
    ListNewsAdapter adapter;
    RecyclerView lstNews;
    RecyclerView.LayoutManager layoutManager;
    String webHotUrl = "";
    String cityName, stateName, countryName;

    //location service variables
    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 2;
    private Location currentLocation;
    private LocationCallback locationCallback;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_main);

        // News Service
        mService = Common.getNewsService();

        // show loading dialog
        dialog = new SpotsDialog(this);

        // View
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(cityName != null && stateName != null && countryName != null)
                    loadNews(cityName, stateName, countryName, true);
            }
        });
        diagonalLayout = (DiagonalLayout) findViewById(R.id.diagonalLayout);
        diagonalLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent articleBody = new Intent(getBaseContext(), NewsArticleActivity.class);
                articleBody.putExtra("webURL", webHotUrl);
                startActivity(articleBody);
            }
        });

        kbv = (KenBurnsView) findViewById(R.id.top_image);
        top_author = (TextView) findViewById(R.id.topAuthor);
        top_title = (TextView) findViewById(R.id.top_title);

        lstNews = (RecyclerView) findViewById(R.id.list_news);
        lstNews.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        lstNews.setLayoutManager(layoutManager);


        // location service
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                currentLocation = locationResult.getLocations().get(0);
                getAddress();
            }
        };
        startLocationUpdates();

        //bottom nav
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        menuItem.setEnabled(false);
    }

    private void loadNews(String city, String state, String country, final boolean isRefreshed) {
        if (!isRefreshed) {
            dialog.show();

            // if not in united states
            if(!country.equals("USA"))
            {
                Toast.makeText(this, "News function not supported outside of United States", Toast.LENGTH_LONG).show();
                dialog.dismiss();
                return;
            }


            mService.getNews(Common.getRssFeed(city, state, "United States"))
                    .enqueue(new Callback<RssFeed>() {
                        @Override
                        public void onResponse(Call<RssFeed> call, Response<RssFeed> response) {
                            dialog.dismiss();

                            //get first article
                            Picasso.with(getBaseContext())
                                    .load(response.body().getItems().get(0).getImage())
                                    .into(kbv);

                            top_title.setText(response.body().getItems().get(0).getTitle());
                            top_author.setText(cityName);

                            webHotUrl = response.body().getItems().get(0).getLink();

                            // load remainning articles
                            List<RssItem> removeFirstItem = response.body().getItems();

                            // remove the first article that has been load
                            removeFirstItem.remove(0);
                            adapter = new ListNewsAdapter(removeFirstItem, getBaseContext());
                            adapter.notifyDataSetChanged();
                            lstNews.setAdapter(adapter);
                        }

                        @Override
                        public void onFailure(Call<RssFeed> call, Throwable t) {

                        }
                    });
        } else {
            dialog.show();
            if(!country.equals("USA"))
            {
                Toast.makeText(this, "News function not supported outside of United States", Toast.LENGTH_LONG).show();
                dialog.dismiss();
                return;
            }
            mService.getNews(Common.getRssFeed(city, state, "United States"))
                    .enqueue(new Callback<RssFeed>() {
                                 @Override
                                 public void onResponse(Call<RssFeed> call, Response<RssFeed> response) {
                                     dialog.dismiss();

                                     //get first article
                                     Picasso.with(getBaseContext())
                                             .load(response.body().getItems().get(0).getImage())
                                             .into(kbv);

                                     top_title.setText(response.body().getItems().get(0).getTitle());
                                     top_author.setText(cityName);

                                     webHotUrl = response.body().getItems().get(0).getLink();

                                     // load remainning articles
                                     List<RssItem> removeFirstItem = response.body().getItems();

                                     // remove the first article that has been load
                                     removeFirstItem.remove(0);
                                     adapter = new ListNewsAdapter(removeFirstItem, getBaseContext());
                                     adapter.notifyDataSetChanged();

                                     // add news to the database
                                     DatabaseReference newsArticleDataRef = FirebaseDatabase.getInstance().getReference("news");
                                     for(RssItem item: response.body().getItems()){
                                         DatabaseReference thisNewsRef = newsArticleDataRef.child(item.getLink().replace('.',','));
                                         thisNewsRef.child("numOfComment").runTransaction(new Transaction.Handler() {
                                             @Override
                                             public Transaction.Result doTransaction(MutableData mutableData) {
                                                 if(mutableData.getValue() == null)
                                                     mutableData.setValue(0);
                                                 return Transaction.success(mutableData);
                                             }

                                             @Override
                                             public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                                                 dialog.dismiss();
                                                 Log.d("CommentNum", "comment number:" + dataSnapshot.getValue());
                                             }
                                         });
                                     }
                                 }

                                 @Override
                                 public void onFailure(Call<RssFeed> call, Throwable t) {

                                 }
                             }
                    );
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return super.onNavigationItemSelected(item);
    }

    @SuppressWarnings("MissingPermission")
    private void startLocationUpdates(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setInterval(2000);
            locationRequest.setFastestInterval(1000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            fusedLocationClient.requestLocationUpdates(locationRequest,
                    locationCallback,
                    null);
        }
    }

    @SuppressWarnings("MissingPermission")
    private void getAddress() {

        if (!Geocoder.isPresent()) {
            Toast.makeText(NewsMainActivity.this,
                    "Can't find current address, ",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        //set location
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] tokens = addresses.get(0).getAddressLine(1).split(", ");

        if(tokens.length != 2){
            Toast.makeText(NewsMainActivity.this,
                    "Unknown location",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        cityName = tokens[0];
        String[] statetokens = tokens[1].split(" ");

        if(statetokens.length != 2){
            Toast.makeText(NewsMainActivity.this,
                    "Unknown location",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        stateName = statetokens[0];
        countryName = addresses.get(0).getAddressLine(2);
        Log.d("Location", cityName + " " + stateName + " " + countryName);

        loadNews(cityName, stateName, countryName, false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationUpdates();
                } else {
                    Toast.makeText(this, "Location permission not granted, " +
                                    "restart the app if you want the feature",
                            Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }
}



