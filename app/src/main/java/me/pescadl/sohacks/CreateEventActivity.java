package me.pescadl.sohacks;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class CreateEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        findViewById(R.id.startDateText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStartDate(v);
            }
        });
        findViewById(R.id.startTimeText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStartTime(v);
            }
        });
        findViewById(R.id.endTimeText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEndTime(v);
            }
        });
        findViewById(R.id.createEventButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventOpenHelper evHelper = new EventOpenHelper(getBaseContext());
                SQLiteDatabase db = evHelper.getWritableDatabase();
                ContentValues vals = new ContentValues();
                vals.put("name", ((EditText) findViewById(R.id.nameText)).getText().toString());
                vals.put("startTime", ((EditText) findViewById(R.id.startDateText)).getText().toString() + " " + ((EditText) findViewById(R.id.startTimeText)).getText().toString());
                vals.put("endTime", ((EditText) findViewById(R.id.startDateText)).getText().toString() + " " + ((EditText) findViewById(R.id.endTimeText)).getText().toString());
                vals.put("location", ((EditText) findViewById(R.id.locationText)).getText().toString());
                vals.put("description", ((EditText) findViewById(R.id.descriptionText)).getText().toString());
                long newRowId;
                newRowId = db.insert("events", "null", vals);
                db.close();
                finish();
            }
        });
    }

    public void setStartDate(View v) {
        DialogFragment newFragment = new StartDatePickerFragment();
        newFragment.show(getFragmentManager(), "startDatePicker");
    }

    public void setStartTime(View v) {
        DialogFragment newFragment = new StartTimePickerFragment();
        newFragment.show(getFragmentManager(), "startTimePicker");
    }

    public void setEndTime(View v) {
        DialogFragment newFragment = new EndTimePickerFragment();
        newFragment.show(getFragmentManager(), "endTimePicker");
    }
}
