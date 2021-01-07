package com.example.travelog.ui.Trips;

import android.os.Bundle;

import com.example.travelog.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Itinerary_detail extends AppCompatActivity {

    private List<ItineraryRow> ItineraryList = new ArrayList<>();
    private RecyclerView recyclerView;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary_detail);


        buildRecyclerView();

        Button add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //go to edit screen
                //pass in info
                addItineraryList("NewTitle", "Description", "Location", "17", "Time2");
                buildRecyclerView();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    private void addItineraryList(String rowTitle, String description, String location, String startTime, String endTime){

        ItineraryList.add(new ItineraryRow(rowTitle, description, location, startTime, endTime));
        Collections.sort(ItineraryList, ItineraryRow.AscendingHour);

    }


    private void buildRecyclerView() {
        recyclerView = findViewById(R.id.recycleView);
        ItineraryRowAdapter itineraryRowAdapter = new ItineraryRowAdapter(ItineraryList);
        recyclerView.setAdapter(itineraryRowAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }



}