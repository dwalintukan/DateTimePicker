package com.walintukan.datetimepickerdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.walintukan.datetimepicker.DateTimePickerActivity;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_CALENDAR = "calendar";

    private Calendar mCalendar;

    private TextView mSelectedDateTimeTextView;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DateTimePickerActivity.REQUEST_CODE && resultCode == RESULT_OK) {
            mCalendar = DateTimePickerActivity.getSelectedDateTime(data);
            setDateTimeText();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSelectedDateTimeTextView = (TextView) findViewById(R.id.textview_selected_date_time);

        final Button selectDateTimeButton = (Button) findViewById(R.id.button_select_date_time);
        selectDateTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchDateTimePicker();
            }
        });

        if (savedInstanceState != null) {
            mCalendar = (Calendar) savedInstanceState.getSerializable(KEY_CALENDAR);
            setDateTimeText();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(KEY_CALENDAR, mCalendar);
        super.onSaveInstanceState(outState);
    }

    private void launchDateTimePicker() {
        if (mCalendar == null) {
            mCalendar = Calendar.getInstance();
        }

        final Intent intent = DateTimePickerActivity.createIntent(this, mCalendar);
        startActivityForResult(intent, DateTimePickerActivity.REQUEST_CODE);
    }

    private void setDateTimeText() {
        if (mCalendar != null) {
            final DateFormat dateFormat =
                    DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT);
            dateFormat.setTimeZone(TimeZone.getDefault());

            final String formattedDateTime = dateFormat.format(mCalendar.getTime());
            mSelectedDateTimeTextView.setText(formattedDateTime);
        }
    }
}
