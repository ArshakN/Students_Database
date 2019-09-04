package com.example.studentsdatabase.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.studentsdatabase.Student;

import java.util.ArrayList;



public class Database extends SQLiteOpenHelper {

    private	static final int DATABASE_VERSION =	5;
    private	static final String	DATABASE_NAME = "student";
    private	static final String TABLE_STUDENT = "students";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "studentname";
    private static final String COLUMN_NO = "mark";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String	CREATE_CONTACTS_TABLE = "CREATE	TABLE " + TABLE_STUDENT + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_NAME + " TEXT," + COLUMN_NO + " INTEGER" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        onCreate(db);
    }

    public ArrayList<Student> listStudents(){
        String sql = "select * from " + TABLE_STUDENT;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Student> students = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                String phno = cursor.getString(2);
                students.add(new Student(id, name, phno));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return students;
    }

    public void addStudents(Student student){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, student.getName());
        values.put(COLUMN_NO, student.getMarks());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_STUDENT, null, values);
    }

    public void updateStudents(Student student){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, student.getName());
        values.put(COLUMN_NO, student.getMarks());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_STUDENT, values, COLUMN_ID	+ "	= ?", new String[] { String.valueOf(student.getId())});
    }

    public void deleteStudent(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STUDENT, COLUMN_ID	+ "	= ?", new String[] { String.valueOf(id)});
    }
}
