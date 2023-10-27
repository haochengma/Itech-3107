package au.edu.federation.itech3107.studentattendance30395779;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.checkattendance.R;

import au.edu.federation.itech3107.studentattendance30395779.bean.Teacher;


public class RegisterActivity extends Activity {
    // Member variables
    EditText editname, editpass1, editpass2;
    Button butsave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Call the initialization method
        this.init();
        // Add button click listener for "Save"
        this.butsave.setOnClickListener(arg0 -> {
            // Call the save method - saveAction
            saveAction();
        });
    }

    /**
     * Initialize the interface
     */
    private void init() {
        this.editname = findViewById(R.id.regeditname);
        this.editpass1 = findViewById(R.id.regditpass1);
        this.editpass2 = findViewById(R.id.regditpass2);
        this.butsave = findViewById(R.id.regbutsave);
    }

    /**
     * Save button functionality
     */
    public void saveAction() {
        // 1. Get the input
        String name = this.editname.getText().toString();
        String pass1 = this.editpass1.getText().toString();
        String pass2 = this.editpass2.getText().toString();
        // 2. Check if the input is empty
        if (name.length() == 0 || pass1.length() == 0 || pass2.length() == 0) {
            Toast.makeText(this, "Incomplete data input, please modify", Toast.LENGTH_LONG).show();
            // Return
            return;
        }
        // 3. Check if the passwords match
        if (!pass1.equals(pass2)) {
            // Passwords do not match
            Toast.makeText(this, "Passwords do not match, please modify", Toast.LENGTH_LONG).show();
            // Return
            return;
        }
        // 4. Call the relevant method to modify the database content
        Teacher teacher = new Teacher();
        teacher.setteacher_name(name);
        teacher.setteacher_pwd(pass1);
        if (teacher.save()) {
            // Add - Registration successful
            Toast.makeText(this, "New user registration successful", Toast.LENGTH_LONG).show();
        } else {
            // Add - Registration failed
            Toast.makeText(this, "New user registration failed", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Reset button functionality
     */
    public void resetAction() {
        this.editname.setText("");
        this.editpass1.setText("");
        this.editpass2.setText("");
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