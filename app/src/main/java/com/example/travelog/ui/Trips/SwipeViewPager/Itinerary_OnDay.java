package com.example.travelog.ui.Trips.SwipeViewPager;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.travelog.R;
import com.example.travelog.ui.Trips.Itinerary_create;
import com.example.travelog.ui.Trips.Itinerary_detail;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Itinerary_OnDay extends AppCompatActivity {
    ViewPager viewPager;
    Adapter adapter;
    List<Model> models;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
        setContentView(R.layout.onday);

        Bundle extras = getIntent().getExtras();
        String createdTitle = extras.getString(Itinerary_create.p_createdTitle);
        String createdStartDate = extras.getString(Itinerary_create.p_createdStartDate);
        String createdEndDate = extras.getString(Itinerary_create.p_createdEndDate);
        int numOfDay = extras.getInt("Number", 0);
        System.out.println(numOfDay);
        System.out.println(numOfDay);
        System.out.println(numOfDay);
        System.out.println(numOfDay);
        System.out.println(numOfDay);

        title = findViewById(R.id.title);
        title.setText(createdTitle);

        models = new ArrayList<>();
        adapter = new Adapter(models, this);

        int count = 0;
        for (int i=0; i<=numOfDay ;i++) {
            count++;
            models.add(new Model(R.drawable.brochure, "Day " + count, "Here is your description!"));
        }

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(130, 0, 130, 0);

        Integer[] colors_temp = {
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color2),
                getResources().getColor(R.color.color3),
                getResources().getColor(R.color.color4)
        };

        colors = colors_temp;

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (position < (adapter.getCount() -1) && position < (colors.length - 1)) {
                    viewPager.setBackgroundColor(

                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position],
                                    colors[position + 1]
                            )
                    );
                }

                else {
                    viewPager.setBackgroundColor(colors[colors.length - 1]);
                }
            }

            @Override
            public void onPageSelected(int position) {

                // TODO Auto-generated method stub
                if(position==viewPager.getAdapter().getCount()){
                    Intent reg = new Intent(Itinerary_OnDay.this,Itinerary_detail.class);
                    reg.putExtra("daynum", position);
                    System.out.println(position);
                    System.out.println(position);
                    System.out.println(position);
                    startActivity(reg);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



    }
}
