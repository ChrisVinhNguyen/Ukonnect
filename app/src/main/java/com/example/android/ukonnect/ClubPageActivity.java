package com.example.android.ukonnect;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ClubPageActivity extends AppCompatActivity {

    String clubPageURL;
    Elements desciption;
    String info;

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

            info = desciption.text();
            if(!desciption.hasText()) {
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
}
