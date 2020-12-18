package pw.codehub.taskino.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Editable;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ProjectsDoa {
    private SQLiteOpenHelper dbHelper;
    private SQLiteDatabase database;
    private String[] allcolumns = { MySQLiteHelper.COLUMN_ID,MySQLiteHelper.COLUMN_NAME,
        MySQLiteHelper.COLUMN_DESCRIPTION,MySQLiteHelper.COLUMN_COLOR,MySQLiteHelper.COLUMN_HOURLYRATE,
        MySQLiteHelper.COLUMN_DEADLINE
    };

    public ProjectsDoa(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Projects createproject(String name, String Description, int color, int hourly_rate, String deadline) {

        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NAME, name);
        values.put(MySQLiteHelper.COLUMN_DESCRIPTION, Description);
        values.put(MySQLiteHelper.COLUMN_COLOR, color);
        values.put(MySQLiteHelper.COLUMN_HOURLYRATE, hourly_rate);
        values.put(MySQLiteHelper.COLUMN_DEADLINE, deadline);

        long insertId = database.insert(MySQLiteHelper.TABLE_PROJECTS, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_PROJECTS,
                allcolumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Projects newProject = cursorToProject(cursor);
        cursor.close();
        return newProject;

    }

    public void deleteproject(String id) {
        database.delete(MySQLiteHelper.TABLE_PROJECTS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Projects> getAllProjects() {
        List<Projects> projects = new ArrayList<Projects>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_PROJECTS,
                allcolumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Projects project = cursorToProject(cursor);
            projects.add(project);
            cursor.moveToNext();
        }
        // assurez-vous de la fermeture du curseur
        cursor.close();
        return projects;
    }
    private Projects cursorToProject(Cursor cursor) {
        Projects project = new Projects();
        project.setId(cursor.getLong(0));
        project.setName(cursor.getString(1));
        project.setDescription(cursor.getString(2));
        project.setColor(cursor.getInt(3));
        project.setHourly_rate(cursor.getInt(4));
        project.setDeadline(cursor.getString(5));
        return project;
    }
}

