package me.pescadl.sohacks;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

/**
 * Created by Chufan on 8/7/2015.
 */
public class EndDatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, dayOfMonth);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        EditText dateText = (EditText) getActivity().findViewById(R.id.endDateText);
        String dateString = year + "-";
        if (monthOfYear < 10) {
            dateString += 0;
        }
        dateString += monthOfYear + "-";
        if (dayOfMonth < 10) {
            dateString += 0;
        }
        dateString += dayOfMonth;
        dateText.setText(dateString);
    }
}
