package com.example.android.ukonnect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class AddNewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Add New Club");
        setContentView(R.layout.activity_add_new);
    }
    public void addClub(View view){
        //just toggles name for now
        Button testButton= (Button) view;

        if(testButton.getText()=="Add Club") {
            testButton.setText("Add Club!");
        }

        else{
            testButton.setText("Add Club");
        }
    }
}

