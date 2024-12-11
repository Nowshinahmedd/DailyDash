package com.example.project3regex;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class Calender extends AppCompatActivity {

    private TextView monthYearText;
    private GridLayout calendarGrid;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender); // Make sure this matches your XML layout file name

        // Initialize views
        monthYearText = findViewById(R.id.monthYearText);
        calendarGrid = findViewById(R.id.calendarGrid);
        Button previousMonthButton = findViewById(R.id.previousMonthButton);
        Button nextMonthButton = findViewById(R.id.nextMonthButton);

        // Initialize Calendar instance to current date
        calendar = Calendar.getInstance();

        // Populate the calendar for the current month
        populateCalendar();

        // Set up button listeners for previous and next month navigation
        previousMonthButton.setOnClickListener(v -> moveToPreviousMonth());

        nextMonthButton.setOnClickListener(v -> moveToNextMonth());
    }

    // Method to populate the calendar grid
    @SuppressLint("SetTextI18n")
    private void populateCalendar() {
        // Get current month and year
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentYear = calendar.get(Calendar.YEAR);

        // Update the TextView with the current month and year
        monthYearText.setText(getMonthName(currentMonth) + " " + currentYear);

        // Get the maximum number of days in the current month
        int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        // Get the first day of the week for the current month (1 = Sunday, 7 = Saturday)
        int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        // Clear the existing calendar grid
        calendarGrid.removeAllViews();

        // Add empty views for the days before the 1st of the month (if necessary)
        for (int i = 1; i < firstDayOfWeek; i++) {
            Button emptyButton = new Button(this);
            emptyButton.setVisibility(View.INVISIBLE); // Hide empty buttons
            calendarGrid.addView(emptyButton);
        }

        // Add the actual days of the month to the grid
        for (int day = 1; day <= maxDays; day++) {
            Button dayButton = new Button(this);
            dayButton.setText(String.valueOf(day));

            // Set up an OnClickListener to display a message when a day is clicked
            dayButton.setOnClickListener(v -> onDateSelected((Button) v));

            // Add the button to the calendar grid
            calendarGrid.addView(dayButton);
        }
    }

    // Method to handle date selection
    private void onDateSelected(Button dayButton) {
        String selectedDate = "Selected Date: " + dayButton.getText();
        Toast.makeText(this, selectedDate, Toast.LENGTH_SHORT).show();
    }

    // Method to move to the previous month
    private void moveToPreviousMonth() {
        calendar.add(Calendar.MONTH, -1); // Decrease the month by 1
        populateCalendar(); // Re-populate the calendar with the new month
    }

    // Method to move to the next month
    private void moveToNextMonth() {
        calendar.add(Calendar.MONTH, 1); // Increase the month by 1
        populateCalendar(); // Re-populate the calendar with the new month
    }

    // Method to get the month name based on the month index
    private String getMonthName(int month) {
        String[] monthNames = {
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        };
        return monthNames[month];
    }
}
