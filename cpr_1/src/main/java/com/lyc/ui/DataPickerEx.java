package com.lyc.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DataPickerEx {

    public String selected_date;
    private Activity ac;
    OnDateSetListener datelistener;

    public DataPickerEx(Activity ac) {
        this.ac = ac;
        this.datelistener = new OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                selected_date = year + "-" + String.format("%02", monthOfYear) + "-" + String.format("%02", dayOfMonth);
            }
        };
    }

    public DataPickerEx(Activity ac, OnDateSetListener datelistener) {
        this.ac = ac;
        this.datelistener = datelistener;
    }

    public void show() {
        Calendar mycalendar = Calendar.getInstance(Locale.CHINA);
        Date mydate = new Date();
        mycalendar.setTime(mydate);

        DatePickerDialog dpd = new DatePickerDialog(ac, datelistener, mycalendar.get(Calendar.YEAR), mycalendar.get(Calendar.MONTH), mycalendar.get(Calendar.DAY_OF_MONTH));

        dpd.getDatePicker().setCalendarViewShown(false);
        dpd.show();
    }
}
