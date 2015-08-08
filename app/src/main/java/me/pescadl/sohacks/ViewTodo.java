package me.pescadl.sohacks;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ViewTodo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_todo);

        final ArrayList<String> listItems = new ArrayList<String>();
        ArrayAdapter<String> adapter;

        TodoOpenHelper mDbHelper = new TodoOpenHelper(getBaseContext());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String sortOrder = "_id DESC";
        Cursor c = db.query(
                "todos",
                null,
                null,
                null,
                null,
                null,
                sortOrder
        );

        c.moveToFirst();
        while (!c.isAfterLast()) {
            listItems.add(c.getString(c.getColumnIndexOrThrow("description")));
            c.moveToNext();
        }


        c.close();
        db.close();


        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        ((ListView) findViewById(R.id.listView2)).setAdapter(adapter);

        ((ListView) findViewById(R.id.listView2)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TodoOpenHelper mmDbHelper = new TodoOpenHelper(getBaseContext());
                SQLiteDatabase ddb = mmDbHelper.getReadableDatabase();

                String selection = "description LIKE ?";
                String[] selectionArgs = {listItems.get((int) id)};
                ddb.delete("todos", selection, selectionArgs);

                listItems.remove((int) id);

                ddb.close();
                Intent intent = new Intent(getBaseContext(), ViewTodo.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_todo, menu);
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
}
