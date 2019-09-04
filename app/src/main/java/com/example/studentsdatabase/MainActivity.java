package com.example.studentsdatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentsdatabase.adapter.MyAdapter;
import com.example.studentsdatabase.database.Database;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private Database mDatabase;
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
        mDatabase = new Database(MainActivity.this);
        allStudents = mDatabase.listStudents();

        if(allStudents.size() > 0){
            contactView.setVisibility(View.VISIBLE);
            mAdapter = new MyAdapter(this, allStudents);
            contactView.setAdapter(mAdapter);
        }else {
            contactView.setVisibility(View.GONE);
            Toast.makeText(this, "There is no student in the database. Start adding now", Toast.LENGTH_LONG).show();
        }
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
                    mDatabase.addStudents(newStudent);
                    Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                mAdapter.update();
                                            }
                                        }
                            , 500);

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mDatabase != null){
            mDatabase.close();
        }
    }
}
