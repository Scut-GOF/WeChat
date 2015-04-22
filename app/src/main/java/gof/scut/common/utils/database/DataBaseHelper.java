package gof.scut.common.utils.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBaseHelper extends SQLiteOpenHelper {
	public final static int DBVersion = 1;

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
			+ TBIDLabelConstants.LABEL + " INTEGER NOT NULL,constraint pk_t1 primary key ("
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
	//    CREATE TRIGGER addmembercount after insert on id_label begin update label set count = count+1 where label.label = new.label;
	//    end;
	private final static String SQL_TRIGGER_ADD_MEMBER = "CREATE TRIGGER addMemberCount AFTER INSERT ON" +
			TBIDLabelConstants.TABLE_NAME +
			" BEGIN UPDATE " + TBLabelConstants.TABLE_NAME + " SET " + TBLabelConstants.MEMBER_COUNT + " = "
			+ TBLabelConstants.MEMBER_COUNT + "+1 WHERE " + TBLabelConstants.TABLE_NAME + "."
			+ TBLabelConstants.LABEL + " = NEW." + TBIDLabelConstants.LABEL + ";END";

	//    CREATE TRIGGER updatelabelname after update of label on label begin update id_label set label = new.label where label = old.label;end;
//    COMMIT;
	private final static String SQL_TRIGGER_UPDATE_LABEL = "CREATE TRIGGER updateLabelName AFTER UPDATE of " +
			TBLabelConstants.LABEL + " ON " +
			TBLabelConstants.TABLE_NAME +
			" BEGIN UPDATE " + TBIDLabelConstants.TABLE_NAME + " SET " + TBIDLabelConstants.LABEL + " = "
			+ "NEW." + TBIDLabelConstants.LABEL + " WHERE " + TBIDLabelConstants.LABEL + " = OLD."
			+ TBIDLabelConstants.LABEL + ";END";

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
		db.execSQL(SQL_TRIGGER_ADD_MEMBER);
		db.execSQL(SQL_TRIGGER_UPDATE_LABEL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
