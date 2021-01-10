package com.example.travelog.ui.Trips.SwipeViewPager;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.travelog.R;
import com.example.travelog.User;
import com.example.travelog.ui.Trips.Itinerary_create;
import com.example.travelog.ui.Trips.Itinerary_detail_day;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itinerary_view);

        Username = User.getName();

        //retrieve data from database
        ref = FirebaseDatabase.getInstance().getReference().child("itinerary").child(Username);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//get all child itinerary under user in loop
                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                            //retrieve data
                    //failed if i copy the models.add here because of the adapter context
                    //here is the link explanation, you check if no way to do it then dont use recycler view also ok
                    //https://stackoverflow.com/questions/42140707/retrieve-string-out-of-addvalueeventlistener-firebase

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        adapter = new Adapter(models, this);
        models.add(new Model(R.drawable.brochure, createdTitle));

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

                    //tell the next activity which model title is selected
                    reg.putExtra("selected", models.get(position).getTitle());
                    startActivity(reg);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });





    }
}
