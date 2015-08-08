package me.pescadl.sohacks;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Chufan on 8/8/2015.
 */
public class SliceView extends View {
    float rot = 0;
    int y;
    int m;
    int d;

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

    public void setDate(int ye, int mo, int da) {
        y = ye;
        m = mo;
        d = da;
    }

    public void init() {
        eventColor.setColor(Color.rgb(238,238,238));
        eventColor.setStyle(Paint.Style.FILL);
        centerColor.setColor(Color.rgb(212,212,212));
        centerColor.setStyle(Paint.Style.FILL);
        indicator.setColor(android.graphics.Color.RED);
        indicator.setStyle(Paint.Style.FILL_AND_STROKE);
        Paint darkSalmon = new Paint();
        darkSalmon.setColor(Color.rgb(241, 156, 125));
        palette.put("Dark Salmon", darkSalmon);
        Paint chardonnay = new Paint();
        chardonnay.setColor(Color.rgb(249, 204, 142));
        palette.put("Chardonnay", chardonnay);
        Paint deco = new Paint();
        deco.setColor(Color.rgb(213, 227, 146));
        palette.put("Deco", deco);
        Paint babyBlue = new Paint();
        babyBlue.setColor(Color.rgb(139, 211, 244));
        palette.put("Baby Blue", babyBlue);
        Paint seaPink = new Paint();
        seaPink.setColor(Color.rgb(236, 160, 162));
        palette.put("Sea Pink", seaPink);
        Paint tacao = new Paint();
        tacao.setColor(Color.rgb(244, 180, 135));
        palette.put("Tacao", tacao);
        Paint picasso = new Paint();
        picasso.setColor(Color.rgb(255, 245, 158));
        palette.put("Picasso", picasso);
        Paint etonBlue = new Paint();
        etonBlue.setColor(Color.rgb(149, 203, 156));
        palette.put("Eton Blue", etonBlue);
        Paint carolinaBlue = new Paint();
        carolinaBlue.setColor(Color.rgb(145, 175, 219));
        palette.put("Carolina Blue", carolinaBlue);
        Paint pastelViolet = new Paint();
        pastelViolet.setColor(Color.rgb(201, 152, 192));
        palette.put("Pastel Violet", pastelViolet);
        Paint butteryWhite = new Paint();
        butteryWhite.setColor(Color.rgb(243, 236, 220));
        palette.put("Buttery White", butteryWhite);
    }

    ;

    RectF outerBounds = null;

    Paint eventColor = new Paint();
    Paint indicator = new Paint();
    Paint centerColor = new Paint();

    HashMap<String, Paint> palette = new HashMap<>();

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
        if (outerBounds == null) {
            outerBounds = new RectF(canvas.getWidth() / 2 - 850, canvas.getHeight() - 850, canvas.getWidth() / 2 + 850, canvas.getHeight() + 850);
        }
        while (!c.isAfterLast()) {
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
            if (begincal.get(Calendar.YEAR) == y && begincal.get(Calendar.MONTH) == (m - 1) && begincal.get(Calendar.DAY_OF_MONTH) == d) {
                float startDecimal = begincal.get(Calendar.HOUR_OF_DAY) + begincal.get(Calendar.MINUTE) / 60.0f;
                float endDecimal = endcal.get(Calendar.HOUR_OF_DAY) + endcal.get(Calendar.MINUTE) / 60.0f;
                float startdeg = startDecimal / 24.0f * 360 + 180;
                float enddeg = (endDecimal - startDecimal) / 24 * 360;
                canvas.drawArc(outerBounds, startdeg - rot, enddeg, true, eventColor);
            }
            c.moveToNext();
        }
        c.close();
        db.close();
        ClassOpenHelper mCDbHelper = new ClassOpenHelper(getContext());
        SQLiteDatabase cdb = mCDbHelper.getReadableDatabase();
        String[] cprojection = {
                "days", "startTime", "endTime", "color"
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
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar endcal = Calendar.getInstance();
            try {
                endcal.setTime(format.parse(endTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar today = Calendar.getInstance();
            today.set(y, m, d);
            if (days.charAt(today.get(Calendar.DAY_OF_WEEK) - 1) == '1') {
                float startDecimal = begincal.get(Calendar.HOUR_OF_DAY) + begincal.get(Calendar.MINUTE) / 60.0f;
                float endDecimal = endcal.get(Calendar.HOUR_OF_DAY) + endcal.get(Calendar.MINUTE) / 60.0f;
                float startdeg = startDecimal / 24.0f * 360 + 180;
                float enddeg = (endDecimal - startDecimal) / 24 * 360;

                canvas.drawArc(outerBounds, startdeg - rot, enddeg, true, palette.get(color));
            }
            c.moveToNext();
        }
//        canvas.drawCircle(canvas.getWidth() / 2,canvas.getHeight(),530,centerColor);
        c.close();
        cdb.close();
        canvas.drawRect(outerBounds.centerX() - 5f, outerBounds.bottom - 1370, outerBounds.centerX() + 5f, outerBounds.bottom - 1250, indicator);
//        canvas.drawLine(outerBounds.centerX(), outerBounds.bottom - 1500, outerBounds.centerX(), outerBounds.bottom, indicator);
    }
}
