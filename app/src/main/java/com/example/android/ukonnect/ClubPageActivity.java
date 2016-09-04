package com.example.android.ukonnect;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

public class ClubPageActivity extends AppCompatActivity {
    public static final String prefName = "MyClubListPref";
    public Set<String> clubSet = new TreeSet<>();

    String clubPageURL;
    String info;
    String emailAddress, groupWebpage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_page);

        ProgressBar loading = (ProgressBar) findViewById(R.id.loading_bar);
        //loading.setVisibility(View.VISIBLE);

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

    /////////////////////////////////////////////////////////////////IO THREAD ASYNC///////////////////////////////////////////////////////////////////

    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {
        Elements desciption;
        Elements contactInfo, groupProfile, groupLeaders;
        Element node;
        Elements contactUs, officialGroupWebsite;
        Element email, website;
        Boolean hasEmail, hasWebsite;

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
                hasEmail = Boolean.TRUE;
            }

            officialGroupWebsite = htmlDocument.select("dd:contains(Official Group Website) > a[href]");
            if (!officialGroupWebsite.hasText()) {
                hasWebsite = Boolean.FALSE;
            } else {
                website = officialGroupWebsite.first();
                hasWebsite = Boolean.TRUE;
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
            }

            if (hasWebsite) {
                Button websiteButton = (Button) findViewById(R.id.group_website);
                websiteButton.setVisibility(View.VISIBLE);
                groupWebpage = website.attr("href");
            }
            ProgressBar loading = (ProgressBar) findViewById(R.id.loading_bar);
            loading.setVisibility(View.GONE);

            ScrollView alltheothershit = (ScrollView) findViewById(R.id.alltheothershit);
            alltheothershit.setVisibility(View.VISIBLE);

        }
    }


    public void toGroupWebsite(View view) {
        Button websiteButton = (Button) findViewById(R.id.group_website);
        Uri webpage = Uri.parse(groupWebpage);
        //
        // websiteButton.setText(groupWebpage);

        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void toContactEmail(View view) {
        /* Create the Intent */
        Uri webpage = Uri.parse(emailAddress);

        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void addClub(View view) {

        Intent intent = getIntent();
        String clubName = intent.getExtras().getString("clubName");
        if (SQLcontains(clubName)) {
            Snackbar.make(view, "Club already in list", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return;
        }


        Snackbar.make(view, "Club added", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        addClubToSQL(clubName, clubPageURL);


    }

    public void addClubToSQL(String clubName, String clubPageURL) {
        SQLiteDatabase myDB = null;
        String TableName = "myTable";


        //Create a Database.
        try {
            myDB = this.openOrCreateDatabase("DatabaseName", MODE_PRIVATE, null);

            //Create a Table in the Database.
            myDB.execSQL("CREATE TABLE IF NOT EXISTS "
                    + TableName
                    + " (Field1 VARCHAR, Field2 VARCHAR);");

            //Insert data to a Table
            myDB.execSQL("INSERT INTO "
                    + TableName
                    + " (Field1, Field2)"
                    + " VALUES ('" + clubName + "','" + clubPageURL + "')");
            //+ " VALUES ('qwrsqwr', 'qwrsqwr');");
        } catch (Exception e) {
            Log.e("Error", "Error", e);
        } finally {
            if (myDB != null)
                myDB.close();
        }
    }
    public boolean SQLcontains(String clubName){
        SQLiteDatabase myDB = null;
        String TableName = "myTable";


        //open  Database.
        try {
            myDB = this.openOrCreateDatabase("DatabaseName", MODE_PRIVATE, null);

            Cursor c = myDB.rawQuery("SELECT * FROM " + TableName, null);

            int Column1 = c.getColumnIndex("Field1");

            // Check if our result was valid.
            c.moveToFirst();
            while (c != null) {
                // Loop through all Results

                final String Name = c.getString(Column1);
                if(clubName.equals(Name)){
                    return true;
                }
                c.moveToNext();

            }

        } catch (Exception e) {
            Log.e("Error", "Error", e);
        } finally {
            if (myDB != null)
                myDB.close();
        }
        return false;
    }
}

