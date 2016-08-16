package com.example.android.ukonnect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import java.util.Vector;


public class MyClubList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("My Club List-testing java layout");
       /* final Button testButton = new Button(this);
        testButton.setText("TEST");

        final RelativeLayout myLayout = new RelativeLayout(this);
        myLayout.addView(testButton);
        setContentView(myLayout);

       testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(testButton.getText()=="TEST!") {
                    testButton.setText("TEST");
                }
                else{
                    testButton.setText("TEST!");
                }
                addClub();
            }
        });*/
        setContentView(R.layout.activity_my_club_list);
}
    public void listAddClub(View view){
       /* Button testButton= (Button) view;
        if(testButton.getText()=="Add Club") {
            testButton.setText("Add Club!");
        }
        else{
            testButton.setText("Add Club");
        }*///TEST Code for button functionality

        //adds test buttons for clubs

        //Find scrollview amd layout
        ScrollView list_scroll= (ScrollView) findViewById(R.id.Club_List);
        LinearLayout list_layout= (LinearLayout) findViewById(R.id.Club_List_linear);

        //Add Button

        Button newClub= new Button(this);
        newClub.setText("TEST");

        list_layout.addView(newClub);
    }
}
