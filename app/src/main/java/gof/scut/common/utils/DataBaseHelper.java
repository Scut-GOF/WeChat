package gof.scut.common.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBaseHelper extends SQLiteOpenHelper {
    private final static int DBVersion = 1;
    private final static String TABLE_NAME = "contacts";
    private final static String ID = "_id";
    private final static String NAME = "name";
    private final static String L_PINYIN = "l_pinyin";
    private final static String S_PINYIN = "s_pinyin";
    private final static String TEL = "tel";
    private final static String LABEL = "label";
    private final static String ADDRESS = "address";
    private final static String NOTES = "notes";

    private final static String SQL_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME + " VARCHAR(8) NOT NULL,"
            + L_PINYIN + " VARCHAR(24),"
            + S_PINYIN + " VARCHAR(12),"
            + TEL + " VARCHAR(15) NOT NULL,"
            + LABEL + " INT,"
            + ADDRESS + " TEXT,"
            + NOTES + " TEXT";


    public DataBaseHelper(Context context) {
        super(context, TABLE_NAME, null, DBVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //insert
    public long insertAll(String name, String lPinYin, String sPinYin, String tel,
                          String label, String address, String notes) {
        ContentValues value = new ContentValues();
        value.put(NAME, name);
        value.put(L_PINYIN, lPinYin);
        value.put(S_PINYIN, sPinYin);
        value.put(TEL, tel);
        value.put(LABEL, label);
        value.put(ADDRESS, address);
        value.put(NOTES, notes);
        SQLiteDatabase db = this.getWritableDatabase();
        long status = db.insert(TABLE_NAME, null, value);
        db.close();
        return status;
    }

    //delete
    public int deleteWithID(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int status = db.delete(TABLE_NAME, ID + "=?", new String[]{id});
        db.close();
        return status;
    }

    public int deleteWithTel(String tel) {
        SQLiteDatabase db = this.getWritableDatabase();
        int status = db.delete(TABLE_NAME, TEL + "=?", new String[]{tel});
        db.close();
        return status;
    }

    //update
    public void update(String setToSQL, String condition) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_NAME + " SET " + setToSQL + " " + condition);
        db.close();
    }

    //update ALL
    public long updateAllWithID(String name, String lPinYin, String sPinYin, String tel,
                                String label, String address, String notes,
                                String byID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(NAME, name);
        value.put(L_PINYIN, lPinYin);
        value.put(S_PINYIN, sPinYin);
        value.put(TEL, tel);
        value.put(LABEL, label);
        value.put(ADDRESS, address);
        value.put(NOTES, notes);
        long status;
        status = db.update(TABLE_NAME, value, ID + "=?", new String[]{byID});
        db.close();
        return status;
    }

    //update ALL
    public long updateAllWithTel(String name, String lPinYin, String sPinYin, String tel,
                                 String label, String address, String notes,
                                 String byTel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(NAME, name);
        value.put(L_PINYIN, lPinYin);
        value.put(S_PINYIN, sPinYin);
        value.put(TEL, tel);
        value.put(LABEL, label);
        value.put(ADDRESS, address);
        value.put(NOTES, notes);
        long status;
        status = db.update(TABLE_NAME, value, TEL + "=?", new String[]{byTel});
        db.close();
        return status;
    }

    //query
    public Cursor selectWithCondition(String[] columns, String selection, String[] selectionArgs,
                                      String groupBy, String having, String orderBy) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.query(TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
        db.close();
        return c;
    }

    public Cursor selectAllWithID(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.query(TABLE_NAME,
                new String[]{NAME, L_PINYIN, S_PINYIN, TEL, LABEL, ADDRESS, NOTES},
                ID + " LIKE ?", new String[]{id}, null, null, null);
        db.close();
        return c;
    }

    public Cursor selectAllWithTel(String tel) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.query(TABLE_NAME,
                new String[]{NAME, L_PINYIN, S_PINYIN, TEL, LABEL, ADDRESS, NOTES},
                TEL + " LIKE ?", new String[]{tel}, null, null, null);
        db.close();
        return c;
    }

    public Cursor selectAllWithName(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.query(TABLE_NAME,
                new String[]{NAME, L_PINYIN, S_PINYIN, TEL, LABEL, ADDRESS, NOTES},
                NAME + " LIKE ?", new String[]{name}, null, null, null);
        db.close();
        return c;
    }

    public Cursor selectAllWithLabel(String label) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.query(TABLE_NAME,
                new String[]{NAME, L_PINYIN, S_PINYIN, TEL, LABEL, ADDRESS, NOTES},
                LABEL + " = ?", new String[]{label}, null, null, null);
        db.close();
        return c;
    }

    public Cursor selectAllWithAddress(String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.query(TABLE_NAME,
                new String[]{NAME, L_PINYIN, S_PINYIN, TEL, LABEL, ADDRESS, NOTES},
                ADDRESS + " LIKE ?", new String[]{address}, null, null, null);
        db.close();
        return c;
    }

    public Cursor selectAllWithNotes(String notes) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.query(TABLE_NAME,
                new String[]{NAME, L_PINYIN, S_PINYIN, TEL, LABEL, ADDRESS, NOTES},
                NOTES + " LIKE ?", new String[]{notes}, null, null, null);
        db.close();
        return c;
    }
}
