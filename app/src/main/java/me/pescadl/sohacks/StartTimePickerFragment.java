package me.pescadl.sohacks;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by Chufan on 8/7/2015.
 */
public class StartTimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        EditText timeText = (EditText) getActivity().findViewById(R.id.startTimeText);
        String timeString = hourOfDay + ":";
        if (minute < 10) {
            timeString += 0;
        }
        timeString += minute;
        timeText.setText(timeString);
    }
}
