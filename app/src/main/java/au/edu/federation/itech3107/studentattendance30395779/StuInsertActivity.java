package au.edu.federation.itech3107.studentattendance30395779;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.checkattendance.R;

import au.edu.federation.itech3107.studentattendance30395779.bean.Student;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class StuInsertActivity extends AppCompatActivity {
    private static String TAG = "InsertActivity";

    private EditText nameET;
    private EditText sidET;
    private EditText classidET;
    private EditText coursenameET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_insert);
        // Hide the default title bar
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        initUI();
    }

    private void initUI() {
        nameET = (EditText) findViewById(R.id.name);
        sidET = (EditText) findViewById(R.id.sid);
        coursenameET = (EditText) findViewById(R.id.coursename);
    }

    public void confirm(View view) {
        Log.d(TAG, "confirm");
        // Get user input information
        String name = nameET.getText().toString();
        String sid = sidET.getText().toString();
        String coursename = coursenameET.getText().toString();
        // If the user input information is incomplete, prompt a warning message
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(sid) ||  TextUtils.isEmpty(coursename)) {
            Toast.makeText(this, "Please complete all information!", Toast.LENGTH_SHORT).show();
            return;
        }
        // Pass the obtained data to the Student table
        Student student = new Student();
        student.setName(name);
        student.setSid(sid);
        student.setCoursename(coursename);
        // Create SimpleDateFormat object and specify the date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        // Determine the situation of adding new information
        if (student.save()) {
            Toast.makeText(this, "New student information added successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to add new student information", Toast.LENGTH_SHORT).show();
        }
    }

    public void cancel(View view) {
        Log.d(TAG, "cancel");
        finish();
    }
}