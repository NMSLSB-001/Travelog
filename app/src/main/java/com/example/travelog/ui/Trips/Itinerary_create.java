package com.example.travelog.ui.Trips;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import com.example.travelog.R;
import com.example.travelog.User;
import com.example.travelog.ui.Trips.SwipeViewPager.Itinerary_View;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Itinerary_create extends AppCompatActivity{

    private Button datePicker;
    private TextView startDate;
    private TextView endDate;
    private TextView textView2;
    private Button create;
    private EditText itinerary_title, location;
    String Username;
    Integer num;
    String createdTitle, createdStartDate, createdEndDate, createdLocation;
    List<Date> dates = new ArrayList<Date>();

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    public static final String p_createdTitle = "p_createdTitle";
    public static final String p_createdStartDate = "p_createdStartDate";
    public static final String p_createdEndDate = "p_createdEndDate";
    public static final String p_createdLoc = "p_createdLoc";

    public Itinerary_create(){};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itinerary_create);

        Username = User.getName();

        datePicker = findViewById(R.id.datePicker);
        startDate = findViewById(R.id.startDate);
        endDate = (TextView) findViewById(R.id.endDate);
        itinerary_title = (EditText) findViewById(R.id.itinerary_title);
        location = (EditText) findViewById(R.id.location);

        //calendar constraints
        CalendarConstraints.Builder constraintBuilder = new CalendarConstraints.Builder();
        constraintBuilder.setValidator(DateValidatorPointForward.now());

        //materialDatePicker
        final MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("SELECT YOUR START DATE");
        builder.setCalendarConstraints(constraintBuilder.build());

        // now build the material date picker dialog
        final MaterialDatePicker materialDatePicker = builder.build();

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(), "DATE PICKER");
            }
        });


        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
            @Override
            public void onPositiveButtonClick(Pair<Long, Long> selection) {

                Pair selectedDates = (Pair) materialDatePicker.getSelection();
//              then obtain the startDate & endDate from the range
                final Pair<Date, Date> rangeDate = new Pair<>(new Date((Long) selectedDates.first), new Date((Long) selectedDates.second));
//              assigned variables
                Date date1 = rangeDate.first;
                Date date2 = rangeDate.second;
//              Format the dates in ur desired display mode
                SimpleDateFormat simpleFormat = new SimpleDateFormat("dd/MM/yyyy");
//              Display it by setText
                startDate.setText(simpleFormat.format(date1));
                endDate.setText(simpleFormat.format(date2));

                createdStartDate = startDate.getText().toString();
                createdEndDate = endDate.getText().toString();

                //list of days between in DATE datatype
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(date1);
                dates.clear();

                while (calendar.getTime().before(date2))
                {
                    Date result = calendar.getTime();
                    dates.add(result);
                    calendar.add(Calendar.DATE, 1);

                }

                num = dates.size();

            }
        });
    }



    public void create(View view) {
        Intent intent = new Intent(Itinerary_create.this, Itinerary_View.class);
        create = (Button) findViewById(R.id.create);

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("itinerary");

        createdTitle = itinerary_title.getText().toString().trim();
        createdLocation = location.getText().toString().trim();

        SimpleDateFormat timeStampFormat = new SimpleDateFormat("ddMMyyyyHHmm");
        Date GetDate = new Date();
        String itineraryID = timeStampFormat.format(GetDate);

        firebase_itinerary firebase_itinerary = new firebase_itinerary(createdTitle, createdStartDate, createdEndDate, createdLocation);
        reference.child(Username).child(itineraryID).setValue(firebase_itinerary);
//        intent.putExtra("Number", num);
        startActivity(intent);

    }
}







