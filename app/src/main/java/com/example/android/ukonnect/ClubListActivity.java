package com.example.android.ukonnect;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_list);

        Intent intent = getIntent();
        String categoryName = intent.getExtras().getString("category");

        setTitle(categoryName);

        categoryName = categoryName.toLowerCase();
        ///////////ADD EXCEPTION FOR SOCIAL JUSTICE/ADVOCACY////////////////
        if(categoryName=="social justice/advocacy"){
            url="https://www.ulife.utoronto.ca/interests/list/type/justice";
        }
        //////////////NOT WORKING ATM,FUCK IT ILL DO IT LATER//////////////////
        else {
            String[] shortenedString = categoryName.split(" ");
            url = "https://www.ulife.utoronto.ca/interests/list/type/" + shortenedString[0];
        }

        ClubListActivity.context = getApplicationContext();
        parsed_club_names=new Vector<>(25,10);
    }



    @Override
    protected void onStart() {
        super.onStart();
        //club = new Button(this);
        online_club_list = (LinearLayout) findViewById(R.id.Club_List_Online);

        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
        jsoupAsyncTask.execute();

    }

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

           for(int i=0;i<li.size();i++) {
                node = htmlDocument.select("ul.listing.innerListing > li > a").get(i);
                parsed_club_names.addElement(node.text());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            for(int i=0;i<parsed_club_names.size();i++) {
                Button club = new Button(context);
                club.setText(parsed_club_names.get(i));
                online_club_list.addView(club);
            }

            Button load_more=new Button(context);
            load_more.setText("Load More");
            online_club_list.addView(load_more);
        }
    }

    public static Context getAppContext() {
            return ClubListActivity.context;
        }
}
