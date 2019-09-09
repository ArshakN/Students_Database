package com.example.studentsdatabase;

import android.app.Application;

import com.example.studentsdatabase.persistence.DBWrapper;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DBWrapper.create(this);

    }


}