package com.example.travelog.ui.Trips;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.travelog.R;

public class Itinerary_edit extends AppCompatActivity {

    TextView rowTitle, description, location, startTime, endTime;
    Button time_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itinerary_edit);

        ActionBar actionBar = getSupportActionBar();

        rowTitle = findViewById(R.id.rowTitle);
        description = findViewById(R.id.description);
        location = findViewById(R.id.location);
        startTime = findViewById(R.id.startTime);
        endTime = findViewById(R.id.endTime);
        time_edit = findViewById(R.id.time_edit);

    }
}