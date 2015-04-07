package gof.scut.common.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBaseHelper extends SQLiteOpenHelper {
    public final static int DBVersion = 1;
    public final static int MAIN_TABLE = 0;
    public final static int LABEL_TABLE = 1;
    public final static int TEL_TABLE = 2;


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
    private final static String SQL_LABEL_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "
            + TBLabelConstants.TABLE_NAME + " ("
            + TBLabelConstants.ID
            + " INTEGER NOT NULL, "
            + TBLabelConstants.LABEL + " INTEGER NOT NULL,constraint pk_t2 primary key ("
            + TBLabelConstants.ID + "," + TBLabelConstants.LABEL
            + "))";

    public DataBaseHelper(Context context) {
        super(context, TBMainConstants.TABLE_NAME, null, DBVersion);
    }

    public DataBaseHelper(Context context, String tableName) {
        super(context, tableName, null, DBVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_MAIN_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
