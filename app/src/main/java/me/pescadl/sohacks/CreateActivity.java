package me.pescadl.sohacks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        ListView lv = (ListView) findViewById(R.id.listView);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.create_array , android.R.layout.simple_list_item_1);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    Intent intent = new Intent(getBaseContext(), CreateEventActivity.class);
                    startActivity(intent);
                    finish();
                }
                if(position==1){
                    Intent intent = new Intent(getBaseContext(), CreateClassActivity.class);
                    startActivity(intent);
                    finish();
                }
                if(position==2){
                    Intent intent = new Intent(getBaseContext(), CreateTodoActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

}
