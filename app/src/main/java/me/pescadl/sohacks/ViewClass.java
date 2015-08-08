package me.pescadl.sohacks;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ViewClass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_class);
        String id = getIntent().getStringExtra("EventId");
        ClassOpenHelper mDbHelper = new ClassOpenHelper(getBaseContext());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String sortOrder = "startTime DESC";
        Cursor c = db.query(
                "classes",
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
            String daysText = "";
            String[] daysOfWeek = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
            String dys = c.getString(c.getColumnIndexOrThrow("days"));
            int cnt = dys.replaceAll("0","").length();
            int ca = 0;
            for(int i=0;i<dys.length();i++){
                if(dys.charAt(i)=='1') {
                    daysText += daysOfWeek[i];
                    ca++;
                    if (ca < cnt) {
                        daysText += ", ";
                    }
                }
            }
            ((TextView)findViewById(R.id.daysText)).setText(daysText);
            ((TextView)findViewById(R.id.timeText)).setText(c.getString(c.getColumnIndexOrThrow("startTime")) + " - " + c.getString(c.getColumnIndexOrThrow("endTime")));
            ((TextView)findViewById(R.id.locationText)).setText(c.getString(c.getColumnIndexOrThrow("location")));
            c.moveToNext();
        }
        c.close();
        db.close();
    }
}
