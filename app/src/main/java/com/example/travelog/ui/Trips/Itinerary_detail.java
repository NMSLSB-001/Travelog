package com.example.travelog.ui.Trips;

import android.content.Intent;
import android.database.DatabaseErrorHandler;
import android.os.Bundle;

import com.example.travelog.R;
import com.example.travelog.ui.Trips.SwipeViewPager.Itinerary_OnDay;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Itinerary_detail extends AppCompatActivity{

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    private List<ItineraryRow> ItineraryList = new ArrayList<>();
    private ItineraryRowAdapter mAdapter;
    private RecyclerView recyclerView;
    private Button add;
    private TextView day, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary_detail);

        Intent mIntent = getIntent();
        int days = mIntent.getIntExtra("daynum", 0);

        System.out.println(days);
        System.out.println(days);
        System.out.println(days);

        day = findViewById(R.id.day);
        date = findViewById(R.id.date);

        day.setText("Day " + (days+1));

        createItineraryList();
        buildRecyclerView();

        add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Itinerary_detail.this, Itinerary_add.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }


    public void removeItem(int position) {
        ItineraryList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    public void createItineraryList() {
        ItineraryList = new ArrayList<>();
        ItineraryList.add(new ItineraryRow("New Title1", "New Description", "Location" , "8:00", "9:00"));
        ItineraryList.add(new ItineraryRow("New Title2", "New Description", "Location" , "9:00", "10:00"));
    }

    private void addItineraryList(String rowTitle, String description, String location, String startTime, String endTime){

        ItineraryList.add(new ItineraryRow(rowTitle, description, location, startTime, endTime));
        Collections.sort(ItineraryList, ItineraryRow.AscendingHour);
    }


    private void buildRecyclerView() {
        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ItineraryRowAdapter(ItineraryList);
        recyclerView.setAdapter(mAdapter);


        mAdapter.setOnItemClickListener(new ItineraryRowAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }

            @Override
            public void onItemClick(ItineraryRow itineraryRow) {
                Intent intent = new Intent(Itinerary_detail.this, Itinerary_add.class);
                intent.putExtra(Itinerary_add.EXTRA_ID, itineraryRow.getId());
                intent.putExtra(Itinerary_add.EXTRA_TITLE, itineraryRow.getRowTitle());
                intent.putExtra(Itinerary_add.EXTRA_DESCRIPTION, itineraryRow.getDescription());
                intent.putExtra(Itinerary_add.EXTRA_LOCATION, itineraryRow.getLocation());
                intent.putExtra(Itinerary_add.EXTRA_STARTTIME, itineraryRow.getStartTime());
                intent.putExtra(Itinerary_add.EXTRA_ENDTIME, itineraryRow.getEndTime());

                startActivityForResult(intent, EDIT_NOTE_REQUEST);

            }
        });

    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END , 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();

            Collections.swap(ItineraryList, fromPosition, toPosition);
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
            String title = data.getStringExtra(Itinerary_add.EXTRA_TITLE);
            String description = data.getStringExtra(Itinerary_add.EXTRA_DESCRIPTION);
            String location = data.getStringExtra(Itinerary_add.EXTRA_LOCATION);
            String start = data.getStringExtra(Itinerary_add.EXTRA_STARTTIME);
            String end = data.getStringExtra(Itinerary_add.EXTRA_ENDTIME);
            ItineraryList.add(new ItineraryRow(title, description, location , start, end));
            Collections.sort(ItineraryList, ItineraryRow.AscendingHour);
            buildRecyclerView();
            Toast.makeText(this, "Row saved", Toast.LENGTH_SHORT).show();
        }
        else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(Itinerary_add.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Row can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = data.getStringExtra(Itinerary_add.EXTRA_TITLE);
            String description = data.getStringExtra(Itinerary_add.EXTRA_DESCRIPTION);
            String location = data.getStringExtra(Itinerary_add.EXTRA_LOCATION);
            String start = data.getStringExtra(Itinerary_add.EXTRA_STARTTIME);
            String end = data.getStringExtra(Itinerary_add.EXTRA_ENDTIME);

            String titleOld = data.getStringExtra(Itinerary_add.EXTRA_TITLE_OLD);
            String descriptionOld = data.getStringExtra(Itinerary_add.EXTRA_DESCRIPTION_OLD);
            String locationOld = data.getStringExtra(Itinerary_add.EXTRA_LOCATION_OLD);
            String startOld = data.getStringExtra(Itinerary_add.EXTRA_STARTTIME_OLD);
            String endOld = data.getStringExtra(Itinerary_add.EXTRA_ENDTIME_OLD);

            ItineraryRow rowToDelete = null;
            for(ItineraryRow row:ItineraryList){
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
                ItineraryList.remove(rowToDelete);

            ItineraryRow row = new ItineraryRow(title, description, location , start, end);
            row.setId(id);
            ItineraryList.add(row);
            Collections.sort(ItineraryList, ItineraryRow.AscendingHour);
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



