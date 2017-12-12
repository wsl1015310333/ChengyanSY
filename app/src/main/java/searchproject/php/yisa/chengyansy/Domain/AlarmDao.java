package searchproject.php.yisa.chengyansy.Domain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import searchproject.php.yisa.chengyansy.FilesUtils;
import searchproject.php.yisa.chengyansy.Model_All.Read_alarmJson;


/**
 * Created by Administrator on 2017/10/9 0009.
 */

public class AlarmDao {
    AlarmDbOpenHelper helper;
    Context context;
    public AlarmDao(Context context){
this.context=context;
        helper = new AlarmDbOpenHelper(context);
    }
// 		db.execSQL("create table Read_alarmJson_Read  (_id integer primary key autoincrement,Rid varchar(10),pic varchar(300),plate varchar(20),captureTime varchar(30),locationName varchar(100),reason varchar(100))");

    public void addRead(Read_alarmJson st, String table){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        String uid=   FilesUtils.sharedGetFile(context,"user","uid");
        values.put("Uid",uid);
        values.put("id", st.getId());
        values.put("pic", st.getPic());
        values.put("plate", st.getPlate());
        values.put("captureTime", st.getCaptureTime());
        values.put("locationName", st.getLocationName());
        values.put("reason", st.getReason());
        db.insert(table, null, values);
    }
    public void addUnRead(Read_alarmJson st,String table) {
        SQLiteDatabase db = helper.getWritableDatabase();

//		db.execSQL("insert into students values(null,?,?)",new Object[]{st.getName(),st.getSex()});
        ContentValues values = new ContentValues();
      String uid=   FilesUtils.sharedGetFile(context,"user","uid");
         values.put("Uid",uid);
        values.put("id", st.getId());
        values.put("pic", st.getPic());
        values.put("plate", st.getPlate());
        values.put("captureTime", st.getCaptureTime());
        values.put("locationName", st.getLocationName());
        values.put("reason", st.getReason());
        db.insert(table, null, values);

    }
    public void delete(String id,String table){
        SQLiteDatabase db = helper.getWritableDatabase();
     String uid=   FilesUtils.sharedGetFile(context,"user","uid");
     db.execSQL("delete from "+table+" where Uid="+uid+" and (select count(_id) from "+table+")> "+id+" and _id in (select _id from "+table+" order by _id desc limit (select count(_id) from "+table+") offset "+id+")");
      //  db.execSQL("delete from "+table+" where (select count(_id) from "+table+")> "+id+" and _id in (select _id from "+table+" order by _id desc limit (select count(_id) from "+table+") offset "+id+")");

        //    NSString *deletesql = @"delete from DetailHistory where (select count(rowid) from DetailHistory)> 30 and rowid in (select rowid from DetailHistory order by rowid desc limit (select count(rowid) from DetailHistory) offset 30)";
        //	db.delete("delete from DetailHistory where (select count(rowid) from DetailHistory)> 30 and rowid in (select rowid from DetailHistory order by rowid desc limit (select count(rowid) from DetailHistory) offset 30", null);
    }

    public List<Read_alarmJson> findRead(String table){
        SQLiteDatabase db = helper.getReadableDatabase();
        // select * from users where id=?
        //	db.query("students", new String[]{"_id","name","sex"}, "_id=?", new String[]{id}, null, null, null);
        //	Cursor cursor = db.rawQuery("select * from students where _id=?", new String[]{id});
        String uid=   FilesUtils.sharedGetFile(context,"user","uid");
        Cursor cursor = db.rawQuery("select *  from "+table+" where Uid= "+uid+" order by _id desc", new String[]{}); // Read_alarmJson_Read

      //  Cursor cursor = db.rawQuery("select *  from "+table+" order by _id desc", new String[]{}); // Read_alarmJson_Read

        List<Read_alarmJson>read_alarmJsons=new ArrayList<Read_alarmJson>();
        while (cursor.moveToNext()){
//			st = new Student();
			/*int _id = cursor.getInt(0);
			String name = cursor.getString(1);
			String sex = cursor.getString(2);
			st.setId(id);
			st.setName(name);
			st.setSex(sex);*/
            Read_alarmJson st = new Read_alarmJson();
            int _id = cursor.getInt(cursor.getColumnIndex("_id"));
            String id=cursor.getString(cursor.getColumnIndex("id"));
            String pic = cursor.getString(cursor.getColumnIndex("pic"));
            String plate = cursor.getString(cursor.getColumnIndex("plate"));
            String captureTime = cursor.getString(cursor.getColumnIndex("captureTime"));
            String locationName = cursor.getString(cursor.getColumnIndex("locationName"));
            String reason =  cursor.getString(cursor.getColumnIndex("reason"));
            st.setPlate(plate);
            st.setId(id);
            st.setPic(pic);
            st.setCaptureTime(captureTime);
            st.setLocationName(locationName);
            st.setReason(reason);
            read_alarmJsons.add(st);
        }
        cursor.close();

        return read_alarmJsons;
    }
    public List<Read_alarmJson> findUnRead(String table) {

        SQLiteDatabase db = helper.getReadableDatabase();
        // select * from users where id=?
        //	db.query("students", new String[]{"_id","name","sex"}, "_id=?", new String[]{id}, null, null, null);
        //	Cursor cursor = db.rawQuery("select * from students where _id=?", new String[]{id});

        //   Cursor cursor = db.rawQuery("select *  from "+table+" order by _id desc", new String[]{}); // Read_alarmJson_Read
        String uid = FilesUtils.sharedGetFile(context, "user", "uid");
        if (uid.length() > 0) {
            Cursor cursor = db.rawQuery("select *  from " + table + " where Uid= " + uid + " order by _id desc", new String[]{}); // Read_alarmJson_Read
            List<Read_alarmJson> read_alarmJsons = new ArrayList<Read_alarmJson>();
            while (cursor.moveToNext()) {
//			st = new Student();
			/*int _id = cursor.getInt(0);
			String name = cursor.getString(1);
			String sex = cursor.getString(2);
			st.setId(id);
			st.setName(name);
			st.setSex(sex);*/
                Read_alarmJson st = new Read_alarmJson();
                int _id = cursor.getInt(cursor.getColumnIndex("_id"));
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String pic = cursor.getString(cursor.getColumnIndex("pic"));
                String plate = cursor.getString(cursor.getColumnIndex("plate"));
                String captureTime = cursor.getString(cursor.getColumnIndex("captureTime"));
                String locationName = cursor.getString(cursor.getColumnIndex("locationName"));
                String reason = cursor.getString(cursor.getColumnIndex("reason"));

                st.setPlate(plate);
                st.setId(id);
                st.setPic(pic);
                st.setCaptureTime(captureTime);
                st.setLocationName(locationName);
                st.setReason(reason);
                read_alarmJsons.add(st);
            }


            cursor.close();

            return read_alarmJsons;
        }
        return  null;
    }
    public void deleteUnRead(String table){
        SQLiteDatabase db = helper.getWritableDatabase();
        String uid=   FilesUtils.sharedGetFile(context,"user","uid");
        //db.execSQL("delete from  "+table +"");
        if(uid.length()>0) {
            db.execSQL("delete from  " + table + " where Uid= " + uid);
        }
    }

}
