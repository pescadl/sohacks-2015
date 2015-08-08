package me.pescadl.sohacks;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ThemePicker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_picker);
        ListView lv = (ListView) findViewById(R.id.listView);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.theme_array , android.R.layout.simple_list_item_1);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    SharedPreferences settings = getSharedPreferences("CentricPrefs", 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putInt("theme", 0);
                    editor.commit();
                    finish();
                }
                if (position == 1) {
                    SharedPreferences settings = getSharedPreferences("CentricPrefs", 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putInt("theme", 1);
                    editor.commit();
                    finish();
                }
                if (position == 2) {
                    SharedPreferences settings = getSharedPreferences("CentricPrefs", 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putInt("theme", 2);
                    editor.commit();
                    finish();
                }
            }
        });
    }

}
