package com.example.travelog.ui.Trips;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelog.R;

public class Itinerary_add extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.example.travelog.ui.Trips.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "com.example.travelog.ui.Trips.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.example.travelog.ui.Trips.EXTRA_DESCRIPTION";
    public static final String EXTRA_LOCATION =
            "com.example.travelog.ui.Trips.EXTRA_LOCATION";
    public static final String EXTRA_STARTTIME =
            "com.example.travelog.ui.Trips.EXTRA_STARTTIME";
    public static final String EXTRA_ENDTIME =
            "com.example.travelog.ui.Trips.EXTRA_ENDTIME";

    public static final String EXTRA_TITLE_OLD =
            "com.example.travelog.ui.Trips.EXTRA_TITLE_OLD";
    public static final String EXTRA_DESCRIPTION_OLD =
            "com.example.travelog.ui.Trips.EXTRA_DESCRIPTION_OLD";
    public static final String EXTRA_LOCATION_OLD =
            "com.example.travelog.ui.Trips.EXTRA_LOCATION_OLD";
    public static final String EXTRA_STARTTIME_OLD =
            "com.example.travelog.ui.Trips.EXTRA_STARTTIME_OLD";
    public static final String EXTRA_ENDTIME_OLD =
            "com.example.travelog.ui.Trips.EXTRA_ENDTIME_OLD";

    private EditText editTextRowTitle;
    private EditText editTextDescription;
    private EditText editTextLocation;
    private TextView startTime, endTime;
    private TimePicker timePicker1, timePicker2;
    private String titleOld, descriptionOld, locationOld, startingTimeOld, endingTimeOld;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dialog);

        editTextRowTitle = findViewById(R.id.rowTitle_edit);
        editTextDescription = findViewById(R.id.description_edit);
        editTextLocation = findViewById(R.id.location_edit);
        startTime = findViewById(R.id.startTime);
        endTime = findViewById(R.id.endTime);
        timePicker1 = findViewById(R.id.time_picker1);
        timePicker2 = findViewById(R.id.time_picker2);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Row");
            editTextRowTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            editTextLocation.setText(intent.getStringExtra(EXTRA_LOCATION));
            startTime.setText(intent.getStringExtra(EXTRA_STARTTIME));
            endTime.setText(intent.getStringExtra(EXTRA_ENDTIME));

            titleOld = intent.getStringExtra(EXTRA_TITLE);
            descriptionOld = intent.getStringExtra(EXTRA_DESCRIPTION);
            locationOld = intent.getStringExtra(EXTRA_LOCATION);
            startingTimeOld = intent.getStringExtra(EXTRA_STARTTIME);
            endingTimeOld = intent.getStringExtra(EXTRA_ENDTIME);

        } else {
            setTitle("Add Row");
        }

    }
    private void saveRow() {

        String title = editTextRowTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        String location = editTextLocation.getText().toString();
        int hour1 = timePicker1.getCurrentHour();
        int min1 = timePicker1.getCurrentMinute();
        int hour2 = timePicker2.getCurrentHour();
        int min2 = timePicker2.getCurrentMinute();
        String startingTime = hour1 + ":" + min1;
        String endingTime = hour2 + ":" + min2;
        if (title.trim().isEmpty() || description.trim().isEmpty() || location.trim().isEmpty() || startingTime.trim().isEmpty() || endingTime.trim().isEmpty()) {
            Toast.makeText(this, "Please insert complete info", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_LOCATION, location);
        data.putExtra(EXTRA_STARTTIME, startingTime);
        data.putExtra(EXTRA_ENDTIME, endingTime);


        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
            data.putExtra(EXTRA_TITLE_OLD, titleOld);
            data.putExtra(EXTRA_DESCRIPTION_OLD, descriptionOld);
            data.putExtra(EXTRA_LOCATION_OLD, locationOld);
            data.putExtra(EXTRA_STARTTIME_OLD, startingTimeOld);
            data.putExtra(EXTRA_ENDTIME_OLD, endingTimeOld);
        }

        setResult(RESULT_OK, data);
        finish();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.itinerary_add_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveRow();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}