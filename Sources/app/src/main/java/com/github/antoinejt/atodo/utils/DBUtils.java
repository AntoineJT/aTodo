package com.github.antoinejt.atodo.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.github.antoinejt.atodo.dataclasses.TaskItem;
import com.github.antoinejt.exassert.Preconditions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBUtils extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Database.db";
    private final SQLiteDatabase wdb;

    public DBUtils(Context context) {
        super(context, DATABASE_NAME, null, 5);
        this.wdb = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE Task (" +
                        "taskId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                        "taskName TEXT NOT NULL," +
                        "taskDescription TEXT NOT NULL," +
                        "taskCreatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                        "taskEnds DATETIME NOT NULL," +
                        "taskFinished INTEGER NOT NULL DEFAULT 0 CHECK(taskFinished IN (0, 1)))"
        );

        db.execSQL(
                "CREATE TABLE Category(" +
                        "categoryId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                        "categoryName TEXT, " +
                        "categoryDescription TEXT)"
        );
        db.execSQL(
                "CREATE TABLE Alert(" +
                        "alertId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                        "alertsAt DATETIME)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Task");
        db.execSQL("DROP TABLE IF EXISTS Category");
        db.execSQL("DROP TABLE IF EXISTS Alert");
        onCreate(db);
    }

    public long createTask(String name, String description, Date deadline) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("taskName", name);
        contentValues.put("taskDescription", description);
        contentValues.put("taskEnds", deadline.getTime());
        return wdb.insert("Task", null, contentValues);
    }

    public long createCategory(String categoryName, String categoryDescription) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("categoryName", categoryName);
        contentValues.put("categoryDescription", categoryDescription);
        return wdb.insert("Category", null, contentValues);
    }

    public long createAlert(String alertsAt) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("alertsAt", alertsAt);
        return wdb.insert("Alert", null, contentValues);
    }

    public boolean deleteCategory(long id) {
        Preconditions.requiresUnsigned(id);
        return wdb.delete("Category", "categoryId = ? ", new String[]{Long.toString(id)}) == 1;
    }

    public boolean deleteAlert(long id) {
        Preconditions.requiresUnsigned(id);
        return wdb.delete("Alert", "alertId = ? ", new String[]{Long.toString(id)}) == 1;
    }

    public boolean deleteTask(long id) {
        Preconditions.requiresUnsigned(id);
        return wdb.delete("Task", "taskId = ? ", new String[]{Long.toString(id)}) == 1;
    }

    public boolean updateTask(long id, String name, String description, Date deadline, boolean finished) {
        Preconditions.requiresUnsigned(id);

        ContentValues contentValues = new ContentValues();
        contentValues.put("taskName", name);
        contentValues.put("taskDescription", description);
        contentValues.put("taskEnds", deadline.getTime());
        contentValues.put("taskFinished", finished ? 1 : 0);

        return wdb.update("Task", contentValues, "taskId = ?", new String[]{Long.toString(id)}) == 1;
    }

    public boolean updateCategory(long id, String categoryName, String categoryDescription) {
        Preconditions.requiresUnsigned(id);

        ContentValues contentValues = new ContentValues();
        contentValues.put("categoryName", categoryName);
        contentValues.put("categoryDescription", categoryDescription);

        return wdb.update("Category", contentValues, "categoryId = ? ", new String[]{Long.toString(id)}) == 1;
    }

    public boolean updateAlert(long id, String alertsAt) {
        Preconditions.requiresUnsigned(id);

        ContentValues contentValues = new ContentValues();
        contentValues.put("alertsAt", alertsAt);

        return wdb.update("Alert", contentValues, "alertId = ? ", new String[]{Long.toString(id)}) == 1;
    }

    private long asLong(Cursor cursor, String field) {
        return cursor.getLong(cursor.getColumnIndex(field));
    }

    private Date asDate(Cursor cursor, String field) {
        return new Date(asLong(cursor, field));
    }

    private String asString(Cursor cursor, String field) {
        return cursor.getString(cursor.getColumnIndex(field));
    }

    private int asInt(Cursor cursor, String field) {
        return cursor.getInt(cursor.getColumnIndex(field));
    }

    public List<TaskItem> getTasks() {
        // wdb.query("Task", new String[] { "taskName", "taskDescription" }, null, null, null, null, null);
        Cursor cursor = wdb.rawQuery("SELECT * from Task", null);
        cursor.moveToFirst();

        List<TaskItem> taskItems = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            final long id = asLong(cursor, "taskId");
            final String name = asString(cursor, "taskName");
            final String description = asString(cursor, "taskDescription");
            final Date createdAt = asDate(cursor, "taskCreatedAt");
            final Date deadline = asDate(cursor, "taskEnds");
            final boolean isFinished = asInt(cursor, "taskFinished") == 1;

            taskItems.add(new TaskItem(id, name, description, createdAt, deadline, isFinished));
            cursor.moveToNext();
        }
        cursor.close();
        return taskItems;
    }
/*
    public ArrayList<String> getTask() {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Task", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex("Task"));
            res.moveToNext();
        }
        return array_list;
    }
*/
}
