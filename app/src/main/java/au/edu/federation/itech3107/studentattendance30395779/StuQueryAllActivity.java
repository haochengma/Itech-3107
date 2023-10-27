package au.edu.federation.itech3107.studentattendance30395779;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.checkattendance.R;

import au.edu.federation.itech3107.studentattendance30395779.adapter.GridAdapter;
import au.edu.federation.itech3107.studentattendance30395779.bean.Student;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StuQueryAllActivity extends AppCompatActivity {
    // Query all through the student table
    private static String TAG = "QueryallActivity";

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queryall);
        listView = (ListView) findViewById(R.id.listAll);
        initData();
    }

    private void initData() {
        // Query all data
        List<Student> students = LitePal.findAll(Student.class);

        if (students == null) {
            Toast.makeText(this, "Failed to get student information, please try again later!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            List<Map<String, String>> maps = new ArrayList<>();
            for (int i = 0; i < students.size(); i++) {
                Map<String, String> map = new HashMap<>();
                map.put("name", students.get(i).getName());
                map.put("id", students.get(i).getId().toString());
                map.put("sid", students.get(i).getSid());
                map.put("coursename", students.get(i).getCoursename());
                maps.add(map);
            }
            String[] strings = {"sid", "name", "coursename"};
            int[] ids = {R.id.name, R.id.sid, R.id.absent};

            final SimpleAdapter[] adapter = {new SimpleAdapter(this, maps, R.layout.item_queryall, strings, ids)};  // ListView adapts item_queyall.xml
            listView.setAdapter(adapter[0]);

            //click
            listView.setOnItemClickListener((parent, view, position, id) -> {
                //Toast.makeText(StuQueryAllActivity.this, "sdgsdgds", Toast.LENGTH_SHORT).show();

                new AlertDialog.Builder(StuQueryAllActivity.this).setTitle("")
                        .setMessage("is delete？").setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String id1 = maps.get(position).get("id");
                                int i = Integer.parseInt(id1);
                                LitePal.delete(Student.class, i);
                                maps.remove(position);
                                adapter[0] = new SimpleAdapter(StuQueryAllActivity.this, maps, R.layout.item_queryall, strings, ids);  // ListView adapts item_queyall.xml
                                listView.setAdapter(adapter[0]);
                            }
                        }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();

            });
        }
    }
}