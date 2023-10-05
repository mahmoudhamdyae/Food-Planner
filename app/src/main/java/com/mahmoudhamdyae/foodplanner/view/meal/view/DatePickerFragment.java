package com.mahmoudhamdyae.foodplanner.view.meal.view;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private Date mStartTime, mEndTime;
    private final String mealName;

    public DatePickerFragment(String mealName) {
        this.mealName = mealName;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker.
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it.
        return new DatePickerDialog(requireContext(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        String startTime = year + "-" + (month + 1) + "-" + day + "T09:00:00";
        String endTime = year + "-" + (month + 1) + "-" + day + "T17:00:00";

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        try {
            mStartTime = mSimpleDateFormat.parse(startTime);
            mEndTime = mSimpleDateFormat.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Intent mIntent = new Intent(Intent.ACTION_EDIT);
        mIntent.setType("vnd.android.cursor.item/event");
        mIntent.putExtra("time", true);
        mIntent.putExtra("beginTime", mStartTime.getTime());
        mIntent.putExtra("endTime", mEndTime.getTime());
        mIntent.putExtra("title", mealName);
        startActivity(mIntent);
    }
}
