package au.edu.federation.itech3107.studentattendance30395779;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.checkattendance.R;
import com.google.android.material.navigation.NavigationView;

import au.edu.federation.itech3107.studentattendance30395779.adapter.GridAdapter;
import au.edu.federation.itech3107.studentattendance30395779.bean.Course;
import au.edu.federation.itech3107.studentattendance30395779.bean.Student;
import au.edu.federation.itech3107.studentattendance30395779.bean.StudentAttendance;

import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;

import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView lvCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Course List");
        setSupportActionBar(toolbar);


        // Toggle the navigation drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Set the navigation menu item selection event for NavigationView
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        lvCourse = findViewById(R.id.lv_course);
        List<Course> courses = LitePal.where("1 == 1").find(Course.class);
        List<String> collect = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            collect = courses.stream().map(Course::getName).collect(Collectors.toList());
        }
        GridAdapter adapter = new GridAdapter(this, collect);
        adapter.setOnItemClickListener(position -> {
            // Handle click event
            AppData.cc = courses.get(position);
            startActivity(new Intent(MainActivity.this, CourseManageActivity.class));
            finish();
        });
        int spanCount = 2; // Number of grids displayed per row
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, spanCount);
        lvCourse.setLayoutManager(layoutManager);
        lvCourse.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
/*

        // Create and delete database
        if (id == R.id.create_database) {
            Connector.getDatabase();
            Toast.makeText(MainActivity.this, "Database successfully created", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.clear_database) {
            LitePal.deleteAll(Student.class);
            LitePal.deleteAll(StudentAttendance.class);
            Toast.makeText(MainActivity.this, "Database successfully cleared", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.clear_data) {
            LitePal.deleteAll(StudentAttendance.class);
            Student clear = new Student();
            clear.setToDefault("noclass");
            clear.setToDefault("leave");
            clear.updateAll();
            Toast.makeText(MainActivity.this, "Data successfully cleared", Toast.LENGTH_SHORT).show();
        }
*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_call) {
            Intent intent = new Intent(MainActivity.this, PrepareAddAttendanceActivity.class);
            startActivity(intent);

        }else if (id == R.id.nav_look) {
            Intent intent = new Intent(MainActivity.this, PrepareAddAttendanceActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_QueryStu) {
            Intent intent = new Intent(MainActivity.this, StuQueryAllActivity.class);
            startActivity(intent);

        }  else if (id == R.id.nav_InformationEntry) {
            Intent intent = new Intent(MainActivity.this, StuInsertActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_courseEntry) {
            Intent intent = new Intent(MainActivity.this, CourseAddActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_Exit) {
            AlertDialog alert = new AlertDialog.Builder(MainActivity.this).create();
            alert.setMessage("Are you sure you want to exit?");
            alert.setButton(DialogInterface.BUTTON_NEGATIVE, "No", (dialogInterface, i) -> {
            });
            alert.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", (dialogInterface, i) -> finish());
            alert.show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}