package com.example.travelog.ui.Trips;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.travelog.Itinerary_detail;
import com.example.travelog.R;
import com.example.travelog.change_password_Activity;

public class TripsFragment extends Fragment {

    private Button trip_plan;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_itinerary_detail, container, false);


        return view;
    }
}