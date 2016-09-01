package com.example.android.ukonnect;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Iterator;

public class InternetTest extends AppCompatActivity {

    private Document htmlDocument;
    private String htmlPageUrl = "https://www.ulife.utoronto.ca/interests/list/type/academic";
    private Elements div;
    private Elements ul;
    private Elements li;
    private Element node;
    private Element node2;
    private TextView parsedHtmlNode;
    private String htmlContentInStringFormat;
    private String test;
    private Iterator iterator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet_test);

        parsedHtmlNode = (TextView)findViewById(R.id.html_content);
        Button htmlTitleButton = (Button)findViewById(R.id.button);
        htmlTitleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
                parsedHtmlNode.setText("Loading...");
                jsoupAsyncTask.execute();
            }
        });
    }

    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                int i=0;
                htmlDocument = Jsoup.connect(htmlPageUrl).get();

                div= htmlDocument.select("div.manageTable");
                ul= htmlDocument.select("ul.listing.innerListing");
                node= htmlDocument.select("ul.listing.innerListing > li > a").get(i);

                test=node.text();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            parsedHtmlNode.setText(test);
        }
    }
}
