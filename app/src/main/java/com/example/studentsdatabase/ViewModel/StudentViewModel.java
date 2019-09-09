package com.example.studentsdatabase.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.studentsdatabase.persistence.entity.Student;
import com.example.studentsdatabase.repository.StudenRepository;

import java.util.List;

public class StudentViewModel extends ViewModel {
    private MutableLiveData<Student> mutableLiveData;
    private StudenRepository studenRepository;

    public void init(){
        if (mutableLiveData != null){
            return;
        }
        studenRepository = StudenRepository.getInstance();
    }

    public LiveData<List<Student>> getStudent(){

        return studenRepository.getStudent();
    }

    public void saveStudent(Student student){

        if (studenRepository==null){

            Log.i("LOG","NULL REPO");
        }
        else if (student==null){

            Log.i("LOG","NULL RESULT");
        }
        studenRepository.saveStudent(student);

    }

    public void updateStudent(Student student) {
        studenRepository.updateStudent(student);
    }

    public void deleteStudent(Student student) {
        studenRepository.deleteStudent(student);
    }




    }
