package searchproject.php.yisa.chengyansy.Domain;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/10/9 0009.
 */

public class AlarmDbOpenHelper extends SQLiteOpenHelper {

    public AlarmDbOpenHelper(Context context) {
        super(context, "Read_alarmJson.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("create table Read_ZBalarmJson_Read  (_id integer primary key autoincrement,Uid varchar(30), name varchar(30),id varchar(10),pic varchar(300),plate varchar(20),captureTime varchar(30),locationName varchar(100),reason varchar(100))");
        db.execSQL("create table Read_ZBalarmJson_unRead  (_id integer primary key autoincrement,Uid varchar(30), name varchar(30),id varchar(10),pic varchar(300),plate varchar(20),captureTime varchar(30),locationName varchar(100),reason varchar(100))");
        db.execSQL("create table Read_BKalarmJson_Read  (_id integer primary key autoincrement,Uid varchar(30), name varchar(30),id varchar(10),pic varchar(300),plate varchar(20),captureTime varchar(30),locationName varchar(100),reason varchar(100))");
        db.execSQL("create table Read_BKalarmJson_unRead  (_id integer primary key autoincrement,Uid varchar(30), name varchar(30),id varchar(10),pic varchar(300),plate varchar(20),captureTime varchar(30),locationName varchar(100),reason varchar(100))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
