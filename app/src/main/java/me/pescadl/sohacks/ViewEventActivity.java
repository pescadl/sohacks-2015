package me.pescadl.sohacks;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ViewEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        String id = getIntent().getStringExtra("EventId");
        EventOpenHelper mDbHelper = new EventOpenHelper(getBaseContext());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String sortOrder = "startTime DESC";
        Cursor c = db.query(
                "events",
                null,
                "_id=" + id,
                null,
                null,
                null,
                sortOrder
        );
        c.moveToFirst();
        while (!c.isAfterLast()) {
            ((TextView)findViewById(R.id.nameText)).setText(c.getString(c.getColumnIndexOrThrow("name")));
            ((TextView)findViewById(R.id.dateText)).setText(c.getString(c.getColumnIndexOrThrow("startTime")).split("\\s+")[0]);
            ((TextView)findViewById(R.id.timeText)).setText(c.getString(c.getColumnIndexOrThrow("startTime")).split("\\s+")[1] + " - " + c.getString(c.getColumnIndexOrThrow("endTime")).split("\\s+")[1]);
            ((TextView)findViewById(R.id.locationText)).setText(c.getString(c.getColumnIndexOrThrow("location")));
            ((TextView)findViewById(R.id.descriptionText)).setText(c.getString(c.getColumnIndexOrThrow("description")));
            c.moveToNext();
        }
        c.close();
        db.close();
    }

}
