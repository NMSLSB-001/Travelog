package com.example.travelog.ui.Trips;

import android.content.Intent;
import android.os.Bundle;

import com.example.travelog.R;
import com.example.travelog.User;
import com.example.travelog.ui.Trips.SwipeViewPager.Itinerary_View;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Itinerary_detail_day extends AppCompatActivity{

    private List<ItineraryRowDay> ItineraryListDay = new ArrayList<>();
    private ItineraryRowDayAdapter mAdapter;
    private RecyclerView recyclerView;
    private Button add;
    private TextView title, date, description;

    DatabaseReference ref;
    String Username;
    //String selected = getIntent().getStringExtra("selected");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary_detail);

        title = findViewById(R.id.title);
        date = findViewById(R.id.date);

        title.setText("hehek");

        //retrieve data
        Username = User.getName();
        ref = FirebaseDatabase.getInstance().getReference().child("itinerary").child(Username);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //retrieve data
                        //display them into list of days
                        //the recyclerview is using ItineraryListDay arraylist;
                        //may need to use the createItineraryListDay() function for this
                        //click them and go to Itinerary_detail_day
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        createItineraryListDay();
        buildRecyclerView();

        //Collapsing toolbar things ----
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        //swipe function ----
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }


    public void createItineraryListDay() {
        ItineraryListDay = new ArrayList<>();
        ItineraryListDay.add(new ItineraryRowDay("New title ", "Make your own notes.", "Day 1"));
        ItineraryListDay.add(new ItineraryRowDay("New title 2 ", "Make your own notes 2.", "Day 2"));

    }


    private void buildRecyclerView() {
        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ItineraryRowDayAdapter(ItineraryListDay);
        recyclerView.setAdapter(mAdapter);


        mAdapter.setOnItemClickListener(new ItineraryRowDayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ItineraryRowDay itineraryRowDay) {
                Intent intent = new Intent(Itinerary_detail_day.this, Itinerary_detail_hour.class);
                startActivity(intent);

            }
        });

    }

    //swipe function ----
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END , 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();

            Collections.swap(ItineraryListDay, fromPosition, toPosition);
            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);

            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {


        }
    };


}



