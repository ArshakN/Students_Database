package com.example.studentsdatabase.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentsdatabase.R;
import com.example.studentsdatabase.ViewModel.StudentViewModel;
import com.example.studentsdatabase.adapter.MyAdapter;
import com.example.studentsdatabase.persistence.entity.Student;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

//    private Database mDatabase;
private StudentViewModel studentViewModel;
    private ArrayList<Student> allStudents =new ArrayList<>();
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView contactView = findViewById(R.id.recycler_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        contactView.setLayoutManager(linearLayoutManager);
        contactView.setHasFixedSize(true);


        studentViewModel= ViewModelProviders.of(this).get(StudentViewModel.class);
        studentViewModel.init();
        mAdapter=new MyAdapter(MainActivity.this,studentViewModel);
        contactView.setAdapter(mAdapter);

        studentViewModel.getStudent().observe(MainActivity.this, new Observer<List<Student>>() {
            @Override
            public void onChanged(List<Student> students) {
                mAdapter.setStudentList((ArrayList<Student>) students);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTaskDialog();
            }
        });
    }

    private void addTaskDialog(){
        LayoutInflater inflater = LayoutInflater.from(this);
        View subView = inflater.inflate(R.layout.add_contact_layout, null);

        final EditText nameField = (EditText)subView.findViewById(R.id.enter_name);
        final EditText noField = (EditText)subView.findViewById(R.id.enter_mark);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add new STUDENT");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("ADD Student", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String name = nameField.getText().toString();
                final String mark = noField.getText().toString();

                if(TextUtils.isEmpty(name)){
                    Toast.makeText(MainActivity.this, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                }
                else{

                    Student newStudent = new Student(name, mark);
                    studentViewModel.saveStudent(newStudent);
                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Task cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }
}
