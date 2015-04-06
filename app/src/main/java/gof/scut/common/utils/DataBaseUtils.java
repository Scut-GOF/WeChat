package gof.scut.common.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class DataBaseUtils {
    private static DataBaseHelper dataBaseHelper;

    public DataBaseUtils(Context context) {
        dataBaseHelper = new DataBaseHelper(context);
        //insert several data
        /*for (int i=0;i<10;i++){
            insertAll("Friend"+i,""+i,""+i,"10086",i%3,"","");
        }*/
    }

    //insert
    public long insertAll(String name, String lPinYin, String sPinYin, String tel,
                          int label, String address, String notes) {
        ContentValues value = new ContentValues();
        value.put(DBConstants.NAME, name);
        value.put(DBConstants.L_PINYIN, lPinYin);
        value.put(DBConstants.S_PINYIN, sPinYin);
        value.put(DBConstants.TEL, tel);
        value.put(DBConstants.LABEL, label);
        value.put(DBConstants.ADDRESS, address);
        value.put(DBConstants.NOTES, notes);
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        long status = db.insert(DBConstants.TABLE_NAME, null, value);
        db.close();
        return status;
    }

    //delete
    public int deleteWithID(String id) {
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        int status = db.delete(DBConstants.TABLE_NAME,
                DBConstants.ID + "=?", new String[]{id});
        db.close();
        return status;
    }

    public int deleteWithTel(String tel) {
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        int status = db.delete(DBConstants.TABLE_NAME,
                DBConstants.TEL + "=?", new String[]{tel});
        db.close();
        return status;
    }

    //update
    public void update(String setToSQL, String condition) {
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        db.execSQL("UPDATE " + DBConstants.TABLE_NAME
                + " SET " + setToSQL + " " + condition + " ;");
        db.close();
    }

    //update ALL
    public long updateAllWithID(String name, String lPinYin, String sPinYin, String tel,
                                String label, String address, String notes,
                                String byID) {
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(DBConstants.NAME, name);
        value.put(DBConstants.L_PINYIN, lPinYin);
        value.put(DBConstants.S_PINYIN, sPinYin);
        value.put(DBConstants.TEL, tel);
        value.put(DBConstants.LABEL, label);
        value.put(DBConstants.ADDRESS, address);
        value.put(DBConstants.NOTES, notes);
        long status;
        status = db.update(DBConstants.TABLE_NAME, value,
                DBConstants.ID + "=?", new String[]{byID});
        db.close();
        return status;
    }

    //update ALL
    public long updateAllWithTel(String name, String lPinYin, String sPinYin, String tel,
                                 String label, String address, String notes,
                                 String byTel) {
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(DBConstants.NAME, name);
        value.put(DBConstants.L_PINYIN, lPinYin);
        value.put(DBConstants.S_PINYIN, sPinYin);
        value.put(DBConstants.TEL, tel);
        value.put(DBConstants.LABEL, label);
        value.put(DBConstants.ADDRESS, address);
        value.put(DBConstants.NOTES, notes);
        long status;
        status = db.update(DBConstants.TABLE_NAME, value, DBConstants.TEL + "=?",
                new String[]{byTel});
        db.close();
        return status;
    }

    //query
    public Cursor selectAll() {
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        Cursor c = db.query(DBConstants.TABLE_NAME, null, null, null, null, null, null);
        //db.close();
        return c;
    }

    public Cursor selectAllOrderByLabel() {
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        Cursor c = db.query(DBConstants.TABLE_NAME, null, null, null,
                null, null, DBConstants.LABEL);
        //db.close();
        return c;
    }

    public Cursor selectWithCondition(String[] columns, String selection, String[] selectionArgs,
                                      String groupBy, String having, String orderBy) {
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        Cursor c = db.query(DBConstants.TABLE_NAME, columns, selection,
                selectionArgs, groupBy, having, orderBy);
        //db.close();
        return c;
    }

    public Cursor selectAllWithID(String id) {
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        Cursor c = db.query(DBConstants.TABLE_NAME,
                new String[]{DBConstants.NAME, DBConstants.L_PINYIN,
                        DBConstants.S_PINYIN, DBConstants.TEL, DBConstants.LABEL,
                        DBConstants.ADDRESS, DBConstants.NOTES},
                DBConstants.ID + " LIKE ?", new String[]{id}, null, null, null);
        //db.close();
        return c;
    }

    public Cursor selectAllWithTel(String tel) {
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        Cursor c = db.query(DBConstants.TABLE_NAME,
                new String[]{DBConstants.NAME, DBConstants.L_PINYIN,
                        DBConstants.S_PINYIN, DBConstants.TEL, DBConstants.LABEL,
                        DBConstants.ADDRESS, DBConstants.NOTES},
                DBConstants.TEL + " LIKE ?", new String[]{tel}, null, null, null);
        //db.close();
        return c;
    }

    public Cursor selectAllWithName(String name) {
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        Cursor c = db.query(DBConstants.TABLE_NAME,
                new String[]{DBConstants.NAME, DBConstants.L_PINYIN,
                        DBConstants.S_PINYIN, DBConstants.TEL, DBConstants.LABEL,
                        DBConstants.ADDRESS, DBConstants.NOTES},
                DBConstants.NAME + " LIKE ?", new String[]{name}, null, null, null);
        //db.close();
        return c;
    }

    public Cursor selectAllWithLabel(String label) {
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        Cursor c = db.query(DBConstants.TABLE_NAME,
                new String[]{DBConstants.NAME, DBConstants.L_PINYIN,
                        DBConstants.S_PINYIN, DBConstants.TEL, DBConstants.LABEL,
                        DBConstants.ADDRESS, DBConstants.NOTES},
                DBConstants.LABEL + " = ?", new String[]{label}, null, null, null);
        //db.close();
        return c;
    }

    public Cursor selectAllWithAddress(String address) {
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        Cursor c = db.query(DBConstants.TABLE_NAME,
                new String[]{DBConstants.NAME, DBConstants.L_PINYIN,
                        DBConstants.S_PINYIN, DBConstants.TEL, DBConstants.LABEL,
                        DBConstants.ADDRESS, DBConstants.NOTES},
                DBConstants.ADDRESS + " LIKE ?", new String[]{address}, null, null, null);
        //db.close();
        return c;
    }

    public Cursor selectAllWithNotes(String notes) {
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        Cursor c = db.query(DBConstants.TABLE_NAME,
                new String[]{DBConstants.NAME, DBConstants.L_PINYIN,
                        DBConstants.S_PINYIN, DBConstants.TEL, DBConstants.LABEL,
                        DBConstants.ADDRESS, DBConstants.NOTES},
                DBConstants.NOTES + " LIKE ?", new String[]{notes}, null, null, null);
        //db.close();
        return c;
    }
}
