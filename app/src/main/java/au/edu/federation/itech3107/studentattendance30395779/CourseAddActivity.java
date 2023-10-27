package au.edu.federation.itech3107.studentattendance30395779;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.checkattendance.R;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.StringJoiner;

import au.edu.federation.itech3107.studentattendance30395779.bean.Course;

public class CourseAddActivity extends AppCompatActivity {
    private static String TAG = "CourseAddActivity";

    // Course name
    private EditText nameET;
    private EditText startET;
    private EditText endET;

    static Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        // Hide default action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        initUI();
    }

    @SuppressLint("SetTextI18n")
    private void initUI() {
        course = new Course();
        nameET = (EditText) findViewById(R.id.name);
        startET = (EditText) findViewById(R.id.start);
        endET = (EditText) findViewById(R.id.end);
        startET.setOnClickListener(v -> {
            Calendar mcalendar = Calendar.getInstance();     // Get current time - year, month and day
            int year = mcalendar.get(Calendar.YEAR);         // Get current year
            int month = mcalendar.get(Calendar.MONTH);       // Get current month
            final int day = mcalendar.get(Calendar.DAY_OF_MONTH);  // Get current day

            // Date picker dialog
            new DatePickerDialog(CourseAddActivity.this, (view1, year1, month1, dayOfMonth) -> {
                // This method gets the selected year, month and day, corresponding to three parameters - year, month and dayOfMonth
                startET.setText(year1 + "-" + month1 + "-" + dayOfMonth);
                // Automatically calculate end date
                Calendar instance = Calendar.getInstance();
                instance.set(year1, month1, dayOfMonth);
                endET.setText(get12WeekDates(instance));
                course.setStart(startET.getText().toString());
                course.setEnd(endET.getText().toString());
            }, year, month, day).show();   // Default display year, month and day when the calendar dialog pops up
        });
    }

    public static String get12WeekDates(Calendar startDate) {
        List<String> weekDates = new ArrayList<>();

        Calendar endDate = Calendar.getInstance();
        endDate.setTimeInMillis(startDate.getTimeInMillis());
        endDate.add(Calendar.WEEK_OF_YEAR, 11); // Calculate end date

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);

        Calendar current = Calendar.getInstance();
        current.setTimeInMillis(startDate.getTimeInMillis());

        while (!current.after(endDate)) {
            String formattedDate = dateFormat.format(current.getTime());
            weekDates.add(formattedDate);

            current.add(Calendar.WEEK_OF_YEAR, 1); // Add one week
        }

        StringBuilder sb = new StringBuilder();
        Iterator<String> iterator = weekDates.iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next());
            if (iterator.hasNext()) {
                sb.append(",");
            }
        }
        course.setTmp(sb.toString());
        return weekDates.get(weekDates.size()-1);
    }

    public void confirm(View view) {
        Log.d(TAG, "confirm");
        // Get user input information
        String name = nameET.getText().toString();
        // If user input information is incomplete, issue a warning message
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Please fill in all information!", Toast.LENGTH_SHORT).show();
            return;
        }
        // Pass the obtained data to the Student table
        course.setName(name);
        // Judge the situation of adding new information
        if (course.save()) {
            Toast.makeText(this, "New course information added successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to add new course information", Toast.LENGTH_SHORT).show();
        }


    }

    public void cancel(View view) {
        Log.d(TAG, "cancel");
        Intent intent = new Intent(CourseAddActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        // Add the operation you want to perform here
        Intent intent = new Intent(CourseAddActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        // Call super.onBackPressed() to continue executing the default return key behavior
        //super.onBackPressed();
    }

}