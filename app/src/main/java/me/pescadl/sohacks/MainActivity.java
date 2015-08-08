package me.pescadl.sohacks;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener{
    float currdegree;
    int currYear;
    int currMonth;
    int currDay;

    private GestureDetectorCompat mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.dateView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new StartDatePickerFragment();
                newFragment.show(getFragmentManager(), "StartDatePicker");
            }
        });
        ((EditText)findViewById(R.id.startDateText)).addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String[] things = s.toString().split("-");
                currYear = Integer.parseInt(things[0]);
                currMonth = Integer.parseInt(things[1]);
                currDay = Integer.parseInt(things[2]);

                Button dateView = (Button) findViewById(R.id.dateView);
                String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
                dateView.setText(months[currMonth] + " " + currDay + ", " + currYear);
                SliceView slices = (SliceView) findViewById(R.id.sliceView);
                slices.setDate(currYear,currMonth,currDay);
                slices.setRot(currdegree);
                slices.invalidate();

            }
        });
        mDetector = new GestureDetectorCompat(this,this);
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences settings = getSharedPreferences("CentricPrefs", 0);
        int theme = settings.getInt("theme", 0);

        ImageView wheelView = (ImageView) findViewById(R.id.wheelImageView);
        switch(theme){
            case 0://default
                wheelView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.circle, null));
                break;
            case 1://space
                wheelView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.space, null));
                break;
            case 2://pizza
                wheelView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.pizza, null));
                break;
            default:
                wheelView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.circle, null));
                break;

        }

        Calendar c = Calendar.getInstance();
        currYear = c.get(Calendar.YEAR);
        currMonth = c.get(Calendar.MONTH);
        currDay = c.get(Calendar.DAY_OF_MONTH);

        Button dateView = (Button) findViewById(R.id.dateView);
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        dateView.setText(months[currMonth] + " " + currDay + ", " + currYear);

        double decimalHours = c.get(Calendar.HOUR_OF_DAY) + c.get(Calendar.MINUTE) / 60.0;
        float degrees = (int) ((decimalHours + 18) % 24 / 24.0 * 360.0);
        currdegree = degrees;
        wheelView.setRotation(-currdegree);

        SliceView slices = (SliceView) findViewById(R.id.sliceView);
        slices.setDate(currYear,currMonth,currDay);
        slices.setRot(currdegree);
        slices.invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }



    public void createEvent(View v) {
        Intent intent = new Intent(this, CreateActivity.class);
        startActivity(intent);
    }

    public void viewTodo(View v) {
        Intent intent = new Intent(this, ViewTodo.class);
        startActivity(intent);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        if(flingAnimator != null){
            flingAnimator.cancel();
        }
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        float hour = (currdegree+ 90)/360 * 24;


        EventOpenHelper mDbHelper = new EventOpenHelper(getBaseContext());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {
                "_id","startTime", "endTime"
        };
        String sortOrder = "startTime DESC";
        Cursor c = db.query(
                "events",
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
        c.moveToFirst();
        while (!c.isAfterLast()) {
            // -180 = 12 midnight
            String beginTime = c.getString(c.getColumnIndexOrThrow("startTime"));
            String endTime = c.getString(c.getColumnIndexOrThrow("endTime"));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Calendar begincal = Calendar.getInstance();
            try {
                begincal.setTime(format.parse(beginTime));
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            Calendar endcal = Calendar.getInstance();
            try {
                endcal.setTime(format.parse(endTime));
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            if (begincal.get(Calendar.YEAR) == currYear && begincal.get(Calendar.MONTH) == (currMonth - 1) && begincal.get(Calendar.DAY_OF_MONTH) == currDay) {
                float startDecimal = begincal.get(Calendar.HOUR_OF_DAY) + begincal.get(Calendar.MINUTE) / 60.0f;
                float endDecimal = endcal.get(Calendar.HOUR_OF_DAY) + endcal.get(Calendar.MINUTE) / 60.0f;
                if(startDecimal < hour && hour < endDecimal){

                    Intent intent = new Intent(getBaseContext(), ViewEventActivity.class);
                    intent.putExtra("EventId", c.getString(c.getColumnIndexOrThrow("_id")));
                    startActivity(intent);
                }
            }
            c.moveToNext();
        }
        c.close();
        db.close();
        ClassOpenHelper mCDbHelper = new ClassOpenHelper(getBaseContext());
        SQLiteDatabase cdb = mCDbHelper.getReadableDatabase();
        String[] cprojection = {
                "_id", "days", "startTime", "endTime", "color"
        };
        c = cdb.query(
                "classes",
                cprojection,
                null,
                null,
                null,
                null,
                sortOrder
        );
        c.moveToFirst();

        while (!c.isAfterLast()) {
            // -180 = 12 midnight
            String beginTime = c.getString(c.getColumnIndexOrThrow("startTime"));
            String endTime = c.getString(c.getColumnIndexOrThrow("endTime"));
            String days = c.getString(c.getColumnIndexOrThrow("days"));
            String color = c.getString(c.getColumnIndexOrThrow("color"));
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            Calendar begincal = Calendar.getInstance();
            try {
                begincal.setTime(format.parse(beginTime));
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            Calendar endcal = Calendar.getInstance();
            try {
                endcal.setTime(format.parse(endTime));
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            Calendar today = Calendar.getInstance();
            today.set(currYear, currMonth, currDay);
            if (days.charAt(today.get(Calendar.DAY_OF_WEEK) - 1) == '1') {
                float startDecimal = begincal.get(Calendar.HOUR_OF_DAY) + begincal.get(Calendar.MINUTE) / 60.0f;
                float endDecimal = endcal.get(Calendar.HOUR_OF_DAY) + endcal.get(Calendar.MINUTE) / 60.0f;
                if(startDecimal < hour && hour < endDecimal){
                    Intent intent = new Intent(getBaseContext(), ViewClass.class);
                    intent.putExtra("EventId", c.getString(c.getColumnIndexOrThrow("_id")));
                    startActivity(intent);
                }
            }
            c.moveToNext();
        }

        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        ImageView wheelView = (ImageView) findViewById(R.id.wheelImageView);
        currdegree += (distanceX)/9;
        wheelView.setRotation(-currdegree);
        SliceView slices = (SliceView) findViewById(R.id.sliceView);
        slices.setDate(currYear,currMonth,currDay);
        slices.setRot(currdegree);
        slices.invalidate();
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }
    ValueAnimator flingAnimator;
    float primaryDegree;
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        primaryDegree = currdegree;
        ImageView wheelView = (ImageView) findViewById(R.id.wheelImageView);
        flingAnimator = ValueAnimator.ofFloat(currdegree, currdegree-velocityX/20);
        flingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ImageView wheelView = (ImageView) findViewById(R.id.wheelImageView);
                currdegree = (float)animation.getAnimatedValue();
                wheelView.setRotation(-currdegree);
                SliceView slices = (SliceView) findViewById(R.id.sliceView);
                slices.setDate(currYear,currMonth,currDay);
                slices.setRot(currdegree);
                slices.invalidate();
            }
        });
        currdegree %= 360;
        flingAnimator.setDuration(500);
        flingAnimator.setInterpolator(new DecelerateInterpolator());
        flingAnimator.start();
        return false;
    }
}
