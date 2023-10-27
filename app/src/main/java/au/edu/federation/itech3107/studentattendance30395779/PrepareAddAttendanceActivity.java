package au.edu.federation.itech3107.studentattendance30395779;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.checkattendance.R;

import org.litepal.LitePal;

import java.util.List;
import java.util.stream.Collectors;

import au.edu.federation.itech3107.studentattendance30395779.bean.Course;

public class PrepareAddAttendanceActivity extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    public Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_course);
        // Hide the default title bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        // Get the class from EditText and convert it to a string
        spinner = (Spinner) findViewById(R.id.spinner);
        // Query course data
        List<Course> courses = LitePal.where("1 == 1").find(Course.class);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            List<String> collect = courses.stream().map(Course::getName).collect(Collectors.toList());
            this.adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, collect);
            // Set spinner data source
            spinner.setAdapter(adapter);

        }
    }

    public void admit(View view) {
        // Pass the string type class data to the next CallActivity
        Intent intent = new Intent(PrepareAddAttendanceActivity.this, AttendanceActivity.class);
        intent.putExtra("course_data", spinner.getSelectedItem().toString());
        startActivity(intent);
        finish();
    }
}