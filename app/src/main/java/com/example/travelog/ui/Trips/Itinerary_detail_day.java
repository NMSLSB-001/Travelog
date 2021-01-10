package com.example.travelog.ui.Trips;

import android.content.Intent;
import android.os.Bundle;

import com.example.travelog.R;
import com.example.travelog.ui.Trips.SwipeViewPager.Itinerary_View;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Itinerary_detail_day extends AppCompatActivity{

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    private List<ItineraryRowDay> ItineraryListDay = new ArrayList<>();
    private ItineraryRowDayAdapter mAdapter;
    private RecyclerView recyclerView;
    private Button add;
    private TextView title, date;
    private int days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary_detail);

        Intent mIntent = getIntent();
        String createdTitle = mIntent.getStringExtra(Itinerary_create.p_createdTitle);
        String createdStartDate = mIntent.getStringExtra(Itinerary_create.p_createdStartDate);
        String createdEndDate = mIntent.getStringExtra(Itinerary_create.p_createdEndDate);
        String createdLoc = mIntent.getStringExtra(Itinerary_create.p_createdLoc);
        days = mIntent.getIntExtra("daynum", 0);

        title = findViewById(R.id.title);
        date = findViewById(R.id.date);

        title.setText(createdTitle);
        date.setText(createdStartDate + " - " + createdEndDate);

        createItineraryListDay();
        buildRecyclerView();

        add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Itinerary_detail_day.this, Itinerary_add_day.class);
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
        ItineraryListDay.remove(position);
        days--;
        mAdapter.notifyItemRemoved(position);
    }

    public void createItineraryListDay() {
        ItineraryListDay = new ArrayList<>();

        for(int i = 0; i <= days; i++)
        ItineraryListDay.add(new ItineraryRowDay("New title " + (i+1), "Make your own notes.", "Day " + (i+1)));

    }


    private void buildRecyclerView() {
        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ItineraryRowDayAdapter(ItineraryListDay);
        recyclerView.setAdapter(mAdapter);


        mAdapter.setOnItemClickListener(new ItineraryRowDayAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }

            @Override
            public void onItemClick(ItineraryRowDay itineraryRowDay) {
                Intent intent = new Intent(Itinerary_detail_day.this, Itinerary_detail_hour.class);
                startActivity(intent);

            }
        });

    }

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(Itinerary_add_day.EXTRA_TITLE);
            String description = data.getStringExtra(Itinerary_add_day.EXTRA_DESCRIPTION);
            days++;
            String addDay = "Day " + String.valueOf(days+1);
            ItineraryListDay.add(new ItineraryRowDay(title, description, addDay));
            buildRecyclerView();
            Toast.makeText(this, "Row saved", Toast.LENGTH_SHORT).show();
        }
//        else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
//            int id = data.getIntExtra(Itinerary_add.EXTRA_ID, -1);
//            if (id == -1) {
//                Toast.makeText(this, "Row can't be updated", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            String title = data.getStringExtra(Itinerary_add.EXTRA_TITLE);
//            String description = data.getStringExtra(Itinerary_add.EXTRA_DESCRIPTION);
//            String dayNumber = data.getStringExtra(Itinerary_add.EXTRA_DAYNUM);
//
//            String titleOld = data.getStringExtra(Itinerary_add.EXTRA_TITLE_OLD);
//            String descriptionOld = data.getStringExtra(Itinerary_add.EXTRA_DESCRIPTION_OLD);
//            String dayNumberOld = data.getStringExtra(Itinerary_add.EXTRA_DAYNUM);
//
//            ItineraryRowDay rowToDelete = null;
//            for(ItineraryRowDay row:ItineraryListDay){
//                if(row.getDayTitle().equals(titleOld) && row.getDescription().equals(descriptionOld) && row.getDayNum().equals(dayNumberOld))
//                    rowToDelete = row;
//            }
//
//            if(rowToDelete==null) {
//                System.out.println("No customer found");
//            }
//            else ItineraryListDay.remove(rowToDelete);
//
//            ItineraryRowDay row = new ItineraryRowDay(title, description, dayNumber);
//            row.setId(id);
//            ItineraryListDay.add(row);
//            buildRecyclerView();
//            Toast.makeText(this, "Row updated", Toast.LENGTH_SHORT).show();
//        }
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



