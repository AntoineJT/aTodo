package com.github.antoinejt.atodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.github.antoinejt.atodo.dataclasses.TaskItem;

import java.util.ArrayList;
import java.util.List;

public class DBUtils extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Database.db";
    private final SQLiteDatabase wdb;

    public DBUtils(Context context) {
        super(context, DATABASE_NAME , null, 1);
        this.wdb = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE Task (" +
                        "taskId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                        "taskName TEXT NOT NULL," +
                        "taskDescription TEXT NOT NULL," +
                        "taskListId TEXT NOT NULL," +
                        "taskFinished INTEGER NOT NULL DEFAULT 0 CHECK(taskFinished IN (0, 1)))"
        );

        db.execSQL(
                "CREATE TABLE TaskList (" +
                        "tasklistId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                        "tasklistName TEXT NOT NULL," +
                        "taskListCreatedAt DATETIME NOT NULL," +
                        "taskListEnds DATETIME NOT NULL)"
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
        db.execSQL("DROP TABLE IF EXISTS TaskList");
        db.execSQL("DROP TABLE IF EXISTS Category");
        db.execSQL("DROP TABLE IF EXISTS Alert");
        onCreate(db);
    }

    public boolean createTaskList(String name, String createdAt, String end){
        ContentValues contentValues = new ContentValues();
        contentValues.put("taskListName", name);
        contentValues.put("taskListCreatedAt", createdAt);
        contentValues.put("taskListEnds", end);
        wdb.insert("TaskList", null, contentValues);
        return true;
    }
    //TODO Review this insert thing, Task insert broken AF
    public String idSelect(String name){
        Cursor res =  wdb.rawQuery( "SELECT taskListId FROM Tasklist WHERE taskListName = ?", null );
        System.err.print(res.toString());
        return res.toString();
    }

    public void miniInsert(String taskName, String taskDescription, String taskFinished, String taskListId){
        //db.rawQuery("INSERT INTO Task (TaskName, TaskDescription, TaskFinished) VALUES (?, ?, ?) WHERE TaskList.taskListId = ?", null);
        wdb.rawQuery("INSERT INTO Task (taskName, taskDescription, taskFinished) VALUES (?, ?, ?)",
                new String[] { taskName, taskDescription, taskFinished});
    }

    // imo we must keep the taskListId in the activity's data to pass it by parameter here
    // to avoid doing an error-prone request (we can have 2 taskList named the same)
    public boolean insertTask(String taskName, String taskDescription, String taskFinished) {
        String listId = idSelect(taskName);
        miniInsert(taskName, taskDescription, taskFinished, listId);
        return true;
    }
    
/*
    public boolean insertTask(String taskName, String taskDescription, String taskFinished) {
        SQLiteDatabase wdb = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("taskName",taskName);
        contentValues.put("taskDescription",taskDescription);
        wdb.query("Task",new String[] {"taskId"},"taskIdList");
        contentValues.put();
        contentValues.put("taskFinished",taskFinished);
        //db.rawQuery("INSERT INTO Task (TaskName, TaskDescription, TaskFinished) VALUES (?, ?, ?) WHERE TaskList.taskListId = ?", null);
        //db.rawQuery("INSERT INTO Task (taskName, taskDescription, taskFinished) VALUES (?, ?, ?)", new String[] {"", "", ""});
        wdb.insert("Task",null, null);//Blank insert
        wdb.update("Task", contentValues,"taskId = (SELECT MAX(idTask) FROM Task)",null);
        //db.rawQuery("UPDATE Task SET taskId = (SELECT MAX(idTaskList) FROM TaskList), taskName = ?, taskDescription = ?, taskFinished = ? WHERE taskId = (SELECT MAX(idTask) FROM Task)",new String[] {taskName, taskDescription, taskFinished});
        return true;
    }
*/
    public boolean createCategory(String categoryName, String categoryDescription){
        ContentValues contentValues = new ContentValues();
        contentValues.put("categoryName", categoryName);
        contentValues.put("categoryDescription", categoryDescription);
        wdb.insert("Category", null, contentValues);
        return true;
    }

    public boolean createAlert(String alertsAt){
        ContentValues contentValues = new ContentValues();
        contentValues.put("alertsAt", alertsAt);
        wdb.insert("Alert", null, contentValues);
        return true;
    }



    public Integer deleteTaskList(Integer id) {
        return wdb.delete("TaskList", "tasklistId = ? ", new String[] { Integer.toString(id) });
    }

    public Integer deleteCategory(Integer id) {
        return wdb.delete("Category", "categoryId = ? ", new String[] { Integer.toString(id) });
    }

    public Integer deleteAlert(Integer id) {
        return wdb.delete("Alert", "alertId = ? ", new String[] { Integer.toString(id) });
    }

    public Integer deleteTask(Integer id) {
        return wdb.delete("Task", "taskId = ? ", new String[] { Integer.toString(id) });
    }



    public boolean updateTask(int id, String taskName, String taskDescription, String taskFinished) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("taskName", taskName);
        contentValues.put("taskDescription", taskDescription);
        contentValues.put("taskFinished", taskFinished);
        wdb.update("Task", contentValues, "taskId = ? ", new String[] { Integer.toString(id) });
        return true;
    }

    public boolean updateTaskList(int id, String taskName, String taskListCreatedAt, String end) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("taskListName", taskName);
        contentValues.put("taskListCreatedAt", taskListCreatedAt);
        contentValues.put("taskListEnds", end);
        wdb.update("TaskList", contentValues, "TaskList.tasklistId = ? ", new String[] { Integer.toString(id) });
        return true;
    }

    public boolean updateCategory(int id, String categoryName, String categoryDescription) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("categoryName", categoryName);
        contentValues.put("categoryDescription", categoryDescription);
        wdb.update("Category", contentValues, "categoryId = ? ", new String[] { Integer.toString(id) });
        return true;
    }

    public boolean updateAlert(int id, String alertsAt) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("alertsAt", alertsAt);
        wdb.update("Alert", contentValues, "alertId = ? ", new String[] { Integer.toString(id) });
        return true;
    }

    public List<TaskItem> getTasks() {
        Cursor res =  wdb.rawQuery( "SELECT taskName, taskDescription from Task", null );
        res.moveToFirst();

        List<TaskItem> taskItems = new ArrayList<>();
        while(!res.isAfterLast()){
            String name = res.getString(res.getColumnIndex("taskName"));
            String description = res.getString(res.getColumnIndex("taskDescription"));

            taskItems.add(new TaskItem(name, description));
            res.moveToNext();
        }
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
