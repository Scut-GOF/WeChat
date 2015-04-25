package gof.scut.common.utils.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class MainTableUtils {
	private static DataBaseHelper dataBaseHelper;
	SQLiteDatabase db;

	public MainTableUtils(Context context) {
		dataBaseHelper = new DataBaseHelper(context);
		//TODO insert several data
		for (int i = 0; i < 10; i++) {
			insertAll("Friend" + i, "" + i, "" + i, "", "");
		}
		deleteWithID(9 + "");
		updateAllWithID("Friend" + 9, "9", "9", "", "", "8");
	}

	//insert
	public long insertAll(String name, String lPinYin, String sPinYin,
	                      String address, String notes) {
		closeDataBase();
		ContentValues value = new ContentValues();
		value.put(TBMainConstants.NAME, name);
		value.put(TBMainConstants.L_PINYIN, lPinYin);
		value.put(TBMainConstants.S_PINYIN, sPinYin);
		value.put(TBMainConstants.ADDRESS, address);
		value.put(TBMainConstants.NOTES, notes);
		db = dataBaseHelper.getWritableDatabase();
		long status;
		try {
			status = db.insert(TBMainConstants.TABLE_NAME, null, value);
		} finally {
			db.close();
		}
//		value.put(TBMainConstants.ID, notes);
//		db = dataBaseHelper.getWritableDatabase();
//		status += db.insert(TBMainConstants.FTS_TABLE_NAME, null, value);
//		db.close();
		return status;
	}

	//delete
	public int deleteWithID(String id) {
		closeDataBase();
		db = dataBaseHelper.getWritableDatabase();
		int status;
		try {
			status = db.delete(TBMainConstants.TABLE_NAME,
					TBMainConstants.ID + "=?", new String[]{id});
		} finally {
			db.close();
		}
		return status;
	}


	//update
	public void update(String setToSQL, String condition) {
		closeDataBase();
		db = dataBaseHelper.getWritableDatabase();
		db.execSQL("UPDATE " + TBMainConstants.TABLE_NAME
				+ " SET " + setToSQL + " " + condition + " ;");
		db.close();
	}

	//update ALL
	public long updateAllWithID(String name, String lPinYin, String sPinYin,
	                            String address, String notes,
	                            String byID) {
		closeDataBase();
		db = dataBaseHelper.getWritableDatabase();
		ContentValues value = new ContentValues();
		value.put(TBMainConstants.NAME, name);
		value.put(TBMainConstants.L_PINYIN, lPinYin);
		value.put(TBMainConstants.S_PINYIN, sPinYin);
		value.put(TBMainConstants.ADDRESS, address);
		value.put(TBMainConstants.NOTES, notes);
		long status;
		try {
			status = db.update(TBMainConstants.TABLE_NAME, value,
					TBMainConstants.ID + "=?", new String[]{byID});
		} finally {
			db.close();
		}
		return status;
	}


	//query
	public Cursor selectAll() {
		closeDataBase();
		db = dataBaseHelper.getReadableDatabase();
		Cursor c = db.query(TBMainConstants.FTS_TABLE_NAME, null, null, null, null, null, null);
		//db.close();
		return c;
	}

	public Cursor selectAllIDName() {
		closeDataBase();
		db = dataBaseHelper.getReadableDatabase();
		Cursor c;
//		c = db.query(TBMainConstants.TABLE_NAME, new String[]{TBMainConstants.ID, TBMainConstants.NAME},
//				null, null, null, null, TBMainConstants.NAME);
		//db.close();
		c = db.rawQuery("select " + TBMainConstants.ID + "," + TBMainConstants.NAME + " from "
				+ TBMainConstants.FTS_TABLE_NAME, null);
//		c = db.query(TBMainConstants.FTS_TABLE_NAME, new String[]{TBMainConstants.ID, TBMainConstants.NAME},
//				null, null, null, null, TBMainConstants.FTS_TABLE_NAME);
		return c;
	}


	public Cursor selectAllName() {
		closeDataBase();
		db = dataBaseHelper.getReadableDatabase();
		Cursor c = db.query(TBMainConstants.FTS_TABLE_NAME, new String[]{TBMainConstants.NAME},
				null, null, null, null, TBMainConstants.NAME);
		//db.close();
		return c;
	}

	public Cursor selectWithCondition(String[] columns, String selection, String[] selectionArgs,
	                                  String groupBy, String having, String orderBy) {
		closeDataBase();
		db = dataBaseHelper.getReadableDatabase();
		Cursor c = db.query(TBMainConstants.FTS_TABLE_NAME, columns, selection,
				selectionArgs, groupBy, having, orderBy);
		//db.close();
		return c;
	}

	public Cursor selectAllWithID(String id) {
		closeDataBase();
		db = dataBaseHelper.getReadableDatabase();
		Cursor c;
//		c = db.query(TBMainConstants.TABLE_NAME,
//				new String[]{TBMainConstants.NAME, TBMainConstants.L_PINYIN,
//						TBMainConstants.S_PINYIN, TBMainConstants.ADDRESS, TBMainConstants.NOTES},
//				TBMainConstants.ID + " LIKE ?", new String[]{id}, null, null, null);

		c = db.rawQuery("select * from " + TBMainConstants.FTS_TABLE_NAME + " where "
						+ TBMainConstants.ID + " match ?",
				new String[]{"'" + id + "'"});//  SHOULDN'T BE PUT IN SINGE COBRA
		//db.close();
		return c;
	}


	public Cursor selectAllWithName(String name) {
		closeDataBase();
		db = dataBaseHelper.getReadableDatabase();
		Cursor c;
		c = db.rawQuery("select * from " + TBMainConstants.FTS_TABLE_NAME + " where "
						+ TBMainConstants.NAME + " match ?",
				new String[]{"'" + name + "'"});
//		c = db.query(TBMainConstants.TABLE_NAME,
//				new String[]{TBMainConstants.NAME, TBMainConstants.L_PINYIN,
//						TBMainConstants.S_PINYIN, TBMainConstants.ADDRESS, TBMainConstants.NOTES},
//				TBMainConstants.NAME + " LIKE ?", new String[]{name}, null, null, null);
		//db.close();
		return c;
	}


	public Cursor selectAllWithAddress(String address) {
		closeDataBase();
		db = dataBaseHelper.getReadableDatabase();
		Cursor c;
		c = db.rawQuery("select * from " + TBMainConstants.FTS_TABLE_NAME + " where "
						+ TBMainConstants.ADDRESS + " match ?",
				new String[]{"'" + address + "'"});
//		c= db.query(TBMainConstants.TABLE_NAME,
//				new String[]{TBMainConstants.NAME, TBMainConstants.L_PINYIN,
//						TBMainConstants.S_PINYIN, TBMainConstants.ADDRESS, TBMainConstants.NOTES},
//				TBMainConstants.ADDRESS + " LIKE ?", new String[]{address}, null, null, null);
		//db.close();
		return c;
	}

	public Cursor selectAllWithNotes(String notes) {
		closeDataBase();
		db = dataBaseHelper.getReadableDatabase();
		Cursor c;
		c = db.rawQuery("select * from " + TBMainConstants.FTS_TABLE_NAME + " where "
						+ TBMainConstants.NOTES + " match ?",
				new String[]{"'" + notes + "'"});
//		c = db.query(TBMainConstants.TABLE_NAME,
//				new String[]{TBMainConstants.NAME, TBMainConstants.L_PINYIN,
//						TBMainConstants.S_PINYIN, TBMainConstants.ADDRESS, TBMainConstants.NOTES},
//				TBMainConstants.NOTES + " LIKE ?", new String[]{notes}, null, null, null);
		//db.close();
		return c;
	}

	public Cursor fullTextSearchWithWord(String word) {
		closeDataBase();
		db = dataBaseHelper.getReadableDatabase();
		Cursor c;
		//docid,heightLight
		c = db.rawQuery(null, null);
		return c;
	}

	public void closeDataBase() {
		if (db == null) return;
		if (db.isOpen()) db.close();
	}
}
