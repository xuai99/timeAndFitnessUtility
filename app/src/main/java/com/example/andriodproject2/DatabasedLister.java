package com.example.andriodproject2;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * Created by User on 2/28/2017.
 */

public class DatabasedLister extends AppCompatActivity {

    private static final String TAG = "DatabasedLister";

    DatabaseHelper stepsDatabased;

    private ListView mListView;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
        //XML ID
        mListView = (ListView) findViewById(R.id.ListDataView);
        stepsDatabased = new DatabaseHelper(this);


        ListDataSetForViewing();
    }
 // list out all value in databased to the ListView
    private void ListDataSetForViewing() {

        //get the data from the databased and append to the list view
        Cursor dataLister = stepsDatabased.getData();
        ArrayList<String> listData = new ArrayList<>();
        while(dataLister.moveToNext()){
            //get value in column 1 for viewing then add it to the list virw
            listData.add(dataLister.getString(1));
        }
        //create the list adapter and set the adapter
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);

    }
}
