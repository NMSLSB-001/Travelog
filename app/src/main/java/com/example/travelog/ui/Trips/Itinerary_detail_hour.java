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
import com.example.travelog.ui.Trips.SwipeViewPager.Itinerary_View;
import com.google.android.material.appbar.CollapsingToolbarLayout;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary_on_hour);

        createItineraryListHour();
        buildRecyclerView();

        add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Itinerary_detail_hour.this, Itinerary_add_hour.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }


    public void removeItem(int position) {
        ItineraryListHour.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    public void createItineraryListHour() {
        ItineraryListHour = new ArrayList<>();
        ItineraryListHour.add(new ItineraryRowHour("New Title1", "New Description", "Location" , "8:00", "9:00"));
        ItineraryListHour.add(new ItineraryRowHour("New Title2", "New Description", "Location" , "9:00", "10:00"));
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
                intent.putExtra(Itinerary_add_hour.EXTRA_ENDTIME, itineraryRowDay.getEndTime());

                startActivityForResult(intent, EDIT_NOTE_REQUEST);

            }
        });

    }

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(Itinerary_add_hour.EXTRA_TITLE);
            String description = data.getStringExtra(Itinerary_add_hour.EXTRA_DESCRIPTION);
            String location = data.getStringExtra(Itinerary_add_hour.EXTRA_LOCATION);
            String start = data.getStringExtra(Itinerary_add_hour.EXTRA_STARTTIME);
            String end = data.getStringExtra(Itinerary_add_hour.EXTRA_ENDTIME);
            ItineraryListHour.add(new ItineraryRowHour(title, description, location , start, end));
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
            String title = data.getStringExtra(Itinerary_add_hour.EXTRA_TITLE);
            String description = data.getStringExtra(Itinerary_add_hour.EXTRA_DESCRIPTION);
            String location = data.getStringExtra(Itinerary_add_hour.EXTRA_LOCATION);
            String start = data.getStringExtra(Itinerary_add_hour.EXTRA_STARTTIME);
            String end = data.getStringExtra(Itinerary_add_hour.EXTRA_ENDTIME);

            String titleOld = data.getStringExtra(Itinerary_add_hour.EXTRA_TITLE_OLD);
            String descriptionOld = data.getStringExtra(Itinerary_add_hour.EXTRA_DESCRIPTION_OLD);
            String locationOld = data.getStringExtra(Itinerary_add_hour.EXTRA_LOCATION_OLD);
            String startOld = data.getStringExtra(Itinerary_add_hour.EXTRA_STARTTIME_OLD);
            String endOld = data.getStringExtra(Itinerary_add_hour.EXTRA_ENDTIME_OLD);

            ItineraryRowHour rowToDelete = null;
            for(ItineraryRowHour row:ItineraryListHour){
                if(row.getStartTime().equals(startOld) && row.getEndTime().equals(endOld) && row.getRowTitle().equals(titleOld) && row.getDescription().equals(descriptionOld) && row.getLocation().equals(locationOld))
                    rowToDelete = row;
            }

            if(rowToDelete==null) {
                System.out.println("No customer found");
                System.out.println(startOld);
                System.out.println(endOld);
                System.out.println(titleOld);
            }
            else
                ItineraryListHour.remove(rowToDelete);

            ItineraryRowHour row = new ItineraryRowHour(title, description, location , start, end);
            row.setId(id);
            ItineraryListHour.add(row);
            Collections.sort(ItineraryListHour, ItineraryRowHour.AscendingHour);
            buildRecyclerView();
            Toast.makeText(this, "Row updated", Toast.LENGTH_SHORT).show();
        }
        else
            {
            Toast.makeText(this, "Row not saved", Toast.LENGTH_SHORT).show();
        }
    }



    private Date stringToDate(String aDate,String aFormat) {

        if(aDate==null) return null;
        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat simpledateformat = new SimpleDateFormat(aFormat);
        Date stringDate = simpledateformat.parse(aDate, pos);
        return stringDate;

    }

}



