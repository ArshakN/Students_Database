package com.example.studentsdatabase.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.studentsdatabase.persistence.entity.Student;

import java.util.List;


@Dao
public interface StudentDao {

    @Query("SELECT * FROM student")
    LiveData<List<Student>> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Student student);

    @Update
    void update(Student student);

    @Delete
    void delete(Student student);


}

