package com.example.drizzle16.moviecard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.example.drizzle16.moviecard.adapter.ListAdapter;

import java.util.ArrayList;

/**
 * Created by drizzle16 on 2016-09-09.
 */
public class MyLoveAct extends AppCompatActivity {

    ListView listview ;
    ListAdapter adapter;

    ArrayList<String> mPosterPathArray;
    ArrayList<String> mTitleArray;
    ArrayList<String> mGenreArray;

    ArrayList<String> trashedAraay = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylove);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putStringArrayListExtra("trashed", trashedAraay);
                setResult(RESULT_OK, intent);
                finish();
                overridePendingTransition(0,0);
            }
        });

        adapter = new ListAdapter(trashedAraay) ;

        listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(adapter);

        mPosterPathArray = getIntent().getStringArrayListExtra("posterPath");
        mTitleArray = getIntent().getStringArrayListExtra("title");
        mGenreArray = getIntent().getStringArrayListExtra("genre");

        int length = mPosterPathArray.size();

        for(int i=0;i<length;++i){
            adapter.addItem(mPosterPathArray.get(i), mTitleArray.get(i), mGenreArray.get(i));
        }
    }
}