package au.edu.federation.itech3107.studentattendance30395779;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.checkattendance.R;

import au.edu.federation.itech3107.studentattendance30395779.adapter.StudentAdapter;
import au.edu.federation.itech3107.studentattendance30395779.bean.Course;
import au.edu.federation.itech3107.studentattendance30395779.bean.Student;
import au.edu.federation.itech3107.studentattendance30395779.bean.StudentAttendance;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class AttendanceActivity extends AppCompatActivity {
    private static String TAG = "AttendanceActivity";


    public Spinner spinner1;
    ListView listView;
    String[] strings;
    ArrayAdapter adapter;

    StudentAdapter studentAdapter;
    public String course_out;

    private List<StudentAttendance> stuAttendances;

    private List<Student> students;
    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        // Hide default action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        // Get the course data entered in the previous activity
        Intent intent = getIntent();
        course_out = intent.getStringExtra("course_data");

        initUI();
        initData();
        initListener();
    }

    private void initListener() {
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Handle Spinner option selection events
                stuAttendances = LitePal.where("coursename = ? and attendancedate = ?", course_out, strings[position]).find(StudentAttendance.class);
                if (stuAttendances == null || stuAttendances.size() == 0) {
                    if (students != null && students.size() > 0) {
                        for (Student student : students) {
                            // Add attendance records
                            StudentAttendance studentAttendance = new StudentAttendance();
                            studentAttendance.setCoursename(course_out);
                            studentAttendance.setAttendancedate(strings[position]);
                            studentAttendance.setStatus("0");
                            studentAttendance.setStuname(student.getName());
                            studentAttendance.setSid(student.getSid());
                            studentAttendance.save();
                            stuAttendances.add(studentAttendance);
                        }
                        // Create adapter and bind data source
                        studentAdapter = new StudentAdapter(AttendanceActivity.this, stuAttendances);
                        listView.setAdapter(studentAdapter);
                    }
                }else {
                    // Create adapter and bind data source
                    studentAdapter= new StudentAdapter(AttendanceActivity.this, stuAttendances);
                    listView.setAdapter(studentAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle Spinner no option selected events
            }
        });

        saveBtn.setOnClickListener(v -> {
            // Get the current attendance status and update it
            List<StudentAttendance> data = studentAdapter.getData();
            for (StudentAttendance attendance : data) {
                attendance.update(attendance.getId());
            }
            Toast.makeText(this, "Saved successfully", Toast.LENGTH_SHORT).show();
        });
    }


    private void initUI() {
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        saveBtn = (Button) findViewById(R.id.save);
        listView = findViewById(R.id.list_view);
    }

    // Database initialization
    private void initData() {
        Log.d(TAG, "initData");

        Course course = LitePal.where("name = ?", course_out).find(Course.class).get(0);
        strings = course.getTmp().split(",");
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, strings);
        // Set spinner data source
        spinner1.setAdapter(adapter);

        stuAttendances = LitePal.where("coursename = ? and attendancedate = ?", course_out, course.getStart()).find(StudentAttendance.class);
        students = LitePal.where("coursename = ?", course_out).find(Student.class);

        if (stuAttendances == null || stuAttendances.size() == 0) {
            if (students != null && students.size() > 0) {
                for (Student student : students) {
                    // Add attendance records
                    StudentAttendance studentAttendance = new StudentAttendance();
                    studentAttendance.setCoursename(course_out);
                    studentAttendance.setAttendancedate(course.getStart());
                    studentAttendance.setStatus("0");
                    studentAttendance.setStuname(student.getName());
                    studentAttendance.setSid(student.getSid());
                    studentAttendance.save();
                    stuAttendances.add(studentAttendance);
                }
                // Create adapter and bind data source
                studentAdapter = new StudentAdapter(this, stuAttendances);
                listView.setAdapter(studentAdapter);
            } else {
                Toast.makeText(this, "Please enter students under this course!", Toast.LENGTH_SHORT).show();
                // Create adapter and bind empty data source
                studentAdapter = new StudentAdapter(this, new ArrayList<>());
                listView.setAdapter(studentAdapter);
            }
        } else {
            // Create adapter and bind data source
            studentAdapter= new StudentAdapter(this, stuAttendances);
            listView.setAdapter(studentAdapter);
        }


    }


}