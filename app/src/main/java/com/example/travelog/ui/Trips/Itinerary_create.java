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
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Itinerary_create extends AppCompatActivity {

    private Button datePicker;
    private TextView startDate;
    private TextView endDate;
    private Button create;
    private EditText itinerary_title;
    private TextView invite;

    String createdStartDate;
    String createdEndDate;
    String createdTitle;


    public static final String p_createdTitle = "p_createdTitle";
    public static final String p_createdStartDate = "p_createdStartDate";
    public static final String p_createdEndDate = "p_createdEndDate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itinerary_create);

        datePicker = (Button) findViewById(R.id.datePicker);
        startDate = (TextView) findViewById(R.id.startDate);
        endDate = (TextView) findViewById(R.id.endDate);
        itinerary_title = (EditText) findViewById(R.id.itinerary_title);
        invite = (TextView) findViewById(R.id.invite);


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
                SimpleDateFormat simpleFormat = new SimpleDateFormat("dd MMM yyyy");
//              Display it by setText
                startDate.setText(simpleFormat.format(date1));
                endDate.setText(simpleFormat.format(date2));

                createdStartDate = startDate.getText().toString();
                createdEndDate = endDate.getText().toString();

            }
        });
    }


    public void create(View view) {
        Intent intent = new Intent(Itinerary_create.this, Itinerary_detail.class);
        create = (Button) findViewById(R.id.create);
        intent.putExtra(p_createdTitle, itinerary_title.getText().toString());
        intent.putExtra(p_createdStartDate, createdStartDate);
        intent.putExtra(p_createdEndDate, createdEndDate);

        startActivity(intent);
    }




}







