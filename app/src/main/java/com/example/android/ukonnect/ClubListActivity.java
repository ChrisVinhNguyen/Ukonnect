package com.example.android.ukonnect;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

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
    LinearLayout online_club_list;
    private static Context context;
    int pageNum;
    String newUrl;
    String ulife;
    TextView pageNumView;
    ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_list);


        Intent intent = getIntent();
        String categoryName = intent.getExtras().getString("category");
        online_club_list = (LinearLayout) findViewById(R.id.Club_List_Online);
        loading = (ProgressBar) findViewById(R.id.loading_bar);
        loading.setVisibility(View.VISIBLE);
        setTitle(categoryName);

        ulife = "https://www.ulife.utoronto.ca";

        if (categoryName.equals("Social Justice/Advocacy")) {
            url = ulife + "/interests/list/type/justice";
        } else {
            categoryName = categoryName.toLowerCase();
            String[] shortenedString = categoryName.split(" ");
            pageNum = 1;
            url = ulife + "/interests/list/type/" + shortenedString[0] + "/page/" + String.valueOf(pageNum);
        }

        ClubListActivity.context = getApplicationContext();
        parsed_club_names = new Vector<>(25, 10);


        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
        jsoupAsyncTask.execute();
        pageNumView = (TextView) findViewById(R.id.page_num);
        pageNumView.setText(String.valueOf(pageNum));

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
            // load_more.setTextColor(-16777216);
            //load_more.setBackgroundColor(-3355444);
            // online_club_list.addView(load_more);
            loading.setVisibility(View.GONE);

            load_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    load_more.setText("Loading...");
                    pageNum = pageNum + 1;
                    //newUrl = url + "/page/" + String.valueOf(pageNum);
                    url = url + "/page/" + String.valueOf(pageNum);
                    JsoupAsyncTask jsoupAsyncTask2 = new JsoupAsyncTask();
                    jsoupAsyncTask2.execute();
                    online_club_list.removeView(load_more);
                }
            });
            Button prevButton=(Button) findViewById(R.id.prev_button);
            prevButton.setEnabled(true);
            Button nextButton=(Button) findViewById(R.id.next_button);
            nextButton.setEnabled(true);
        }
    }

    /////////////////////////////////////////////////////////////////IO THREAD ASYNC///////////////////////////////////////////////////////////////////


    public static Context getAppContext() {
        return ClubListActivity.context;
    }

    public void loadNextPage(View view) {
        //load_more.setText("Loading...");
        //////ADD EXCEPTION FOR LAST PAGE
        if (false) {
            Snackbar.make(view, "Last Page", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            pageNum = pageNum + 1;
            pageNumView.setText(String.valueOf(pageNum));
            url = url + "/page/" + String.valueOf(pageNum);
            online_club_list.removeAllViews();
            loading.setVisibility(View.VISIBLE);
            parsed_club_names.clear();

            Button prevButton=(Button) findViewById(R.id.prev_button);
            prevButton.setEnabled(false);
            Button nextButton=(Button) findViewById(R.id.next_button);
            nextButton.setEnabled(false);

            JsoupAsyncTask jsoupAsyncTask2 = new JsoupAsyncTask();
            jsoupAsyncTask2.execute();
        }

    }

    public void loadPrevPage(View view) {
        //load_more.setText("Loading...");
        if (pageNum == 1) {
            Snackbar.make(view, "First Page", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            pageNum = pageNum - 1;
            pageNumView.setText(String.valueOf(pageNum));
            url = url + "/page/" + String.valueOf(pageNum);
            online_club_list.removeAllViews();
            loading.setVisibility(View.VISIBLE);
            parsed_club_names.clear();

            Button prevButton=(Button) findViewById(R.id.prev_button);
            prevButton.setEnabled(false);
            Button nextButton=(Button) findViewById(R.id.next_button);
            nextButton.setEnabled(false);

            JsoupAsyncTask jsoupAsyncTask1 = new JsoupAsyncTask();
            jsoupAsyncTask1.execute();

        }

    }
}
