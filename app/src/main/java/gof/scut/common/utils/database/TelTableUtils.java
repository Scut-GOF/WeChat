package gof.scut.common.utils.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class TelTableUtils {
	private static DataBaseHelper dataBaseHelper;

	public TelTableUtils(Context context) {
		dataBaseHelper = new DataBaseHelper(context);

	}

	//insert
	public long insertAll(String id, String tel) {
		ContentValues value = new ContentValues();
		value.put(TBTelConstants.ID, id);
		value.put(TBTelConstants.TEL, tel);
		SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
		long status = db.insert(TBTelConstants.TABLE_NAME, null, value);
		db.close();
		return status;
	}

	//delete
	public int deleteWithID(String id) {
		SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
		int status = db.delete(TBTelConstants.TABLE_NAME,
				TBTelConstants.ID + "=?", new String[]{id});
		db.close();
		return status;
	}

	//delete
	public int deleteWithTel(String tel) {
		SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
		int status = db.delete(TBTelConstants.TABLE_NAME,
				TBTelConstants.TEL + "=?", new String[]{tel});
		db.close();
		return status;
	}

	//delete
	public int deleteWithID_Tel(String id, String tel) {
		SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
		int status = db.delete(TBTelConstants.TABLE_NAME,
				TBTelConstants.ID + "=? and " + TBTelConstants.TEL + "=?", new String[]{id, tel});
		db.close();
		return status;
	}


	//update ALL
	public long updateTelWithID(String tel,
	                            String byID) {
		SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
		ContentValues value = new ContentValues();
		value.put(TBTelConstants.TEL, tel);
		value.put(TBTelConstants.ID, byID);

		long status;
		status = db.update(TBTelConstants.TABLE_NAME, value,
				TBTelConstants.ID + "=?", new String[]{byID});
		db.close();
		return status;
	}

	//update ALL
	public long updateWithID_Tel(String tel,
	                             String id) {
		SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
		ContentValues value = new ContentValues();
		value.put(TBTelConstants.TEL, tel);
		value.put(TBTelConstants.ID, id);

		long status;
		status = db.update(TBTelConstants.TABLE_NAME, value,
				TBTelConstants.ID + "=? and " + TBTelConstants.TEL + "=?", new String[]{id, tel});
		db.close();
		return status;
	}

	//query
	public Cursor selectTelWithID(String ID) {
		SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
		Cursor c = db.query(TBTelConstants.TABLE_NAME, new String[]{TBTelConstants.TEL},
				TBTelConstants.ID + " = ?", new String[]{ID}, null, null, null);
		//db.close();
		return c;
	}

	public Cursor selectIDWithTel(String tel) {
		SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
		Cursor c = db.query(TBTelConstants.TABLE_NAME, new String[]{TBTelConstants.ID},
				TBTelConstants.TEL + " = ?", new String[]{tel}, null, null, null);
		//db.close();
		return c;
	}

}
