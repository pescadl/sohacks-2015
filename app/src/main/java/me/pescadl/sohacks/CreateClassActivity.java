package me.pescadl.sohacks;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class CreateClassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class);

        Spinner spinner = (Spinner) findViewById(R.id.color_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.colors_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

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
                ClassOpenHelper evHelper = new ClassOpenHelper(getBaseContext());
                SQLiteDatabase db = evHelper.getWritableDatabase();
                ContentValues vals = new ContentValues();
                vals.put("name", ((EditText) findViewById(R.id.nameText)).getText().toString());
                String days = "";
                if (((CheckBox) findViewById(R.id.sund)).isChecked()) {
                    days += 1;
                } else {
                    days += 0;
                }
                if (((CheckBox) findViewById(R.id.mon)).isChecked()) {
                    days += 1;
                } else {
                    days += 0;
                }
                if (((CheckBox) findViewById(R.id.tue)).isChecked()) {
                    days += 1;
                } else {
                    days += 0;
                }
                if (((CheckBox) findViewById(R.id.wed)).isChecked()) {
                    days += 1;
                } else {
                    days += 0;
                }
                if (((CheckBox) findViewById(R.id.thu)).isChecked()) {
                    days += 1;
                } else {
                    days += 0;
                }
                if (((CheckBox) findViewById(R.id.fri)).isChecked()) {
                    days += 1;
                } else {
                    days += 0;
                }
                if (((CheckBox) findViewById(R.id.sat)).isChecked()) {
                    days += 1;
                } else {
                    days += 0;
                }
                vals.put("days", days);
                vals.put("startTime", ((EditText) findViewById(R.id.startTimeText)).getText().toString());
                vals.put("endTime", ((EditText) findViewById(R.id.endTimeText)).getText().toString());
                vals.put("location", ((EditText) findViewById(R.id.locationText)).getText().toString());
                vals.put("color", ((Spinner) findViewById(R.id.color_spinner)).getSelectedItem().toString());
                long newRowId;
                newRowId = db.insert("classes", "null", vals);
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
