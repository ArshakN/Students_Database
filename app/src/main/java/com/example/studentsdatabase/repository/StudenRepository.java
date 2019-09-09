package com.example.studentsdatabase.repository;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.studentsdatabase.persistence.AppDatabase;
import com.example.studentsdatabase.persistence.DBWrapper;
import com.example.studentsdatabase.persistence.entity.Student;

import java.util.List;

public class StudenRepository {
    private static StudenRepository studenRepository;
    AppDatabase database = DBWrapper.getDatabase();

    public static StudenRepository getInstance() {
        if (studenRepository == null) {
            studenRepository = new StudenRepository();
        }
        return studenRepository;
    }

    public void saveStudent(final Student student) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                database.studentDAO().insert(student);
                return null;
            }
        }.execute();
    }

    public void updateStudent(final Student student){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                database.studentDAO().update(student);
                return null;
            }
        }.execute();
    }

public void deleteStudent(final Student student)
{
    new AsyncTask<Void, Void, Void>() {
        @Override
        protected Void doInBackground(Void... voids) {
            database.studentDAO().delete(student);
            return null;
        }
    }.execute();

}

    public LiveData<List<Student>> getStudent() {
        return database.studentDAO().getAll();
    }
}
