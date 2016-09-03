package com.example.android.ukonnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;


public class MyClubList extends AppCompatActivity {
    public static final String prefName = "MyClubListPref";
    public Set<String> clubSet = new TreeSet<>();

    public Set<String> testSet = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_club_list);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        accessDatabase();

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences list = getSharedPreferences(prefName, 0);
        Set<String> newClubList = new HashSet<>();
        newClubList.add("No Clubs Added");

        clubSet = list.getStringSet("Club_List", newClubList);

        LinearLayout list_layout = (LinearLayout) findViewById(R.id.Club_List_linear);
        Iterator iterator = clubSet.iterator();


        while (iterator.hasNext()) {
            String name = (String) iterator.next();
            Button newClub = new Button(this);
            newClub.setText(name);
            list_layout.addView(newClub);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        //saving preferences
        SharedPreferences list = getSharedPreferences(prefName, 0);
        SharedPreferences.Editor editor = list.edit();
        editor.putStringSet("Club_List", clubSet);

        editor.clear().apply();
        clubSet.clear();


    }
    @Override
    protected void onStop(){
        super.onStop();
        LinearLayout list_layout = (LinearLayout) findViewById(R.id.Club_List_linear);
        list_layout.removeAllViews();
    }

    public void listAddClub(View view) {
       /* Button testButton= (Button) view;
        if(testButton.getText()=="Add Club") {
            testButton.setText("Add Club!");
        }
        else{
            testButton.setText("Add Club");
        }*///TEST Code for button functionality

        //adds test buttons for clubs

        //Find scrollview amd layout
        ScrollView list_scroll = (ScrollView) findViewById(R.id.Club_List);
        LinearLayout list_layout = (LinearLayout) findViewById(R.id.Club_List_linear);

        //Add Button

        Button newClub = new Button(this);

        String clubName = "qwrqwvtgyiftgyuiivr";
        newClub.setText(clubName);

        clubSet.add(clubName);

        list_layout.addView(newClub);
    }

    public void internetTest(View view) {
        Intent intent = new Intent(this, InternetTest.class);
        startActivity(intent);
    }

    public void toAddNew(View view) {
        Intent intent = new Intent(this, AddNewActivity.class);
        startActivity(intent);
    }

    public void accessDatabase(){
        SQLiteDatabase myDB = null;
        String TableName = "myTable";

        String Data = "";

        //open  Database.
        try {
            myDB = this.openOrCreateDatabase("DatabaseName", MODE_PRIVATE, null);

            Cursor c = myDB.rawQuery("SELECT * FROM " + TableName, null);

            int Column1 = c.getColumnIndex("Field1");
            int Column2 = c.getColumnIndex("Field2");

            // Check if our result was valid.
            c.moveToFirst();
            while (c != null) {
                // Loop through all Results

                String Name = c.getString(Column1);
                String URL = c.getString(Column2);
                Data = Data + Name + "/" + URL + "\n";
                TextView test= (TextView) findViewById(R.id.test_database);
                test.setText(Data);
                c.moveToNext();
            }

        } catch (Exception e) {
            Log.e("Error", "Error", e);
        } finally {
            if (myDB != null)
                myDB.close();
        }

    }

}