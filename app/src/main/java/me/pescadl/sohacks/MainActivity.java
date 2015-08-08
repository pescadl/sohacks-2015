package me.pescadl.sohacks;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onResume(){
        super.onResume();

        EventOpenHelper evHelper = new EventOpenHelper(getBaseContext());
        SQLiteDatabase db = evHelper.getReadableDatabase();
        String[] projection = {
                "name",
                "startTime",
                "endTime",
                "location",
                "description"
        };
        String sortOrder = "name DESC";
        Cursor c = db.query("events", projection, null, null, null, null, sortOrder);
        c.moveToFirst();
        TableLayout tab = (TableLayout) findViewById(R.id.eventTable);
        tab.removeAllViews();
        while(!c.isAfterLast()) {
            TableRow tr = new TableRow(this);
            TextView name = new TextView(this);
            name.setText(c.getString(c.getColumnIndexOrThrow("name")));
            TextView startTime = new TextView(this);
            startTime.setText(c.getString(c.getColumnIndexOrThrow("startTime")));
            TextView endTime = new TextView(this);
            endTime.setText(c.getString(c.getColumnIndexOrThrow("endTime")));
            TextView location = new TextView(this);
            location.setText(c.getString(c.getColumnIndexOrThrow("location")));
            TextView description = new TextView(this);
            description.setText(c.getString(c.getColumnIndexOrThrow("description")));
            tr.addView(name);
            tr.addView(startTime);
            tr.addView(endTime);
            tr.addView(location);
            tr.addView(description);
            tab.addView(tr, new TableLayout.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            c.moveToNext();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void createEvent(View v) {
        Intent intent = new Intent(this, CreateEventActivity.class);
        startActivity(intent);
    }
}
