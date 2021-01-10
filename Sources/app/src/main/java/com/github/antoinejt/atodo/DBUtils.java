package com.github.antoinejt.atodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class DBUtils extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "Database.db";

    public DBUtils(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                String.format("create table Task (" +
                        "taskId integer NOT NULL PRIMARY KEY AUTOINCREMENT," +
                        "taskName text NOT NULL," +
                        "taskDescription text NOT NULL," +
                        "taskFinished INTEGER NOT NULL DEFAULT 0 CHECK(taskFinished IN (0,1))")
        );

        db.execSQL(
                String.format("create table TaskList (" +
                        "tasklistId integer NOT NULL PRIMARY KEY AUTOINCREMENT," +
                        "tasklistName text NOT NULL," +
                        "taskListCreatedAt datetime NOT NULL," +
                        "taskListEnds datetime NOT NULL )")
        );
        db.execSQL(
                String.format("create table Category(" +
                        "categoryId integer NOT NULL PRIMARY KEY AUTOINCREMENT," +
                        " categoryName text," +
                        " categoryDescription text)")
        );
        db.execSQL(
                String.format("create table Alert(" +
                        "alertId integer NOT NULL PRIMARY KEY AUTOINCREMENT," +
                        " alertsAt datetime)")
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Task");
        db.execSQL("DROP TABLE IF EXISTS TaskList");
        db.execSQL("DROP TABLE IF EXISTS Category");
        db.execSQL("DROP TABLE IF EXISTS Alert");
        onCreate(db);
    }

    public boolean createTaskList(String name, String createdAt, String end){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("taskListName", name);
        contentValues.put("taskListCreatedAt", createdAt);
        contentValues.put("taskListEnds", end);
        db.insert("TaskList", null, contentValues);
        return true;
    }

    public boolean insertTask(String taskName, String taskDescription, String taskFinished) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("taskName", taskName);
        contentValues.put("taskDescription", taskDescription);
        contentValues.put("taskFinished", taskFinished);
        db.insert("Task", null, contentValues);
        return true;
    }

    public boolean createCategory(String categoryName, String categoryDescription){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("categoryName", categoryName);
        contentValues.put("categoryDescription", categoryDescription);
        db.insert("Category", null, contentValues);
        return true;
    }

    public boolean createAlert(String alertsAt){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("alertsAt", alertsAt);
        db.insert("Alert", null, contentValues);
        return true;
    }



    public Integer deleteTaskList(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("TaskList", "id = ? ", new String[] { Integer.toString(id) });
    }

    public Integer deleteCategory(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Category", "id = ? ", new String[] { Integer.toString(id) });
    }

    public Integer deleteAlert(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Alert", "id = ? ", new String[] { Integer.toString(id) });
    }

    public Integer deleteTask(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Task", "id = ? ", new String[] { Integer.toString(id) });
    }



    public boolean updateTask(int id, String taskName, String taskDescription, String taskFinished) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("taskName", taskName);
        contentValues.put("taskDescription", taskDescription);
        contentValues.put("taskFinished", taskFinished);
        db.update("Task", contentValues, "id = ? ", new String[] { Integer.toString(id) });
        return true;
    }

    public boolean updateTaskList(int id, String taskName, String taskListCreatedAt, String end) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("taskListName", taskName);
        contentValues.put("taskListCreatedAt", taskListCreatedAt);
        contentValues.put("taskListEnds", end);
        db.update("TaskList", contentValues, "id = ? ", new String[] { Integer.toString(id) });
        return true;
    }

    public boolean updateCategory(int id, String categoryName, String categoryDescription) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("categoryName", categoryName);
        contentValues.put("categoryDescription", categoryDescription);
        db.update("Category", contentValues, "id = ? ", new String[] { Integer.toString(id) });
        return true;
    }

    public boolean updateAlert(int id, String alertsAt) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("alertsAt", alertsAt);
        db.update("Alert", contentValues, "id = ? ", new String[] { Integer.toString(id) });
        return true;
    }

}
