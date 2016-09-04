package com.example.android.ukonnect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class AddNewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        // remove popup of keyboard in this activity
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public void toClubList(View view){

        // make sure view in arguments is a TextView
        TextView category = (TextView) view;

        // get the text from the TextView that is passed
        String categoryName = category.getText().toString();

        // link intent to ClubListActivity and pass in the category name
        Intent intent = new Intent(this, ClubListActivity.class);
        intent.putExtra("category",categoryName);
        startActivity(intent);
    }
}

