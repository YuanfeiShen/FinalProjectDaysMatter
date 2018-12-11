package com.example.android.daysmatter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "DEBUG START");
        final Spinner startYearSpinner = findViewById(R.id.spin1);
        final Spinner startMonthSpinner = findViewById(R.id.spin2);
        final Spinner startDaySpinner = findViewById(R.id.spin3);
        final Spinner endYearSpinner = findViewById(R.id.spin4);
        final Spinner endMonthSpinner = findViewById(R.id.spin5);
        final Spinner endDaySpinner = findViewById(R.id.spin6);
        final TextView endTextView = findViewById(R.id.textView5);
        final TextView importantText = findViewById(R.id.textView9);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int StartYear = Integer.parseInt(startYearSpinner.getSelectedItem().toString());
                int StartMonth = Integer.parseInt(startMonthSpinner.getSelectedItem().toString());
                int StartDate = Integer.parseInt(startDaySpinner.getSelectedItem().toString());
                int EndYear = Integer.parseInt(endYearSpinner.getSelectedItem().toString());
                int EndMonth = Integer.parseInt(endMonthSpinner.getSelectedItem().toString());
                int EndDate = Integer.parseInt(endDaySpinner.getSelectedItem().toString());
                int days = calculator(StartYear, StartMonth, StartDate, EndYear, EndMonth, EndDate);
                double years = days / 365.0;
                double months = years * 12;
                int hours = days * 24;
                int mins = hours * 60;
                int secs = mins * 60;
                //String yearOut = Double.toString(years);
                //String monthOut = Double.toString(months);
                String dayOut = Integer.toString(days);
                //String hoursOut = Integer.toString(hours);
                //String minsOut = Integer.toString(mins);
                //String secsOut = Integer.toString(secs);


                endTextView.setText(/* yearOut + " years\n" + monthOut + " months\n" +*/ dayOut + " days\n" /*
                 + hoursOut + " hours\n" + minsOut + " minutes\n" + secsOut + " seconds\n"*/ );

                if (isLeap(StartYear)) {
                    if (StartMonth == 2 && StartDate > 29) {
                        endTextView.setText("Invalid input.\nPlease check your input and try again.");
                    }
                } else {
                    if (StartMonth == 2 && StartDate > 28) {
                        endTextView.setText("Invalid input.\nPlease check your input and try again.");
                    }
                }
                if (isLeap(EndYear)) {
                    if (EndMonth == 2 && EndDate > 29) {
                        endTextView.setText("Invalid input.\nPlease check your input and try again.");
                    }
                } else {
                    if (EndMonth == 2 && EndDate > 28) {
                        endTextView.setText("Invalid input.\nPlease check your input and try again.");
                    }
                }
                if (StartMonth == 4 || StartMonth == 6 || StartMonth == 9 || StartMonth == 11) {
                    if (StartDate == 31) {
                        endTextView.setText("Invalid input.\nPlease check your input and try again.");
                    }
                }
                if (EndMonth == 4 || EndMonth == 6 || EndMonth == 9 || EndMonth == 11) {
                    if (EndDate == 31) {
                        endTextView.setText("Invalid input.\nPlease check your input and try again.");
                    }
                }
            }
        });

        Button clear = findViewById(R.id.button1);
        clear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startYearSpinner.setSelection(0);
                startMonthSpinner.setSelection(0);
                startDaySpinner.setSelection(0);
                endYearSpinner.setSelection(0);
                endMonthSpinner.setSelection(0);
                endDaySpinner.setSelection(0);
                endTextView.setText("");
            }
        });

        final Button important = findViewById(R.id.button2);
        important.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int StartYear = Integer.parseInt(startYearSpinner.getSelectedItem().toString());
                int StartMonth = Integer.parseInt(startMonthSpinner.getSelectedItem().toString());
                int StartDate = Integer.parseInt(startDaySpinner.getSelectedItem().toString());
                String[] calendar = Calendar.getInstance().getTime().toString().split(" ");
                int calYear = Integer.parseInt(calendar[5]);
                int calMonth = 0;
                switch (calendar[1]) {
                    case "Jan": {
                        calMonth = 1;
                    }
                    case "Feb": {
                        calMonth = 2;
                    }
                    case "Mar": {
                        calMonth = 3;
                    }
                    case "Apr": {
                        calMonth = 4;
                    }
                    case "May": {
                        calMonth = 5;
                    }
                    case "Jun": {
                        calMonth = 6;
                    }
                    case "Jul": {
                        calMonth = 7;
                    }
                    case "Aug": {
                        calMonth = 8;
                    }
                    case "Sept": {
                        calMonth = 9;
                    }
                    case "Oct": {
                        calMonth = 10;
                    }
                    case "Nov": {
                        calMonth = 11;
                    }
                    case "Dec": {
                        calMonth = 12;
                    }
                }
                int calDay = Integer.parseInt(calendar[2]);
                int importantDay = calculator(StartYear, StartMonth, StartDate, calYear, calMonth, calDay);
                if (importantDay > 0) {
                    importantText.setText("Already " + Integer.toString(importantDay) + " day(s)");
                } else {
                    importantText.setText(Integer.toString(Math.abs(importantDay)) + " day(s) left");
                }
            }
        });
    }
    public int calculator(int startYear, int startMonth, int startDate,
                          int endYear, int endMonth, int endDate) {
        int[] daysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int[] daysOfMonthLeap = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int countStartDay = 0;
        int countEndDay = 0;
        for (int i = 1; i < startYear; i++) {
            if (isLeap(i)) {
                countStartDay += 366;
            } else {
                countStartDay += 365;
            }
        }
        for (int i = 0; i < startMonth - 1; i++) {
            if (isLeap(startYear)) {
                countStartDay += daysOfMonthLeap[i];
            } else {
                countStartDay += daysOfMonth[i];
            }
        }
        countStartDay += startDate;
        for (int i = 1; i < endYear; i++) {
            if (isLeap(i)) {
                countEndDay += 366;
            } else {
                countEndDay += 365;
            }
        }
        for (int i = 0; i < endMonth - 1; i++) {
            if (isLeap(endYear)) {
                countEndDay += daysOfMonthLeap[i];
            } else {
                countEndDay += daysOfMonth[i];
            }
        }
        countEndDay += endDate;
        return countEndDay - countStartDay;
    }

    public boolean isLeap(int year) {
        if (year % 4 == 0 && year % 100 != 0) {
            return true;
        } else if (year % 400 == 0) {
            return true;
        } else {
            return false;
        }
    }
}