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

public class Itinerary_add_day extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.example.travelog.ui.Trips.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "com.example.travelog.ui.Trips.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.example.travelog.ui.Trips.EXTRA_DESCRIPTION";
//    public static final String EXTRA_DAYNUM =
//            "com.example.travelog.ui.Trips.EXTRA_DAYNUM";
//    public static final String EXTRA_TITLE_OLD =
//            "com.example.travelog.ui.Trips.EXTRA_TITLE_OLD";
//    public static final String EXTRA_DESCRIPTION_OLD =
//            "com.example.travelog.ui.Trips.EXTRA_DESCRIPTION_OLD";


    private EditText editTextRowTitle;
    private EditText editTextDescription;
    private String titleOld, descriptionOld;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dialog_day);

        editTextRowTitle = findViewById(R.id.dayTitle_edit);
        editTextDescription = findViewById(R.id.description_edit);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Row");
            editTextRowTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));

//            titleOld = intent.getStringExtra(EXTRA_TITLE);
//            descriptionOld = intent.getStringExtra(EXTRA_DESCRIPTION);

        } else {
            setTitle("Add Row");
        }

    }
    private void saveRow() {

        String title = editTextRowTitle.getText().toString();
        String description = editTextDescription.getText().toString();


        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please insert complete info", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
//            data.putExtra(EXTRA_TITLE_OLD, titleOld);
//            data.putExtra(EXTRA_DESCRIPTION_OLD, descriptionOld);

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