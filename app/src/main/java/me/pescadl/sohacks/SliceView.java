package me.pescadl.sohacks;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Chufan on 8/8/2015.
 */
public class SliceView extends View {
    float rot = 0;

    public SliceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SliceView(Context context) {
        super(context);
        init();
    }

    public SliceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void setRot(float rotat) {
        rot = rotat;
    }

    public void init() {

        eventColor.setColor(android.graphics.Color.LTGRAY);
        eventColor.setStyle(Paint.Style.FILL);
        red.setColor(android.graphics.Color.RED);
        red.setStyle(Paint.Style.STROKE);
    }

    RectF bounds = null;

    Paint eventColor = new Paint();
    Paint red = new Paint();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        EventOpenHelper mDbHelper = new EventOpenHelper(getContext());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {
            "startTime", "endTime"
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
        if (bounds == null) {
            bounds = new RectF(canvas.getWidth() / 2 - 800, canvas.getHeight() - 800, canvas.getWidth()/2 +800, canvas.getHeight() + 800);
        }
        while(!c.isAfterLast()) {
            // -180 = 12 midnight
            String beginTime = c.getString(c.getColumnIndexOrThrow("startTime"));
            String endTime = c.getString(c.getColumnIndexOrThrow("endTime"));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Calendar begincal = Calendar.getInstance();
            try {
                begincal.setTime(format.parse(beginTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar endcal = Calendar.getInstance();
            try {
                endcal.setTime(format.parse(endTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            float startDecimal = begincal.get(Calendar.HOUR_OF_DAY) + begincal.get(Calendar.MINUTE)/60.0f;
            float endDecimal = endcal.get(Calendar.HOUR_OF_DAY) + endcal.get(Calendar.MINUTE)/60.0f;
            float startdeg = startDecimal / 24.0f * 360 + 180;
            float enddeg = (endDecimal - startDecimal) / 24  * 360;
            canvas.drawArc(bounds, startdeg - rot, enddeg, true, eventColor);
            c.moveToNext();
        }
        canvas.drawLine(bounds.centerX(),bounds.bottom - 1500,bounds.centerX(),bounds.bottom, red);
    }
}
