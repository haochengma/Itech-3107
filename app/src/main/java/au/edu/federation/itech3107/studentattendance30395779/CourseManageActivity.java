package au.edu.federation.itech3107.studentattendance30395779;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.checkattendance.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import au.edu.federation.itech3107.studentattendance30395779.bean.Course;

public class CourseManageActivity extends AppCompatActivity {
    private static String TAG = "CourseManageActivity";

    // Course name
    private EditText nameET;
    private EditText startET;
    private EditText endET;

    static Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_namage_course);
        // Hide the default title bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        course = AppData.cc;
        initUI();
    }

    @SuppressLint("SetTextI18n")
    private void initUI() {
        nameET = (EditText) findViewById(R.id.name);
        startET = (EditText) findViewById(R.id.start);
        endET = (EditText) findViewById(R.id.end);
        startET.setOnClickListener(v -> {
            Calendar mcalendar = Calendar.getInstance();     // Get current time - year, month, day
            int year = mcalendar.get(Calendar.YEAR);         // Get current year
            int month = mcalendar.get(Calendar.MONTH);       // Get current month
            final int day = mcalendar.get(Calendar.DAY_OF_MONTH);  // Get current day

            // Date picker dialog
            new DatePickerDialog(CourseManageActivity.this, (view1, year1, month1, dayOfMonth) -> {
                // Get the selected year, month, and day of the month
                startET.setText(year1 + "-" + month1 + "-" + dayOfMonth);
                // Automatically calculate the end date
                Calendar instance = Calendar.getInstance();
                instance.set(year1, month1, dayOfMonth);
                endET.setText(get12WeekDates(instance));
                course.setStart(startET.getText().toString());
                course.setEnd(endET.getText().toString());
            }, year, month, day).show();   // Show year, month, and day by default when the calendar dialog pops up
        });
        nameET.setText(course.getName());
        startET.setText(course.getStart());
        endET.setText(course.getEnd());
    }

    public static String get12WeekDates(Calendar startDate) {
        List<String> weekDates = new ArrayList<>();

        Calendar endDate = Calendar.getInstance();
        endDate.setTimeInMillis(startDate.getTimeInMillis());
        endDate.add(Calendar.WEEK_OF_YEAR, 11); // Calculate the end date

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
        return weekDates.get(weekDates.size() - 1);
    }

    public void confirm(View view) {
        Log.d(TAG, "confirm");
        // Get user input information
        String name = nameET.getText().toString();
        // If the user input information is not complete, prompt a warning message
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Please fill in all the information!", Toast.LENGTH_SHORT).show();
            return;
        }
        // Pass the obtained data to the Course table
        course.setName(name);
        // Determine the situation of adding new information
        if (course.update(course.getId()) > 0) {
            Toast.makeText(this, "Course information saved successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to save course information", Toast.LENGTH_SHORT).show();
        }
    }

    public void delete(View view) {
        Log.d(TAG, "confirm");

        // Delete by id
        if (course.delete() > 0) {
            Toast.makeText(this, "Course information deleted successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to delete course information", Toast.LENGTH_SHORT).show();
        }
    }

    public void cancel(View view) {
        Log.d(TAG, "cancel");
        startActivity(new Intent(CourseManageActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        // Add the operation you want to execute here
        startActivity(new Intent(CourseManageActivity.this, MainActivity.class));
        finish();
        // Call super.onBackPressed() to continue executing the default back key behavior
        //super.onBackPressed();
    }

}