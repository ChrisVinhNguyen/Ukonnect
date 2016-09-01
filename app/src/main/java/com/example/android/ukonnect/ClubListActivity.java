package com.example.android.ukonnect;

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

public class ClubListActivity extends AppCompatActivity {

    Document htmlDocument;
    Elements li;
    Element node;
    String parsed_club_name;
    String url;
    Button club;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_list);

        Intent intent = getIntent();
        String categoryName = intent.getExtras().getString("category");

        setTitle(categoryName);

        categoryName= categoryName.toLowerCase();
        String[] shortenedString=categoryName.split(" ");
        url="https://www.ulife.utoronto.ca/interests/list/type/"+shortenedString[0];
    }
    ///////////ADD EXCEPTION FOR SOCIAL JUSTICE/ADVOCACY////////////////


    @Override
    protected void onStart() {
        super.onStart();
        club=new Button(this);

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

            li = htmlDocument.select("ul.listing.innerListing > li > a");
            node = htmlDocument.select("ul.listing.innerListing > li > a").get(0);

            parsed_club_name = node.text();

            return null;
        }
        @Override
        protected void onPostExecute(Void result){
            LinearLayout online_club_list = (LinearLayout) findViewById(R.id.Club_List_Online);
            club.setText(parsed_club_name);
            online_club_list.addView(club);
        }
    }
}
