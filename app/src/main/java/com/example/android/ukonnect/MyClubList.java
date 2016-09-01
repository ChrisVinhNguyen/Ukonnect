package com.example.android.ukonnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class MyClubList extends AppCompatActivity {
    public static final String prefName= "MyClubListPref";
    public Set<String> clubSet= new HashSet<>();

    public Set<String> testSet=new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTitle("My Club List-testing java layout");
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

        //loading shared preferences


       // String clubName;

        //setting content view
        setContentView(R.layout.activity_my_club_list);

}
    @Override
    protected void onStart(){
        super.onStart();

        SharedPreferences list= getSharedPreferences(prefName,0);
        Set<String> newClubList = new HashSet<>();
        newClubList.add("No Clubs Added");

        clubSet= list.getStringSet("Club_List",newClubList);

        LinearLayout list_layout= (LinearLayout) findViewById(R.id.Club_List_linear);
        Iterator iterator= clubSet.iterator();

       /* testSet.add("1");
        testSet.add("2");
        testSet.add("3");
        testSet.add("4");
        testSet.add("5");

        Iterator iterator= testSet.iterator();*/

       while(iterator.hasNext()) {
           String name = (String) iterator.next();
           Button newClub = new Button(this);
           newClub.setText(name);
           list_layout.addView(newClub);
       }
    }
    @Override
    protected void onPause(){
        super.onPause();

        //saving preferences
        SharedPreferences list= getSharedPreferences(prefName,0);
        SharedPreferences.Editor editor = list.edit();
        editor.putStringSet("Club_List",clubSet);

        editor.commit();
        //clubSet.clear();

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

        String clubName="qwrqwvtgyiftgyuiivr";
        newClub.setText(clubName);

        clubSet.add(clubName);

        list_layout.addView(newClub);
    }

    public void internetTest(View view) {
        Intent intent = new Intent(this, InternetTest.class);
        startActivity(intent);
    }
}