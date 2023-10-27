package au.edu.federation.itech3107.studentattendance30395779;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.checkattendance.R;

import au.edu.federation.itech3107.studentattendance30395779.bean.Teacher;

import org.litepal.LitePal;

import java.util.List;


public class LoginActivity extends Activity {
    // Member variables
    EditText editname, editpass;
    Button butlogin, butreg;
    CheckBox checksave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Call the initialization method
        this.init();

        // Call the method to read saved user information
        this.readLoginInfo();

        // Add button click listener for "New User Registration"
        this.butreg.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Switch to the user registration page
                Intent abc = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(abc);

            }
        });
        // Add button click listener for login
        this.butlogin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Call the relevant login method
                loginAction();
            }
        });
    }

    /**
     * Initialize the interface
     */
    public void init() {

        LitePal.initialize(this); // Initialize LitePal configuration

       /* LitePal.getDatabase().close();
        LitePal.deleteDatabase("StudentInfo");
        LitePal.initialize(this);*/


        this.editname = (EditText) findViewById(R.id.loginditname);
        this.editpass = (EditText) findViewById(R.id.logeditpass);
        this.butlogin = (Button) findViewById(R.id.Logbutloging);
        this.butreg = (Button) findViewById(R.id.logbutredister);
        this.checksave = (CheckBox) findViewById(R.id.checkBox1);
    }

    /**
     * Login button functionality
     */
    public void loginAction() {
        // 1. Get the username and password entered by the user
        String name = this.editname.getText().toString();
        String pass = this.editpass.getText().toString();
        // 2. Check if the input is empty
        if (name.length() == 0 || pass.length() == 0) {
            Toast.makeText(this, "Incomplete data input, please modify", Toast.LENGTH_LONG).show();
            return;
        }
        // 3. Call the relevant method to query the database in the user information table
        // Find the students who need to make up for the attendance in the Student table
        List<Teacher> teacher = LitePal.where("teacher_name = ? and teacher_pwd = ?", name, pass).find(Teacher.class);
        // 4. Handle the result
        if (teacher == null || teacher.size() == 0) {
            // If the user information in the user information table is empty - login failed
            Toast.makeText(this, "Incorrect username or password, please modify!", Toast.LENGTH_LONG).show();
        } else {
            // If there is user information in the user information table - login successful - legitimate user
            Toast.makeText(this, "User login successful!", Toast.LENGTH_LONG).show();
            SharedPreferences sharesave = getSharedPreferences("loginfo", Context.MODE_PRIVATE);
            Editor editor = sharesave.edit();
            if (this.checksave.isChecked()) {
                // The user chooses to save the user information and display the username and password
                editor.putString("uname", name);
                editor.putString("upass", pass);
                // Submit
                editor.commit();
            } else {
                // The user did not choose to save, clear - or clear the saved content
                editor.clear();
                // Only display the username that the user has logged in, not the password
                editor.putString("uname", name);
                editor.commit();
            }
            // Legitimate user - login successful - jump to the user interface
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            // Destroy the login and registration interface - return and exit directly
            this.finish();
        }
    }

    /**
     * Read the saved username and password
     */
    public void readLoginInfo() {
        SharedPreferences sharelogin = getSharedPreferences("loginfo", Context.MODE_PRIVATE);
        // "Read based on the key name" "Display default empty value if not read" Context.private
        String uname = sharelogin.getString("uname", "");
        String upass = sharelogin.getString("upass", "");
        // Display the read content in the username and password fields
        this.editname.setText(uname);
        this.editpass.setText(upass);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}