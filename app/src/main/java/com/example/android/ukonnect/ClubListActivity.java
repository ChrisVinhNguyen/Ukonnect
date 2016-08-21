package com.example.android.ukonnect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ClubListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_list);

        Intent intent = getIntent();
        String categoryName = intent.getExtras().getString("category");

        setTitle(categoryName);
    }
}
