package com.example.androidphotos80;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toolbar;

import com.example.androidphotos80.model.Photo;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class searchResults extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Photo> resultPhotos;
    private RecyclerViewAdapterSearch adapter;
    private int searchFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        Toolbar toolbar = findViewById(R.id.toolbar);
        getSupportActionBar().setTitle("Search Results");

        // Get intent result Photos for display
        Intent intent = getIntent();
        //searchFlag = intent.getIntExtra("flag", 0);
        resultPhotos = (ArrayList<Photo>) intent.getSerializableExtra("results");
        /*
        Set<Photo> resultSet = new HashSet<Photo>(resultPhotos);
        resultPhotos = new ArrayList<Photo>(resultSet);
         */
        ArrayList<Photo> finalResults = new ArrayList<>();
        // Remove duplicates from results
        for(Photo p : resultPhotos){
            if(!finalResults.contains(p)){
                finalResults.add(p);
            }
        }

        recyclerView = findViewById(R.id.recyclerViewSearch);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RecyclerViewAdapterSearch(this, finalResults);
        recyclerView.setAdapter(adapter);

    }
}