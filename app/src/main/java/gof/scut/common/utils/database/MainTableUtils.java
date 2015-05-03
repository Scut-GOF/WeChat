package gof.scut.common.utils.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import gof.scut.common.utils.PinyinUtils;
import gof.scut.common.utils.StringUtils;
import gof.scut.cwh.models.object.IdObj;
import gof.scut.cwh.models.object.LightIdObj;
import gof.scut.cwh.models.object.SearchObj;


public class MainTableUtils {
	private static DataBaseHelper dataBaseHelper;
	SQLiteDatabase db;

	public MainTableUtils(Context context) {
		dataBaseHelper = new DataBaseHelper(context);
		//TODO insert several data
		for (int i = 0; i < 10; i++) {
			insertAll("Barry Allen" + i, "ADDR" + i, "NOTE" + i);
			insertAll("陈伟航" + i, "ADDR" + i, "NOTE" + i);
			insertAll("陈伟航 Cwh" + i, "ADDR" + i, "NOTE" + i);

		}

	}

	//insert
	public long insertAll(String name, String lPinYin, String sPinYin,
	                      String address, String notes) {

		name = StringUtils.splitChineseSingly(name);
		address = StringUtils.splitChineseSingly(address);
		notes = StringUtils.splitChineseSingly(notes);

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

	//insert
	public long insertAll(String name, String address, String notes) {

		String lPinYin = "", sPinYin = "";
		if (StringUtils.containChinese(name)) {
			lPinYin = PinyinUtils.testPurePinYinBlankLy(name);
			//Log.d("PINYIN", lPinYin);
			sPinYin = PinyinUtils.testPureSPinYinBlankLy(name);
			//Log.d("PINYIN", sPinYin);
		}
		name = StringUtils.splitChineseSingly(name);
		address = StringUtils.splitChineseSingly(address);
		notes = StringUtils.splitChineseSingly(notes);


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

		name = StringUtils.splitChineseSingly(name);
		address = StringUtils.splitChineseSingly(address);
		notes = StringUtils.splitChineseSingly(notes);

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

	//TODO SUGGESTION: RETURN LIST FOR QUERY BUT NOT CURSOR,SO YOU CAN CLOSE THE CURSOR AND DB AT ONCE
	//query


	public List<LightIdObj> selectAllIDName() {
		closeDataBase();
		db = dataBaseHelper.getReadableDatabase();
		Cursor cursorContacts;
		cursorContacts = db.rawQuery("select " + TBMainConstants.ID + "," + TBMainConstants.NAME + " from "
				+ TBMainConstants.FTS_TABLE_NAME + " order by " + TBMainConstants.NAME, null);
		List<LightIdObj> contacts = new ArrayList<>();
		for (int i = 0; i < cursorContacts.getCount(); i++) {
			cursorContacts.moveToPosition(i);
			contacts.add(new LightIdObj(
					cursorContacts.getString(cursorContacts.getColumnIndex(TBMainConstants.ID)),
					StringUtils.recoverWordFromDB(
							cursorContacts.getString(cursorContacts.getColumnIndex(TBMainConstants.NAME))
					)
			));
		}
		cursorContacts.close();
		closeDataBase();
		return contacts;
	}


	public IdObj selectAllWithID(String id) {
		closeDataBase();
		db = dataBaseHelper.getReadableDatabase();
		Cursor cursor;
		cursor = db.rawQuery("select * from " + TBMainConstants.FTS_TABLE_NAME + " where "
						+ TBMainConstants.ID + " match ?",
				new String[]{"'" + id + "'"});//  SHOULDN'T BE PUT IN SINGE COBRA
		if (cursor.getCount() == 0) {
			return new IdObj(-1);
		}
		cursor.moveToPosition(0);
		IdObj contact = new IdObj(
				cursor.getInt(cursor.getColumnIndex(TBMainConstants.ID)),
				StringUtils.recoverWordFromDB(cursor.getString(cursor.getColumnIndex(TBMainConstants.NAME))),
				cursor.getString(cursor.getColumnIndex(TBMainConstants.L_PINYIN)),
				cursor.getString(cursor.getColumnIndex(TBMainConstants.S_PINYIN)),
				StringUtils.recoverWordFromDB(cursor.getString(cursor.getColumnIndex(TBMainConstants.ADDRESS))),
				StringUtils.recoverWordFromDB(cursor.getString(cursor.getColumnIndex(TBMainConstants.NOTES)))
		);
		cursor.close();
		closeDataBase();
		//db.close();
		return contact;
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


	synchronized public List<SearchObj> fullTextSearchWithWord(String word) {
		closeDataBase();
		db = dataBaseHelper.getReadableDatabase();
		word = StringUtils.splitNoBlankChineseSingly(word);
		Cursor cursorResult;
//		select * from (
//				select *,'' label,'' tel from fts_contacts
//				where fts_contacts match 'l*' group by _id
//				union
//		select fts_contacts.*,label, '' tel from (
//				(select _id lid,label from fts_id_label where fts_id_label match 'l*')  left join fts_contacts
//		on fts_contacts._id = lid)group by lid
//		) group by _id;
		cursorResult = db.rawQuery("select * from ( "
						+ "select *,'' " + TBIDLabelConstants.LABEL + ",'' " + TBTelConstants.TEL
						+ " from " + TBMainConstants.FTS_TABLE_NAME
						+ " where " + TBMainConstants.FTS_TABLE_NAME
						+ " match ? group by " + TBMainConstants.ID
						+ " union "
						+ "select " + TBMainConstants.FTS_TABLE_NAME + ".*," + TBIDLabelConstants.LABEL + ",'' " + TBTelConstants.TEL
						+ " from (select " + TBIDLabelConstants.ID + " lid," + TBIDLabelConstants.LABEL + " from "
						+ TBIDLabelConstants.FTS_TABLE_NAME + " where " + TBIDLabelConstants.FTS_TABLE_NAME + " match ?) "
						+ "left join " + TBMainConstants.FTS_TABLE_NAME
						+ " on " + TBMainConstants.FTS_TABLE_NAME + "." + TBMainConstants.ID + "= lid group by lid"
						+ " )group by " + TBMainConstants.ID,
				new String[]{"'" + word + "*'", "'" + word + "*'"});
		List<SearchObj> results = new ArrayList<>();
		try {

			for (int i = 0; i < cursorResult.getCount(); i++) {
				cursorResult.moveToPosition(i);
				results.add(new SearchObj(
						cursorResult.getString(cursorResult.getColumnIndex(TBMainConstants.ID)),
						cursorResult.getString(cursorResult.getColumnIndex(TBMainConstants.NAME)),
						cursorResult.getString(cursorResult.getColumnIndex(TBMainConstants.L_PINYIN)),
						cursorResult.getString(cursorResult.getColumnIndex(TBMainConstants.S_PINYIN)),
						StringUtils.recoverWordFromDB(cursorResult.getString(cursorResult.getColumnIndex(TBMainConstants.ADDRESS))),
						StringUtils.recoverWordFromDB(cursorResult.getString(cursorResult.getColumnIndex(TBMainConstants.NOTES))),
						cursorResult.getString(cursorResult.getColumnIndex(TBLabelConstants.LABEL)),
						cursorResult.getString(cursorResult.getColumnIndex(TBTelConstants.TEL))
				));
			}
		} finally {
			CursorUtils.closeExistsCursor(cursorResult);
			closeDataBase();
		}
		//repeated rows
//		c = db.rawQuery("select * from ( "
//				+ TBMainConstants.FTS_TABLE_NAME + " join " + TBIDLabelConstants.FTS_TABLE_NAME
//				+ " on " + TBMainConstants.FTS_TABLE_NAME + "." + TBMainConstants.ID + "="
//				+ TBIDLabelConstants.FTS_TABLE_NAME + "." + TBIDLabelConstants.ID + ")"
//				+ " where " + TBMainConstants.FTS_TABLE_NAME + "." + TBMainConstants.ID + " in ("
//				+" select "+TBMainConstants.ID+" from "+TBMainConstants.FTS_TABLE_NAME
//				+" where "+TBMainConstants.FTS_TABLE_NAME+" match ? "
//				+" union "
//				+" select "+TBIDLabelConstants.ID+" from "+TBIDLabelConstants.FTS_TABLE_NAME
//				+" where "+TBIDLabelConstants.FTS_TABLE_NAME+" match ? "
//				+" union "
//				+" select "+TBTelConstants.ID+" from "+TBTelConstants.FTS_TABLE_NAME
//				+" where "+TBTelConstants.FTS_TABLE_NAME+" match ? "
//				+") order by "+TBMainConstants.NAME
//				, new String[]{"'" + word + "*'", "'" + word + "*'", "'" + word + "*'"});
//		//select * from contacts left outer join id_label
//		c = db.rawQuery("select * from (("
//				+" select * from "+TBMainConstants.FTS_TABLE_NAME
//				+" where "
//				+ TBMainConstants.FTS_TABLE_NAME + " match ? )"
//				+" left join (select "+TBIDLabelConstants.ID+" as LID,"
//				+TBIDLabelConstants.LABEL+" from "
//				+ TBIDLabelConstants.FTS_TABLE_NAME
//				+ " where "
//				+ TBIDLabelConstants.FTS_TABLE_NAME + " match ?) on "
//				+TBMainConstants.ID+"= LID) group by "+TBMainConstants.ID
//				, new String[]{"'" + word + "*'","'" + word + "*'"});
		//in one query, match can be used only one time,so union is needed
//		c = db.rawQuery("select * from "
//				+ TBMainConstants.FTS_TABLE_NAME + " inner join " + TBIDLabelConstants.FTS_TABLE_NAME
//				+ " on "
//				+ TBMainConstants.FTS_TABLE_NAME + "." + TBMainConstants.ID
//				+ "=" + TBIDLabelConstants.FTS_TABLE_NAME + "." + TBMainConstants.ID
//				+ " where "
//				+ TBMainConstants.FTS_TABLE_NAME + " match ? or " + TBIDLabelConstants.FTS_TABLE_NAME + " match ?"
//				, new String[]{"'*" + word + "*'", "'*" + word + "*'"});
		return results;
	}

	synchronized public List<SearchObj> fullTextSearchWithNumOrWord(String word) {
		closeDataBase();
		db = dataBaseHelper.getReadableDatabase();
		Cursor cursorResult;
		word = StringUtils.splitNoBlankChineseSingly(word);
//		c=db.rawQuery("select * from contacts",null);
//		c=db.rawQuery("select * from label",null);
//		c=db.rawQuery("select * from idlabel",null);
//		c=db.rawQuery("select * from tel",null);
		cursorResult = db.rawQuery("select * from ( "
				+ "select *,'' " + TBIDLabelConstants.LABEL + ",'' " + TBTelConstants.TEL
				+ " from " + TBMainConstants.FTS_TABLE_NAME
				+ " where " + TBMainConstants.FTS_TABLE_NAME
				+ " match ? group by " + TBMainConstants.ID
				+ " union "
				+ "select " + TBMainConstants.FTS_TABLE_NAME + ".*," + TBIDLabelConstants.LABEL + ",'' " + TBTelConstants.TEL
				+ " from (select " + TBIDLabelConstants.ID + " lid, " + TBIDLabelConstants.LABEL + " from "
				+ TBIDLabelConstants.FTS_TABLE_NAME + " where " + TBIDLabelConstants.FTS_TABLE_NAME + " match ?) "
				+ "left join " + TBMainConstants.FTS_TABLE_NAME
				+ " on " + TBMainConstants.FTS_TABLE_NAME + "." + TBMainConstants.ID + "= lid"
				+ " group by lid"
				+ " union "
				+ "select " + TBMainConstants.FTS_TABLE_NAME + ".*,'' " + TBIDLabelConstants.LABEL + ", " + TBTelConstants.TEL
				+ " from (select " + TBTelConstants.ID + " tid," + TBTelConstants.TEL + " from "
				+ TBTelConstants.FTS_TABLE_NAME + " where " + TBTelConstants.FTS_TABLE_NAME + " match ?) "
				+ "left join " + TBMainConstants.FTS_TABLE_NAME
				+ " on " + TBMainConstants.FTS_TABLE_NAME + "." + TBMainConstants.ID + "= tid"
				+ " group by tid)"
				+ " group by " + TBMainConstants.ID, new String[]{"'" + word + "*'", "'" + word + "*'", "'" + word + "*'"});
		List<SearchObj> results = new ArrayList<>();
		try {

			for (int i = 0; i < cursorResult.getCount(); i++) {
				cursorResult.moveToPosition(i);
				results.add(new SearchObj(
						cursorResult.getString(cursorResult.getColumnIndex(TBMainConstants.ID)),
						cursorResult.getString(cursorResult.getColumnIndex(TBMainConstants.NAME)),
						cursorResult.getString(cursorResult.getColumnIndex(TBMainConstants.L_PINYIN)),
						cursorResult.getString(cursorResult.getColumnIndex(TBMainConstants.S_PINYIN)),
						StringUtils.recoverWordFromDB(cursorResult.getString(cursorResult.getColumnIndex(TBMainConstants.ADDRESS))),
						StringUtils.recoverWordFromDB(cursorResult.getString(cursorResult.getColumnIndex(TBMainConstants.NOTES))),
						cursorResult.getString(cursorResult.getColumnIndex(TBLabelConstants.LABEL)),
						cursorResult.getString(cursorResult.getColumnIndex(TBTelConstants.TEL))
				));
			}
		} finally {
			CursorUtils.closeExistsCursor(cursorResult);
			closeDataBase();
		}
//		c = db.rawQuery("select * from ((("
//				+ " select * from " + TBMainConstants.FTS_TABLE_NAME
//				+ " where "
//				+ TBMainConstants.FTS_TABLE_NAME + " match ? )"
//				+ " left join (select " + TBIDLabelConstants.ID + " as LID,"
//				+ TBIDLabelConstants.LABEL + " from "
//				+ TBIDLabelConstants.FTS_TABLE_NAME
//				+ " where "
//				+ TBIDLabelConstants.FTS_TABLE_NAME + " match ?) on "
//				+ TBMainConstants.ID + "= LID) left join (select " + TBTelConstants.ID + " as TID,"
//				+ TBTelConstants.TEL + " from "
//				+ TBTelConstants.FTS_TABLE_NAME
//				+ " where "
//				+ TBTelConstants.FTS_TABLE_NAME + " match ?) on "
//				+ TBMainConstants.ID + "= TID) group by " + TBMainConstants.ID
//				, new String[]{"'" + word + "*'", "'" + word + "*'", "'" + word + "*'"});
		return results;
	}

	public void closeDataBase() {
		if (db == null) return;
		if (db.isOpen()) db.close();
	}
}
