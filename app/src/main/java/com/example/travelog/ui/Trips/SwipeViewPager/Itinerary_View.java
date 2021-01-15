package com.example.travelog.ui.Trips.SwipeViewPager;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.travelog.R;
import com.example.travelog.ui.Profile.User;
import com.example.travelog.ui.Trips.Itinerary_detail_day;
import com.example.travelog.selectedItinerary;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Itinerary_View extends AppCompatActivity {
    ViewPager viewPager;
    Adapter adapter;
    List<Model> models = new ArrayList<>();
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    String Username;
    String createdTitle;
    DatabaseReference ref;
    List<String> itineraryList = new ArrayList<>();
    List<String> idList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itinerary_view);

        Username = User.getName();

        //retrieve data from database
        ref = FirebaseDatabase.getInstance().getReference().child("itinerary").child(Username);

        adapter = new Adapter(models, this);

        readTitle(new FirebaseCallback() {
            @Override
            public void onCallback(List<String> list, List<String> list2) {
                for(int i = 0; i < itineraryList.size(); i++) {
                    models.add(new Model(R.drawable.brochure, itineraryList.get(i), idList.get(i)));
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
                            //Intent reg = new Intent(Itinerary_View.this, Itinerary_detail_day.class);

//                            Log.i("testing:", "ur mom");

                            //tell the next activity which model title is selected
                            //startActivity(reg);
                            return;
                        }

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
                //Log.i("testing", itineraryList.toString());
            }
        });


    }

    private void readTitle(FirebaseCallback firebaseCallback) {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dss: snapshot.getChildren()) {
                    String title = String.valueOf(dss.child("itinerary_title").getValue());
                    String id = String.valueOf(dss.child("itineraryID").getValue());

                    itineraryList.add(title);
                    idList.add(id);
                }

                firebaseCallback.onCallback(itineraryList, idList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public interface FirebaseCallback {
        void onCallback(List<String> list, List<String> list2);
    }
}
