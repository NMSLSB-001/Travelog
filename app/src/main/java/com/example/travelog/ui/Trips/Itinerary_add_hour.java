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
import com.example.travelog.ui.Profile.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Itinerary_add_hour extends AppCompatActivity {
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

    public static final String EXTRA_TITLE_OLD =
            "com.example.travelog.ui.Trips.EXTRA_TITLE_OLD";
    public static final String EXTRA_DESCRIPTION_OLD =
            "com.example.travelog.ui.Trips.EXTRA_DESCRIPTION_OLD";
    public static final String EXTRA_LOCATION_OLD =
            "com.example.travelog.ui.Trips.EXTRA_LOCATION_OLD";
    public static final String EXTRA_STARTTIME_OLD =
            "com.example.travelog.ui.Trips.EXTRA_STARTTIME_OLD";


    private EditText editTextRowTitle;
    private EditText editTextDescription;
    private EditText editTextLocation;
    private TextView startTime;
    private TimePicker timePicker1;
    private String titleOld, descriptionOld, locationOld, startingTimeOld, endingTimeOld, Username;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dialog_hour);

        editTextRowTitle = findViewById(R.id.rowTitle_edit);
        editTextDescription = findViewById(R.id.description_edit);
        editTextLocation = findViewById(R.id.location_edit);
        startTime = findViewById(R.id.startTime);
        timePicker1 = findViewById(R.id.time_picker1);

        Username = User.getName();

        Intent intent = getIntent();


        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Row");
            editTextRowTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            editTextLocation.setText(intent.getStringExtra(EXTRA_LOCATION));
            startTime.setText(intent.getStringExtra(EXTRA_STARTTIME));

            titleOld = intent.getStringExtra(EXTRA_TITLE);
            descriptionOld = intent.getStringExtra(EXTRA_DESCRIPTION);
            locationOld = intent.getStringExtra(EXTRA_LOCATION);
            startingTimeOld = intent.getStringExtra(EXTRA_STARTTIME);

        } else {
            setTitle("Add Row");
        }

    }

    //here need add data to database
    private void saveRow() {

        String title = editTextRowTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        String location = editTextLocation.getText().toString();
        int hour1 = timePicker1.getCurrentHour();
        int min1 = timePicker1.getCurrentMinute();
        String startingTime = hour1 + ":" + min1;
        String timeTitle = hour1 + "" + min1;
        if (title.trim().isEmpty() || description.trim().isEmpty() || location.trim().isEmpty() || startingTime.trim().isEmpty()) {
            Toast.makeText(this, "Please insert complete info", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();


        //update data to database
        Intent intent = getIntent();
        String dayTitle = intent.getStringExtra("dayTitle");
        String itineraryID = intent.getStringExtra("itineraryID");

        firebase_addHour firebase_addHour = new firebase_addHour(title, location, description, startingTime);
        ref = FirebaseDatabase.getInstance().getReference().child("itineraryDetails").child(Username).child(itineraryID).child(dayTitle);
        ref.child(timeTitle).setValue(firebase_addHour);


        //this tell previous activity either edit or add new
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