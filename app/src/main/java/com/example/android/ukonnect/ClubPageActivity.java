package com.example.android.ukonnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
    String info;
    String emailAddress;

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
        Elements desciption;
        Elements contactInfo, groupProfile, groupLeaders;
        Element node;
        Elements contactUs;
        Element email, website;
        Boolean hasEmail;

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
            if (!desciption.hasText()) {
                info = "No description available";
            }

            contactUs = htmlDocument.select("dd:contains(Contact Us) > a[href]");

            if (!contactUs.hasText()) {
                hasEmail = Boolean.FALSE;
            } else {
                email = contactUs.first();
                hasEmail= Boolean.TRUE;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            TextView infoView = (TextView) findViewById(R.id.club_info);
            infoView.setText(info);

            if (hasEmail) {
                Button emailButton = (Button) findViewById(R.id.contact_email);
                emailButton.setVisibility(View.VISIBLE);
                emailAddress = email.attr("href");
                // temp.removeView(emailButton);
            } //else {

        }
    }


    public void toGroupWebsite(View view) {
        Button button = (Button) findViewById(R.id.group_website);
        //button.setText("Official Group Website!!!");
    }

    public void toContactEmail(View view) {
        Button button = (Button) findViewById(R.id.contact_email);


        /* Create the Intent */
        final Intent emailIntent = new Intent(Intent.ACTION_SENDTO);

        emailAddress = emailAddress.replace("mailto:", "");

        /* Fill it with Data */
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{emailAddress});
        //emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
        // emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Text");
        emailIntent.setData(Uri.parse("mailto:"));

        /* Send it off to the Activity-Chooser */
        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }

    public void addClub(View view) {

        SharedPreferences list = getSharedPreferences(prefName, 0);
        Set<String> newClubList = new HashSet<>();
        newClubList.add("No Clubs Added");
        clubSet = list.getStringSet("Club_List", newClubList);

        Intent intent = getIntent();
        String clubName = intent.getExtras().getString("clubName");
        if (clubSet.contains(clubName)) {
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

