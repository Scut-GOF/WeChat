package gof.scut.common.utils.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBaseHelper extends SQLiteOpenHelper {
    public final static int DBVersion = 1;
    public final static int MAIN_TABLE = 0;
    public final static int LABEL_TABLE = 1;
    public final static int TEL_TABLE = 2;
    public final static int ALL_TABLE = 3;

    private int handleTable;


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
            + TBLabelConstants.LABEL + " INTEGER NOT NULL,constraint pk_t1 primary key ("
            + TBLabelConstants.ID + "," + TBLabelConstants.LABEL
            + "))";
    private final static String SQL_TEL_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "
            + TBTelConstants.TABLE_NAME + " ("
            + TBTelConstants.ID
            + " INTEGER NOT NULL, "
            + TBTelConstants.TEL + " INTEGER NOT NULL,constraint pk_t2 primary key ("
            + TBLabelConstants.ID + "," + TBTelConstants.TEL
            + "))";

    public DataBaseHelper(Context context) {
        super(context, TBMainConstants.TABLE_NAME, null, DBVersion);
        handleTable = ALL_TABLE;
    }

    public DataBaseHelper(Context context, String tableName) {
        super(context, tableName, null, DBVersion);
        if (tableName.equals(TBMainConstants.TABLE_NAME))
            handleTable = MAIN_TABLE;
        else if (tableName.equals(TBTelConstants.TABLE_NAME))
            handleTable = TEL_TABLE;
        else if (tableName.equals(TBLabelConstants.TABLE_NAME))
            handleTable = LABEL_TABLE;
        else handleTable = ALL_TABLE;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("drop table "+TBMainConstants.TABLE_NAME);
//        db.execSQL("drop table "+TBTelConstants.TABLE_NAME);
//        db.execSQL("drop table "+TBLabelConstants.TABLE_NAME);

        switch (handleTable) {
            //TODO TEST DELETE REMOTE TABLE
            case MAIN_TABLE:
                db.execSQL(SQL_MAIN_TABLE_CREATE);
                break;
            case TEL_TABLE:
                db.execSQL(SQL_TEL_TABLE_CREATE);
                break;
            case LABEL_TABLE:
                db.execSQL(SQL_LABEL_TABLE_CREATE);
                break;
            case ALL_TABLE:
                db.execSQL(SQL_MAIN_TABLE_CREATE);
                db.execSQL(SQL_TEL_TABLE_CREATE);
                db.execSQL(SQL_LABEL_TABLE_CREATE);
                break;
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
