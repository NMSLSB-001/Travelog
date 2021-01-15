package com.example.travelog.ui.Trips;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelog.R;
import com.example.travelog.User;
import com.example.travelog.ui.Trips.SwipeViewPager.Itinerary_View;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Itinerary_detail_hour extends AppCompatActivity{

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    private List<ItineraryRowHour> ItineraryListHour = new ArrayList<>();
    private ItineraryRowHourAdapter mAdapter;
    private RecyclerView recyclerView;
    private Button add;
    private TextView title, date;

    String Username;
    DatabaseReference ref;
    List<String> hourTime = new ArrayList<>();
    List<String> hourTitle = new ArrayList<>();
    List<String> hourDescription = new ArrayList<>();
    List<String> hourLocation = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary_on_hour);

        //retrieve data
        Username = User.getName();
        Intent intent = getIntent();
        String dayTitle = intent.getStringExtra("dayTitle");
        String itineraryID = intent.getStringExtra("itineraryID");
        ref = FirebaseDatabase.getInstance().getReference().child("itineraryDetails").child(Username).child(itineraryID).child(dayTitle);


        ItineraryListHour = new ArrayList<>();

        readHour(new FirebaseCallback() {
            @Override
            public void onCallback(List<String> list) {
                ItineraryListHour = new ArrayList<>();
                for(int i = 0; i < hourTime.size(); i++) {
                    ItineraryListHour.add(new ItineraryRowHour(hourTitle.get(i), hourDescription.get(i), hourLocation.get(i), hourTime.get(i)));
                }

                buildRecyclerView();

                //add button
                add = findViewById(R.id.add);
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = getIntent();
                        String dayTitle = intent.getStringExtra("dayTitle");
                        String itineraryID = intent.getStringExtra("itineraryID");
                        Intent intent2 = new Intent(Itinerary_detail_hour.this, Itinerary_add_hour.class);
                        intent2.putExtra("dayTitle", dayTitle);
                        intent2.putExtra("itineraryID", itineraryID);
                        startActivity(intent2);
                        ItineraryListHour = new ArrayList<>();
                    }
                });

                //swipe function ---
                ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
                itemTouchHelper.attachToRecyclerView(recyclerView);

            }
        });
    }


    public void removeItem(int position) {
        ItineraryListHour.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    private void readHour(FirebaseCallback firebaseCallback){
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dss: snapshot.getChildren()) {
                    String time = dss.getKey();
                    if(time == "base_data"){
                        continue;
                    } else {
                        String description = String.valueOf(dss.child("description").getValue());
                        String location = String.valueOf(dss.child("location").getValue());
                        String title = String.valueOf(dss.child("title").getValue());
                        hourTime.add(time);
                        hourDescription.add(description);
                        hourLocation.add(location);
                        hourTitle.add(title);
                    }

                }

                firebaseCallback.onCallback(hourTime);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public interface FirebaseCallback {
        void onCallback(List<String> list);
    }

    private void buildRecyclerView() {
        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ItineraryRowHourAdapter(ItineraryListHour);
        recyclerView.setAdapter(mAdapter);


        mAdapter.setOnItemClickListener(new ItineraryRowHourAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }

            @Override
            public void onItemClick(ItineraryRowHour itineraryRowDay) {
                Intent intent = new Intent(Itinerary_detail_hour.this, Itinerary_add_hour.class);
                intent.putExtra(Itinerary_add_hour.EXTRA_ID, itineraryRowDay.getId());
                intent.putExtra(Itinerary_add_hour.EXTRA_TITLE, itineraryRowDay.getRowTitle());
                intent.putExtra(Itinerary_add_hour.EXTRA_DESCRIPTION, itineraryRowDay.getDescription());
                intent.putExtra(Itinerary_add_hour.EXTRA_LOCATION, itineraryRowDay.getLocation());
                intent.putExtra(Itinerary_add_hour.EXTRA_STARTTIME, itineraryRowDay.getStartTime());

                startActivityForResult(intent, EDIT_NOTE_REQUEST);

            }
        });

    }


    //Swipe function
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END , 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();

            Collections.swap(ItineraryListHour, fromPosition, toPosition);
            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);

            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {


        }
    };

    //activity on add & edit item
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
//            String title = data.getStringExtra(Itinerary_add_hour.EXTRA_TITLE);
//            String description = data.getStringExtra(Itinerary_add_hour.EXTRA_DESCRIPTION);
//            String location = data.getStringExtra(Itinerary_add_hour.EXTRA_LOCATION);
//            String start = data.getStringExtra(Itinerary_add_hour.EXTRA_STARTTIME);
//            ItineraryListHour.add(new ItineraryRowHour(title, description, location , start));
            Collections.sort(ItineraryListHour, ItineraryRowHour.AscendingHour);
            buildRecyclerView();
            Toast.makeText(this, "Row saved", Toast.LENGTH_SHORT).show();
        }
        else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(Itinerary_add_hour.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Row can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
//            String title = data.getStringExtra(Itinerary_add_hour.EXTRA_TITLE);
//            String description = data.getStringExtra(Itinerary_add_hour.EXTRA_DESCRIPTION);
//            String location = data.getStringExtra(Itinerary_add_hour.EXTRA_LOCATION);
//            String start = data.getStringExtra(Itinerary_add_hour.EXTRA_STARTTIME);
//
//            String titleOld = data.getStringExtra(Itinerary_add_hour.EXTRA_TITLE_OLD);
//            String descriptionOld = data.getStringExtra(Itinerary_add_hour.EXTRA_DESCRIPTION_OLD);
//            String locationOld = data.getStringExtra(Itinerary_add_hour.EXTRA_LOCATION_OLD);
//            String startOld = data.getStringExtra(Itinerary_add_hour.EXTRA_STARTTIME_OLD);
//
//            ItineraryRowHour rowToDelete = null;
//            for(ItineraryRowHour row:ItineraryListHour){
//                if(row.getStartTime().equals(startOld)  && row.getRowTitle().equals(titleOld) && row.getDescription().equals(descriptionOld) && row.getLocation().equals(locationOld))
//                    rowToDelete = row;
//            }
//
//            if(rowToDelete==null) {
//                System.out.println("No customer found");
//            }
//            else
//                ItineraryListHour.remove(rowToDelete);
//
//            ItineraryRowHour row = new ItineraryRowHour(title, description, location , start);
//            row.setId(id);
//            ItineraryListHour.add(row);

            Collections.sort(ItineraryListHour, ItineraryRowHour.AscendingHour);
            buildRecyclerView();
            Toast.makeText(this, "Row updated", Toast.LENGTH_SHORT).show();
        }
        else
            {
            Toast.makeText(this, "Row not saved", Toast.LENGTH_SHORT).show();
        }
    }



}



