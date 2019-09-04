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
import com.example.studentsdatabase.Student;
import com.example.studentsdatabase.database.Database;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context context;
    private ArrayList<Student> listStudents;

    private Database mDatabase;

    public MyAdapter(Context context, ArrayList<Student> listStudents) {
        this.context = context;
        this.listStudents = listStudents;
        mDatabase = new Database(context);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_layout, parent, false);
        return new MyViewHolder(view);
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
                mDatabase.deleteStudent(student.getId());
                update();
            }
        });
    }


    public void update()
    {
        listStudents=mDatabase.listStudents();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listStudents.size();
    }


    private void editTaskDialog(final Student student){
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.add_contact_layout, null);

        final EditText nameField = subView.findViewById(R.id.enter_name);
        final EditText contactField =subView.findViewById(R.id.enter_mark);

        if(student != null){
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

                if(TextUtils.isEmpty(name)){
                    Toast.makeText(context, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                }
                else{
                    mDatabase.updateStudents(new Student(student.getId(), name, ph_no));
                    update();
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
