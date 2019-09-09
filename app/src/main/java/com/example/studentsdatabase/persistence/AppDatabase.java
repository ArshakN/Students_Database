package com.example.studentsdatabase.persistence;

import androidx.room.Database;
import androidx.room.RoomDatabase;


import com.example.studentsdatabase.persistence.entity.Student;

@Database(entities = {Student.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract StudentDao studentDAO();
}