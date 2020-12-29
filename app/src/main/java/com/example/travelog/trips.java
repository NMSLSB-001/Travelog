package com.example.travelog;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelog.fragement.Fragment_Articles;
import com.example.travelog.fragement.Fragment_TripPlan;

public class trips extends AppCompatActivity {

    private TextView trip_plan;
    private TextView articles;
    private Fragment_TripPlan trip_plan_activity;
    private Fragment_Articles articles_activity;
    private int Tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trips);
        initView();
        trip_plan_activity=new Fragment_TripPlan();
        articles_activity=new Fragment_Articles();
    }


    private void initView() {
        trip_plan = findViewById(R.id.trip_plan);
        articles = findViewById(R.id.articles);

        trip_plan.setOnClickListener(this);
        articles.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (Tag == 0){
            Tag=1;
            switch (v.getId()){
                case R.id.trip_plan:
                    getSupportFragmentManager().beginTransaction().add(R.id.trips_container,trip_plan_activity).commitAllowingStateLoss();
                    break;
                case R.id.articles:
                    getSupportFragmentManager().beginTransaction().add(R.id.trips_container,articles_activity).commitAllowingStateLoss();
                    break;
            }
        }else if(Tag==1){
            switch (v.getId()){
                case R.id.trip_plan:
                    getSupportFragmentManager().beginTransaction().add(R.id.trips_container,trip_plan_activity).commitAllowingStateLoss();
                    break;
                case R.id.articles:
                    getSupportFragmentManager().beginTransaction().add(R.id.trips_container,articles_activity).commitAllowingStateLoss();
                    break;
            }
        }
    }
}
