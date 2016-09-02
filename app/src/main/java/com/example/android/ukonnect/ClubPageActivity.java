package com.example.android.ukonnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class ClubPageActivity extends AppCompatActivity {
    public static final String prefName = "MyClubListPref";
    public Set<String> clubSet = new TreeSet<>();

    String clubPageURL;
    Elements desciption;
    String info;
    Elements contactInfo, groupProfile, groupLeaders;
    Element node;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_page);

        Intent intent = getIntent();
        clubPageURL = intent.getExtras().getString("clubPageURL");
        String clubName = intent.getExtras().getString("clubName");

        setTitle(clubName);

        TextView title = (TextView) findViewById(R.id.club_name);
        title.setText(clubName);

        TextView url = (TextView) findViewById(R.id.club_page_url);
        url.setText(clubPageURL);

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

            Document htmlDocument = null;
            try {
                htmlDocument = Jsoup.connect(clubPageURL).get();
            } catch (IOException e) {
                Log.e("List", "Failed to load HTML code", e);
            }

            desciption = htmlDocument.select("div#main.internal");
            desciption = desciption.select("p");////// >P DOESN'T  WORK ///////

            // contactInfo = htmlDocument.select("dl.infoDl");

            info = desciption.text();
            if (!desciption.hasText()) {
                info = "No description available";
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            TextView infoView = (TextView) findViewById(R.id.club_info);
            infoView.setText(info);
        }
    }

    public void toGroupWebsite(View view) {
        Button button = (Button) findViewById(R.id.group_website);
        button.setText("Official Group Website!!!");
    }

    public void toContactEmail(View view) {
        Button button = (Button) findViewById(R.id.contact_email);
        button.setText("Contact Email!!!");
    }

    public void addClub(View view) {

        SharedPreferences list = getSharedPreferences(prefName, 0);
        Set<String> newClubList = new HashSet<>();
        newClubList.add("No Clubs Added");
        clubSet = list.getStringSet("Club_List", newClubList);

        Intent intent = getIntent();
        String clubName = intent.getExtras().getString("clubName");
        if(clubSet.contains(clubName)){
            Snackbar.make(view, "Club already in list", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return;
        }
        clubSet.add(clubName);
        clubSet.remove("No Clubs Added");

        Snackbar.make(view, "Club added", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        //save preferences
        SharedPreferences.Editor editor = list.edit();
        editor.putStringSet("Club_List", clubSet);

        editor.clear().apply();

       // Intent intent2 = new Intent(this, MyClubList.class);
       // startActivity(intent2);

    }
}

