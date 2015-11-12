package com.example.will.slugsports;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.Toast;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;


public class CalendarViewActivity extends FragmentActivity
        implements TimePickerFragment.OnTimeSelectedListener{

    CalendarView calendar;

    int dayOfEvent;
    int monthOfEvent;
    int yearOfEvent;

    Long date;

    int timesCalled = 0;

    public void showTimePickerDialog() {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_calendar);

        initializeCalendar();
    }

    public void initializeCalendar(){

        calendar = (CalendarView) findViewById(R.id.calendarView);

        // sets whether to show the week number.
        calendar.setShowWeekNumber(false);

        date = calendar.getDate();


        calendar.setOnDateChangeListener(new OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {

                if(calendar.getDate() != date){
                    date = calendar.getDate();

                    dayOfEvent = day;
                    monthOfEvent = month;
                    yearOfEvent = year;


                    //Toast.makeText(getApplicationContext(), day + "/" + month + "/" + year, Toast.LENGTH_LONG).show();

                    //showTimePickerDialog();


                }
                Intent intent = new Intent(CalendarViewActivity.this, FoundGames.class);
                intent.putExtra("day", dayOfEvent);
                intent.putExtra("month", monthOfEvent);
                intent.putExtra("year", yearOfEvent);
                startActivity(intent);
            }
        });
    }

    public void onTimeSelected(int hour, int minute){
        Intent intent = new Intent(CalendarViewActivity.this, createEvent.class);
        intent.putExtra("day", dayOfEvent);
        intent.putExtra("month", monthOfEvent);
        intent.putExtra("year", yearOfEvent);
        intent.putExtra("minute", minute);

        if(hour > 12){
            intent.putExtra("hour", hour % 12);
            intent.putExtra("AM?", false);
        }
        else {
            intent.putExtra("hour", hour);
            intent.putExtra("AM?", true);
        }

        Log.i("DEBUG", "About to create an event");

        if(timesCalled % 2 == 0)
            startActivity(intent);

        timesCalled++;
    }






}


