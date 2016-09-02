package com.example.android.ukonnect;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Vector;

public class ClubListActivity extends AppCompatActivity {

    Document htmlDocument;
    Elements li;
    Element node;
    Vector<String> parsed_club_names;
    String url;
    // Button club;
    LinearLayout online_club_list;
    private static Context context;
    int pageNum;
    String newUrl;
    String ulife;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_list);

        Intent intent = getIntent();
        String categoryName = intent.getExtras().getString("category");
        online_club_list = (LinearLayout) findViewById(R.id.Club_List_Online);

        setTitle(categoryName);
        ulife = "https://www.ulife.utoronto.ca";

        if (categoryName.equals("Social Justice/Advocacy") ) {
            url = ulife + "/interests/list/type/justice";
        }

        else {
            categoryName = categoryName.toLowerCase();
            String[] shortenedString = categoryName.split(" ");
            pageNum = 1;
            url = ulife + "/interests/list/type/" + shortenedString[0] + "/page/" + String.valueOf(pageNum);
        }

        ClubListActivity.context = getApplicationContext();
        parsed_club_names = new Vector<>(25, 10);

        //ProgressBar loading= new ProgressBar(context, null, R.style.GenericProgressIndicator);
        //RelativeLayout temp= new RelativeLayout(context,null,R.style.GenericProgressBackground);
        // temp.addView(loading);
        // online_club_list.addView(temp);

        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
        jsoupAsyncTask.execute();

        // temp.setVisibility(View.INVISIBLE);
    }

    /////////////////////////////////////////////////////////////////IO THREAD ASYNC///////////////////////////////////////////////////////////////////
    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            //url = "https://www.ulife.utoronto.ca/interests/list/type/academic";
            try {
                htmlDocument = Jsoup.connect(url).get();
            } catch (IOException e) {
                Log.e("List", "Failed to load HTML code", e);
            }

            li = htmlDocument.select("ul.listing.innerListing > li");

            for (int i = 0; i < li.size(); i++) {
                node = htmlDocument.select("ul.listing.innerListing > li > a").get(i);
                parsed_club_names.addElement(node.text());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            for (int i = 0; i < parsed_club_names.size(); i++) {
                final Button club = new Button(context);
                final String name = parsed_club_names.get(i);
                club.setText(name);
                club.setTag(i);
                online_club_list.addView(club);

                club.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Element clubLink = htmlDocument.select("ul.listing.innerListing > li > a[href]").get((int) club.getTag());
                        String clubURL = ulife + clubLink.attr("href");
                        //club.setText(clubURL);
                        club.setText("Loading...");

                        Intent intent = new Intent(context, ClubPageActivity.class);
                        intent.putExtra("clubPageURL", clubURL);
                        intent.putExtra("clubName", name);
                        startActivity(intent);
                        club.setText(name);
                    }
                });
            }

            final Button load_more = new Button(context);
            load_more.setText("Load More");
            load_more.setTextColor(-16777216);
            load_more.setBackgroundColor(-3355444);
            online_club_list.addView(load_more);

            load_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    load_more.setText("Loading...");
                    pageNum = pageNum + 1;
                    newUrl = url + "/page/" + String.valueOf(pageNum);
                    JsoupAsyncTask2 jsoupAsyncTask2 = new JsoupAsyncTask2();
                    jsoupAsyncTask2.execute();
                    online_club_list.removeView(load_more);
                }
            });
        }
    }

    //////////////////////////////////////////////////////PROBABLY NEED TO USE ADAPTORS OR SOMETHING, LIST STARTS GETTING SLOW AFTER LOADING TOO MANY CLUBS////////////////////////////
    private class JsoupAsyncTask2 extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            //url = "https://www.ulife.utoronto.ca/interests/list/type/academic";
            try {
                htmlDocument = Jsoup.connect(newUrl).get();
            } catch (IOException e) {
                Log.e("List", "Failed to load HTML code", e);
            }

            li = htmlDocument.select("ul.listing.innerListing > li");

            for (int i = 0; i < li.size(); i++) {
                node = htmlDocument.select("ul.listing.innerListing > li > a").get(i);
                parsed_club_names.addElement(node.text());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            int count = 0;
            for (int i = ((pageNum - 1) * 25); i < parsed_club_names.size(); i++) {
                final Button club = new Button(context);
                final String name = parsed_club_names.get(i);
                club.setText(name);
                club.setTag(count);
                online_club_list.addView(club);

                club.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Element clubLink = htmlDocument.select("ul.listing.innerListing > li > a[href]").get((int) club.getTag());
                        String clubURL = ulife + clubLink.attr("href");
                        club.setText("Loading...");

                        Intent intent = new Intent(context, ClubPageActivity.class);
                        intent.putExtra("clubPageURL", clubURL);
                        intent.putExtra("clubName", name);
                        startActivity(intent);
                        club.setText(name);
                    }
                });
                count++;
            }

            final Button load_more = new Button(context);
            load_more.setText("Load More");
            load_more.setTextColor(-16777216);
            load_more.setBackgroundColor(-3355444);
            online_club_list.addView(load_more);

            load_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    load_more.setText("Loading...");
                    pageNum = pageNum + 1;
                    newUrl = url + "/page/" + String.valueOf(pageNum);

                    JsoupAsyncTask2 jsoupAsyncTask2 = new JsoupAsyncTask2();
                    jsoupAsyncTask2.execute();
                    online_club_list.removeView(load_more);
                }
            });

        }
    }

    public static Context getAppContext() {
        return ClubListActivity.context;
    }
}
