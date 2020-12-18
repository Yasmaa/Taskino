package pw.codehub.taskino.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class TasksDoa {
    private SQLiteOpenHelper dbHelper;
    private SQLiteDatabase database;
    private String[] allcolumns = { MySQLiteHelper.COLUMN_IDT,MySQLiteHelper.COLUMN_NAMET,
        MySQLiteHelper.COLUMN_DESCRIPTIONT,MySQLiteHelper.COLUMN_TIME,
        MySQLiteHelper.COLUMN_IDP
    };

    public TasksDoa(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Tasks createtask(String name, String Description, String time, Integer idp) {

        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NAMET, name);
        values.put(MySQLiteHelper.COLUMN_DESCRIPTIONT, Description);
        values.put(MySQLiteHelper.COLUMN_TIME, time);
        values.put(MySQLiteHelper.COLUMN_IDP, idp);

        long insertId = database.insert(MySQLiteHelper.TABLE_TASKS, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_TASKS,
                allcolumns, MySQLiteHelper.COLUMN_IDP + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Tasks newTask = cursorToTask(cursor);
        cursor.close();
        return newTask;

    }

    public void deletetask(String id ) {
        database.delete(MySQLiteHelper.TABLE_TASKS, MySQLiteHelper.COLUMN_IDP
                + " = " + id, null);
    }



    public void updatetime(String id,String l) {

        ContentValues values=new ContentValues();
        values.put(MySQLiteHelper.COLUMN_TIME,l);


        database.update(MySQLiteHelper.TABLE_TASKS,values,MySQLiteHelper.COLUMN_IDT
                + " = " + id, null);

    }


    public List<Tasks> getAllTasks(String id) {
        List<Tasks> tasks = new ArrayList<Tasks>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_TASKS,
                allcolumns,MySQLiteHelper.COLUMN_IDP
                        + " = " + id, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Tasks task = cursorToTask(cursor);
            tasks.add(task);
            cursor.moveToNext();
        }
        // assurez-vous de la fermeture du curseur
        cursor.close();
        return tasks;
    }

    public List<Tasks> getTime(String id) {
        List<Tasks> task = new ArrayList<Tasks>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_TASKS,
                allcolumns,MySQLiteHelper.COLUMN_IDT
                        + " = " + id, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Tasks tas = cursorToTask(cursor);
            task.add(tas);
            cursor.moveToNext();
        }
        // assurez-vous de la fermeture du curseur
        cursor.close();
        return task;
    }


    private Tasks cursorToTask(Cursor cursor) {
        Tasks task = new Tasks();
        task.setId(cursor.getLong(0));
        task.setName(cursor.getString(1));
        task.setDescription(cursor.getString(2));
        task.setTime(cursor.getString(3));
        task.setIdP(cursor.getLong(4));
        return task;
    }
}

