package gof.scut.common.utils.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class MainTableUtils {
	private static DataBaseHelper dataBaseHelper;

	public MainTableUtils(Context context) {
		dataBaseHelper = new DataBaseHelper(context);
		//TODO insert several data
		for (int i = 0; i < 100; i++) {
			insertAll("Friend" + i, "" + i, "" + i, "", "");
		}

	}

	//insert
	public long insertAll(String name, String lPinYin, String sPinYin,
	                      String address, String notes) {
		ContentValues value = new ContentValues();
		value.put(TBMainConstants.NAME, name);
		value.put(TBMainConstants.L_PINYIN, lPinYin);
		value.put(TBMainConstants.S_PINYIN, sPinYin);
		value.put(TBMainConstants.ADDRESS, address);
		value.put(TBMainConstants.NOTES, notes);
		SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
		long status = db.insert(TBMainConstants.TABLE_NAME, null, value);
		db.close();
		return status;
	}

	//delete
	public int deleteWithID(String id) {
		SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
		int status = db.delete(TBMainConstants.TABLE_NAME,
				TBMainConstants.ID + "=?", new String[]{id});
		db.close();
		return status;
	}


	//update
	public void update(String setToSQL, String condition) {
		SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
		db.execSQL("UPDATE " + TBMainConstants.TABLE_NAME
				+ " SET " + setToSQL + " " + condition + " ;");
		db.close();
	}

	//update ALL
	public long updateAllWithID(String name, String lPinYin, String sPinYin,
	                            String address, String notes,
	                            String byID) {
		SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
		ContentValues value = new ContentValues();
		value.put(TBMainConstants.NAME, name);
		value.put(TBMainConstants.L_PINYIN, lPinYin);
		value.put(TBMainConstants.S_PINYIN, sPinYin);
		value.put(TBMainConstants.ADDRESS, address);
		value.put(TBMainConstants.NOTES, notes);
		long status;
		status = db.update(TBMainConstants.TABLE_NAME, value,
				TBMainConstants.ID + "=?", new String[]{byID});
		db.close();
		return status;
	}


	//query
	public Cursor selectAll() {
		SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
		Cursor c = db.query(TBMainConstants.TABLE_NAME, null, null, null, null, null, null);
		//db.close();
		return c;
	}

	public Cursor selectAllIDName() {
		SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
		Cursor c = db.query(TBMainConstants.TABLE_NAME, new String[]{TBMainConstants.ID, TBMainConstants.NAME},
				null, null, null, null, TBMainConstants.NAME);
		//db.close();
		return c;
	}


	public Cursor selectAllName() {
		SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
		Cursor c = db.query(TBMainConstants.TABLE_NAME, new String[]{TBMainConstants.NAME},
				null, null, null, null, TBMainConstants.NAME);
		//db.close();
		return c;
	}

	public Cursor selectWithCondition(String[] columns, String selection, String[] selectionArgs,
	                                  String groupBy, String having, String orderBy) {
		SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
		Cursor c = db.query(TBMainConstants.TABLE_NAME, columns, selection,
				selectionArgs, groupBy, having, orderBy);
		//db.close();
		return c;
	}

	public Cursor selectAllWithID(String id) {
		SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
		Cursor c = db.query(TBMainConstants.TABLE_NAME,
				new String[]{TBMainConstants.NAME, TBMainConstants.L_PINYIN,
						TBMainConstants.S_PINYIN, TBMainConstants.ADDRESS, TBMainConstants.NOTES},
				TBMainConstants.ID + " LIKE ?", new String[]{id}, null, null, null);
		//db.close();
		return c;
	}


	public Cursor selectAllWithName(String name) {
		SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
		Cursor c = db.query(TBMainConstants.TABLE_NAME,
				new String[]{TBMainConstants.NAME, TBMainConstants.L_PINYIN,
						TBMainConstants.S_PINYIN, TBMainConstants.ADDRESS, TBMainConstants.NOTES},
				TBMainConstants.NAME + " LIKE ?", new String[]{name}, null, null, null);
		//db.close();
		return c;
	}


	public Cursor selectAllWithAddress(String address) {
		SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
		Cursor c = db.query(TBMainConstants.TABLE_NAME,
				new String[]{TBMainConstants.NAME, TBMainConstants.L_PINYIN,
						TBMainConstants.S_PINYIN, TBMainConstants.ADDRESS, TBMainConstants.NOTES},
				TBMainConstants.ADDRESS + " LIKE ?", new String[]{address}, null, null, null);
		//db.close();
		return c;
	}

	public Cursor selectAllWithNotes(String notes) {
		SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
		Cursor c = db.query(TBMainConstants.TABLE_NAME,
				new String[]{TBMainConstants.NAME, TBMainConstants.L_PINYIN,
						TBMainConstants.S_PINYIN, TBMainConstants.ADDRESS, TBMainConstants.NOTES},
				TBMainConstants.NOTES + " LIKE ?", new String[]{notes}, null, null, null);
		//db.close();
		return c;
	}
}
