package me.pescadl.sohacks;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class CreateTodoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_todo);

        findViewById(R.id.createTodoButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TodoOpenHelper evHelper = new TodoOpenHelper(getBaseContext());
                SQLiteDatabase db = evHelper.getWritableDatabase();
                ContentValues vals = new ContentValues();
                vals.put("description", ((EditText) findViewById(R.id.descriptionText)).getText().toString());
                long newRowId;
                newRowId = db.insert("todos", "null", vals);
                db.close();
                finish();
            }
        });
    }
}
