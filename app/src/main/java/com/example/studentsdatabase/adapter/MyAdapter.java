package com.example.studentsdatabase.adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.studentsdatabase.R;
import com.example.studentsdatabase.persistence.entity.Student;
import com.example.studentsdatabase.ViewModel.StudentViewModel;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    StudentViewModel studentViewModel;
    private Context context;
    private ArrayList<Student> listStudents = new ArrayList<>();


    public MyAdapter(Context context, StudentViewModel studentViewModel) {
        this.context = context;
        this.studentViewModel = studentViewModel;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_layout, parent, false);
        return new MyViewHolder(view);
    }

    public void setStudentList(ArrayList<Student> studentList) {
        this.listStudents = studentList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Student student = listStudents.get(position);

        holder.name.setText(student.getName());
        holder.mark.setText(student.getMarks());

        holder.editStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTaskDialog(student);
            }
        });

        holder.deleteStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studentViewModel.deleteStudent(student);
            }
        });
    }


    @Override
    public int getItemCount() {
        return listStudents.size();
    }


    private void editTaskDialog(final Student student) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.add_contact_layout, null);

        final EditText nameField = subView.findViewById(R.id.enter_name);
        final EditText contactField = subView.findViewById(R.id.enter_mark);

        if (student != null) {
            nameField.setText(student.getName());
            contactField.setText(String.valueOf(student.getMarks()));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit student");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("EDIT STUDENT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String name = nameField.getText().toString();
                final String ph_no = contactField.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(context, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                } else {
                    student.setName(name);
                    student.setMarks(ph_no);
                    studentViewModel.updateStudent(student);

                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Task cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }
}
