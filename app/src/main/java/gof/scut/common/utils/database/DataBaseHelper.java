package gof.scut.common.utils.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBaseHelper extends SQLiteOpenHelper {
    public final static int DBVersion = 1;

    public final static int ALL_TABLE = 4;


    private final static String DATABASE_NAME = "gofContacts";
    private final static String SQL_MAIN_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "
            + TBMainConstants.TABLE_NAME + " ("
            + TBMainConstants.ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TBMainConstants.NAME + " VARCHAR(8) NOT NULL,"
            + TBMainConstants.L_PINYIN + " VARCHAR(24),"
            + TBMainConstants.S_PINYIN + " VARCHAR(12),"
            // + TBMainConstants.TEL + " VARCHAR(15) NOT NULL,"
            // + TBMainConstants.LABEL + " INT,"
            + TBMainConstants.ADDRESS + " TEXT,"
            + TBMainConstants.NOTES + " TEXT)";
    private final static String SQL_ID_LABEL_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "
            + TBIDLabelConstants.TABLE_NAME + " ("
            + TBIDLabelConstants.ID
            + " INTEGER NOT NULL, "
            + TBIDLabelConstants.LABEL + " VARCHAR(30) NOT NULL,constraint pk_t1 primary key ("
            + TBIDLabelConstants.ID + "," + TBIDLabelConstants.LABEL
            + "))";
    private final static String SQL_TEL_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "
            + TBTelConstants.TABLE_NAME + " ("
            + TBTelConstants.ID
            + " INTEGER NOT NULL, "
            + TBTelConstants.TEL + " INTEGER NOT NULL,constraint pk_t2 primary key ("
            + TBIDLabelConstants.ID + "," + TBTelConstants.TEL
            + "))";
    private final static String SQL_LABEL_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "
            + TBLabelConstants.TABLE_NAME + " ("
            + TBLabelConstants.LABEL + " VARCHAR(30) NOT NULL primary key,"
            + TBLabelConstants.LABEL_ICON + " TEXT NOT NULL,"
            + TBLabelConstants.MEMBER_COUNT + " INTEGER NOT NULL)";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DBVersion);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
//        create table at the first time
//        db.execSQL("drop table "+TBMainConstants.TABLE_NAME);
//        db.execSQL("drop table "+TBTelConstants.TABLE_NAME);
//        db.execSQL("drop table "+TBIDLabelConstants.TABLE_NAME);
        db.execSQL(SQL_MAIN_TABLE_CREATE);
        db.execSQL(SQL_TEL_TABLE_CREATE);
        db.execSQL(SQL_LABEL_TABLE_CREATE);
        db.execSQL(SQL_ID_LABEL_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
