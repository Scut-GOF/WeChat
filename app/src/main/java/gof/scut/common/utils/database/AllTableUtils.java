package gof.scut.common.utils.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Administrator on 2015/4/17.
 */
public class AllTableUtils {
	private static DataBaseHelper dataBaseHelper;
	SQLiteDatabase db;
	public AllTableUtils(Context context) {
		dataBaseHelper = new DataBaseHelper(context);
		//insert several data
		/*for (int i=0;i<10;i++){
            insertAll("Friend"+i,""+i,""+i,"10086",i%3,"","");
        }*/
	}

	public Cursor selectAllIDNameOnLabel(String labelName) {
		closeDataBase();
		db = dataBaseHelper.getReadableDatabase();
//		Cursor c1 = db.rawQuery("SELECT  _id,name FROM contacts WHERE _id IN ( SELECT _id FROM idLabel WHERE label = '" + labelName + "')", null);
//		Cursor c2 = db.rawQuery("SELECT _id FROM idLabel WHERE label = '" + labelName + "'", null);
//		Cursor c3 = db.rawQuery("SELECT _id FROM fts_id_label WHERE label = '" + labelName + "'", null);
//		Cursor c4 = db.rawQuery("SELECT _id FROM fts_id_label ", null);

		//SELECT  _id,name FROM contacts WHERE _id IN ( SELECT _id FROM idLabel WHERE label = '" + labelName + "')"
		Cursor c = db.rawQuery("SELECT " + TBMainConstants.ID + "," + TBMainConstants.NAME
				+ " FROM " + TBMainConstants.FTS_TABLE_NAME + " WHERE " + TBMainConstants.ID
				+ " IN ( SELECT " + TBMainConstants.ID + " FROM " + TBIDLabelConstants.FTS_TABLE_NAME
				+ " WHERE " + TBIDLabelConstants.LABEL + " match ? )", new String[]{"'" + labelName + "'"});

		return c;
	}

	public void closeDataBase() {
		if (db == null) return;
		if (db.isOpen()) db.close();
	}
}
