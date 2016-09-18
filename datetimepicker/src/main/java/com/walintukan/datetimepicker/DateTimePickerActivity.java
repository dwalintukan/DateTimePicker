package com.walintukan.datetimepicker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Activity with no UI to launch DatePickerDialog and then TimePickerDialog after.
 * Use DateTimePickerActivity.REQUEST_CODE when using startActivityForResult.
 * Get the Calendar object in onActivityResult by calling getSelectedDateTime(Intent data).
 */
public class DateTimePickerActivity extends Activity implements
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    public static final int REQUEST_CODE = 2000;

    private static final String KEY_SELECTED_DATE_TIME = "selected_date_time";
    private static final String KEY_DATE_SET = "date_set";
    private static final String KEY_THEME_RES_ID = "theme_res_id";

    private Calendar mSelectedDateTime;
    private boolean mDateSet;
    private int mDialogThemeResId = android.R.style.Theme_DeviceDefault_Light_Dialog;

    private DatePickerDialog mDatePickerDialog;
    private TimePickerDialog mTimePickerDialog;

    public static Intent createIntent(Context context, Date date) {
        final Intent intent = new Intent(context, DateTimePickerActivity.class);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        intent.putExtra(KEY_SELECTED_DATE_TIME, calendar);
        return intent;
    }

    public static Intent createIntent(Context context, Date date, int dialogThemeResId) {
        final Intent intent = new Intent(context, DateTimePickerActivity.class);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        intent.putExtra(KEY_SELECTED_DATE_TIME, calendar);
        intent.putExtra(KEY_THEME_RES_ID, dialogThemeResId);
        return intent;
    }

    public static Intent createIntent(Context context, Calendar calendar) {
        final Intent intent = new Intent(context, DateTimePickerActivity.class);
        intent.putExtra(KEY_SELECTED_DATE_TIME, calendar);
        return intent;
    }

    public static Intent createIntent(Context context, Calendar calendar, int dialogThemeResId) {
        final Intent intent = new Intent(context, DateTimePickerActivity.class);
        intent.putExtra(KEY_SELECTED_DATE_TIME, calendar);
        intent.putExtra(KEY_THEME_RES_ID, dialogThemeResId);
        return intent;
    }

    public static Calendar getSelectedDateTime(Intent data) {
        if (data != null) {
            return (Calendar) data.getSerializableExtra(KEY_SELECTED_DATE_TIME);
        } else {
            return null;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            final Intent intent = getIntent();
            mSelectedDateTime = (Calendar) intent.getSerializableExtra(KEY_SELECTED_DATE_TIME);
            if (intent.hasExtra(KEY_THEME_RES_ID)) {
                mDialogThemeResId = intent.getIntExtra(KEY_THEME_RES_ID,
                        android.R.style.Theme_DeviceDefault_Light_Dialog);
            }
        } else {
            mSelectedDateTime = (Calendar) savedInstanceState.getSerializable(KEY_SELECTED_DATE_TIME);
            mDateSet = savedInstanceState.getBoolean(KEY_DATE_SET);
            mDialogThemeResId = savedInstanceState.getInt(KEY_THEME_RES_ID);
        }

        if (mSelectedDateTime == null) {
            mSelectedDateTime = Calendar.getInstance();
        }

        if (!mDateSet) {
            showDatePicker();
        } else {
            showTimePicker();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mSelectedDateTime.set(Calendar.YEAR, year);
        mSelectedDateTime.set(Calendar.MONTH, month);
        mSelectedDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        mDateSet = true;
        showTimePicker();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        mSelectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        mSelectedDateTime.set(Calendar.MINUTE, minute);

        final Intent intent = new Intent();
        intent.putExtra(KEY_SELECTED_DATE_TIME, mSelectedDateTime);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        if (mDatePickerDialog != null && mDatePickerDialog.isShowing()) {
            mDatePickerDialog.dismiss();
        }
        if (mTimePickerDialog != null && mTimePickerDialog.isShowing()) {
            mTimePickerDialog.dismiss();
        }
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(KEY_SELECTED_DATE_TIME, mSelectedDateTime);
        outState.putBoolean(KEY_DATE_SET, mDateSet);
        outState.putInt(KEY_THEME_RES_ID, mDialogThemeResId);
        super.onSaveInstanceState(outState);
    }

    private void showDatePicker() {
        final int year = mSelectedDateTime.get(Calendar.YEAR);
        final int month = mSelectedDateTime.get(Calendar.MONTH);
        final int day = mSelectedDateTime.get(Calendar.DAY_OF_MONTH);

        mDatePickerDialog = new DatePickerDialog(this, mDialogThemeResId, this,
                year, month, day);
        mDatePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
        mDatePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
        mDatePickerDialog.show();
    }

    private void showTimePicker() {
        final int hour = mSelectedDateTime.get(Calendar.HOUR);
        final int minutes = mSelectedDateTime.get(Calendar.MINUTE);

        mTimePickerDialog = new TimePickerDialog(this, mDialogThemeResId, this,
                hour, minutes, false);
        mTimePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
        mTimePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
        mTimePickerDialog.show();
    }
}

