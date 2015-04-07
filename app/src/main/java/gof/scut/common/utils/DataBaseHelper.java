package gof.scut.common.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBaseHelper extends SQLiteOpenHelper {
    public final static int DBVersion = 1;


    private final static String SQL_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "
            + DBConstants.TABLE_NAME + " ("
            + DBConstants.ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DBConstants.NAME + " VARCHAR(8) NOT NULL,"
            + DBConstants.L_PINYIN + " VARCHAR(24),"
            + DBConstants.S_PINYIN + " VARCHAR(12),"
            + DBConstants.TEL + " VARCHAR(15) NOT NULL,"
            + DBConstants.LABEL + " INT,"
            + DBConstants.ADDRESS + " TEXT,"
            + DBConstants.NOTES + " TEXT)";


    public DataBaseHelper(Context context) {
        super(context, DBConstants.TABLE_NAME, null, DBVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
