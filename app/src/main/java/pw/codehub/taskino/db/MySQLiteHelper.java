package pw.codehub.taskino.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_PROJECTS = "projects";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_COLOR = "color";
    public static final String COLUMN_HOURLYRATE = "hourly_rate";
    public static final String COLUMN_DEADLINE = "deadline";
    public static final String COLUMN_CREATEDAT = "created_at";


    public static final String TABLE_TASKS = "tasks";
    public static final String COLUMN_IDT = "id";
    public static final String COLUMN_NAMET = "name";
    public static final String COLUMN_DESCRIPTIONT = "description";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_IDP = "id_project";

    private static final String DATABASE_NAME = "taskino.db";
    private static final int DATABASE_VERSION = 3;

    private static final String DATABASE_CREATE_PROJECTS = "create table "
            + TABLE_PROJECTS + " (" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_NAME
            + " text not null, " + COLUMN_DESCRIPTION
            + " text not null, " + COLUMN_COLOR
            + " char(10) not null," + COLUMN_HOURLYRATE
            + " integer not null, " + COLUMN_DEADLINE
            + " date DEFAULT (datetime('now')), " + COLUMN_CREATEDAT
            + ");";

    private static final String DATABASE_CREATE_TASKS = "create table "
            + TABLE_TASKS + " (" + COLUMN_IDT
            + " integer primary key autoincrement, " + COLUMN_NAMET
            + " text not null, " + COLUMN_DESCRIPTIONT
            + " text not null, " + COLUMN_TIME
            + " time, " + COLUMN_IDP
            + " integer);";
         //   + " FOREIGN KEY("+COLUMN_IDT+") REFERENCES "+ TABLE_PROJECTS+"("+COLUMN_ID +"));";



    public MySQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_PROJECTS);
        db.execSQL(DATABASE_CREATE_TASKS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists " +TABLE_PROJECTS);
        db.execSQL("Drop table if exists " + TABLE_TASKS);
        onCreate(db);
    }
}
