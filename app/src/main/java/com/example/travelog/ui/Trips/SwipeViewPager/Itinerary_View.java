package com.example.travelog.ui.Trips.SwipeViewPager;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.travelog.R;
import com.example.travelog.ui.Trips.Itinerary_create;
import com.example.travelog.ui.Trips.Itinerary_detail_day;

import java.util.ArrayList;
import java.util.List;

public class Itinerary_View extends AppCompatActivity {
    ViewPager viewPager;
    Adapter adapter;
    List<Model> models = new ArrayList<>();
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    public static final String p_createdTitle = "p_createdTitle";
    public static final String p_createdStartDate = "p_createdStartDate";
    public static final String p_createdEndDate = "p_createdEndDate";
    public static final String p_createdLoc = "p_createdLoc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
        setContentView(R.layout.itinerary_view);

        Bundle extras = getIntent().getExtras();
        String createdTitle = extras.getString(Itinerary_create.p_createdTitle);
        String createdStartDate = extras.getString(Itinerary_create.p_createdStartDate);
        String createdEndDate = extras.getString(Itinerary_create.p_createdEndDate);
        String createdLoc = extras.getString(Itinerary_create.p_createdLoc);
        int numOfDay = extras.getInt("Number", 0);

        adapter = new Adapter(models, this);


        models.add(new Model(R.drawable.brochure, createdTitle, createdStartDate,createdEndDate,"Here is your description!"));


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
                    Intent reg = new Intent(Itinerary_View.this, Itinerary_detail_day.class);
                    reg.putExtra(p_createdTitle, createdTitle);
                    reg.putExtra(p_createdStartDate, createdStartDate);
                    reg.putExtra(p_createdEndDate, createdEndDate);
                    reg.putExtra(p_createdLoc, createdLoc);
                    reg.putExtra("num", numOfDay);
                    startActivity(reg);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



    }
}
