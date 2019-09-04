package com.example.studentsdatabase.adapter;




import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.studentsdatabase.R;


public class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView name, mark;
    public ImageView deleteStudent;
    public ImageView editStudent;

    public MyViewHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.student_name);
        mark = itemView.findViewById(R.id.ph_no);
        deleteStudent = itemView.findViewById(R.id.delete_student);
        editStudent = itemView.findViewById(R.id.edit_student);
    }
}
