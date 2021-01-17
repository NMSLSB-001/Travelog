package com.example.travelog.ui.Trips;

import android.content.Intent;
import android.os.Bundle;

import com.example.travelog.R;
import com.example.travelog.ui.Profile.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Itinerary_detail_day extends AppCompatActivity{

    private List<ItineraryRowDay> ItineraryListDay = new ArrayList<>();
    private ItineraryRowDayAdapter mAdapter;
    private RecyclerView recyclerView;
    private ImageView delete;
    private TextView title, date, description;

    DatabaseReference ref;
    String Username, selectedTitle, selectedID;
    List<String> dayTitle = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary_detail);

        title = findViewById(R.id.title);
        date = findViewById(R.id.date);

        //selectedTitle = selectedItinerary.getItineraryTitle();
        title.setText(selectedTitle);

        delete = findViewById(R.id.btn_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItinerary();
            }
        });


        //retrieve data
        Username = User.getName();
        Intent intent = getIntent();
        String itineraryID = intent.getStringExtra("itineraryID");
        //put read data from Itinerary_detail_dayUtils.getItineraryDayData into dayTitle here
        dayTitle = Itinerary_detail_dayUtils.getItineraryDayData(this,itineraryID);
       // ref = FirebaseDatabase.getInstance().getReference().child("itineraryDetails").child(Username).child(itineraryID);

        ItineraryListDay = new ArrayList<>();
        ItineraryListDay.clear();

      //  readDay(new FirebaseCallback() {
            //@Override
           // public void onCallback(List<String> list) {

               // ItineraryListDay.clear();

                for(int i = 0; i < dayTitle.size(); i++) {
                    ItineraryListDay.add(new ItineraryRowDay("New title ", "Make your own notes.", dayTitle.get(i)));
                }

                buildRecyclerView();

                //swipe function ----
               // ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
               // itemTouchHelper.attachToRecyclerView(recyclerView);
           // }
       // });



    }
    /*
    private void readDay(FirebaseCallback firebaseCallback){
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dss: snapshot.getChildren()) {
                    String title = dss.getKey();
                    dayTitle.add(title);
                }

                firebaseCallback.onCallback(dayTitle);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public interface FirebaseCallback {
       void onCallback(List<String> list);
    }


     */

    private void buildRecyclerView() {
        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ItineraryRowDayAdapter(ItineraryListDay);
        recyclerView.setAdapter(mAdapter);


        mAdapter.setOnItemClickListener(new ItineraryRowDayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ItineraryRowDay itineraryRowDay) {
                Intent intent = getIntent();
                String itineraryID = intent.getStringExtra("itineraryID");
                Intent intent2 = new Intent(Itinerary_detail_day.this, Itinerary_detail_hour.class);
                intent2.putExtra("dayTitle", itineraryRowDay.getDayNum());
                intent2.putExtra("itineraryID", itineraryID);

                startActivity(intent2);

            }
        });

    }
    /*

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


     */

    private void deleteItinerary() {
        Intent intent = getIntent();
        String itineraryID = intent.getStringExtra("itineraryID");
        ref = FirebaseDatabase.getInstance().getReference().child("itineraryDetails").child(Username);

        ref.child(itineraryID).removeValue();

        ref = FirebaseDatabase.getInstance().getReference().child("itinerary").child(Username);
        ref.child(itineraryID).removeValue();

        finish();
    }



}



