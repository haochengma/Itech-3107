package au.edu.federation.itech3107.studentattendance30395779.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.checkattendance.R;

import au.edu.federation.itech3107.studentattendance30395779.bean.Student;
import au.edu.federation.itech3107.studentattendance30395779.bean.StudentAttendance;

import java.util.List;
import java.util.stream.Collectors;

public class StudentAdapter extends ArrayAdapter<StudentAttendance> {
    private Context mContext;

    private List<StudentAttendance> studentList;

    public StudentAdapter(Context context, List<StudentAttendance> studentList) {

        super(context, 0, studentList);
        this.studentList = studentList;
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(mContext).inflate(R.layout.list_item_student, parent, false);
        }

        // Get the student object at the current position
        final StudentAttendance currentStudent = getItem(position);

        // Get the checkbox and name views in the list item
        CheckBox checkBox = listItemView.findViewById(R.id.checkbox);
        TextView nameTextView = listItemView.findViewById(R.id.student_name);

        if (currentStudent != null) {
            // Unbind the listener
            checkBox.setOnCheckedChangeListener(null);
            // Set the value of checkbox and name
            checkBox.setChecked("1".equals(currentStudent.getStatus()));
            // Rebind the listener
            CompoundButton.OnCheckedChangeListener listener = (buttonView, isChecked) -> {
                // Listener logic
                if (isChecked) {
                    // Operation performed when CheckBox is checked
                    // Traverse the list to find the object to be modified
                    for (StudentAttendance studentAttendance : studentList) {
                        if (studentAttendance.getId().equals(currentStudent.getId())) { // Find the object to be modified based on the condition
                            // Modify the property
                            studentAttendance.setStatus("1");
                            break; // Exit the loop after finding the object
                        }
                    }
                } else {
                    // Operation performed when CheckBox is unchecked
                    // Traverse the list to find the object to be modified
                    for (StudentAttendance studentAttendance : this.studentList) {
                        if (studentAttendance.getId().equals(currentStudent.getId())) { // Find the object to be modified based on the condition
                            // Modify the property
                            studentAttendance.setStatus("0");
                            break; // Exit the loop after finding the object
                        }
                    }
                }
            };
            checkBox.setOnCheckedChangeListener(listener);
            nameTextView.setText(currentStudent.getStuname());
        }

        return listItemView;
    }

    public List<StudentAttendance> getData() {
        return this.studentList;
    }

}