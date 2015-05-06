package gof.scut.common.utils.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import gof.scut.common.utils.StringUtils;


public class TelTableUtils {
	private static DataBaseHelper dataBaseHelper;
	SQLiteDatabase db;

	public TelTableUtils(Context context) {
		dataBaseHelper = new DataBaseHelper(context);

	}

	//insert
	public long insertAll(String id, String tel) {
		closeDataBase();
		ContentValues value = new ContentValues();
		value.put(TBTelConstants.ID, id);
		value.put(TBTelConstants.TEL, tel);
		db = dataBaseHelper.getWritableDatabase();
		long status;
		try {
			status = db.insert(TBTelConstants.TABLE_NAME, null, value);
		} finally {
			db.close();
		}

		return status;
	}

	//delete
	public int deleteWithID(String id) {
		closeDataBase();
		db = dataBaseHelper.getWritableDatabase();
		int status;
		try {
			status = db.delete(TBTelConstants.TABLE_NAME,
					TBTelConstants.ID + "=?", new String[]{id});
		} finally {
			db.close();
		}


		return status;
	}

	//delete
	public int deleteWithTel(String tel) {
		closeDataBase();
		db = dataBaseHelper.getWritableDatabase();
		int status;
		try {
			status = db.delete(TBTelConstants.TABLE_NAME,
					TBTelConstants.TEL + "=?", new String[]{tel});
		} finally {
			db.close();
		}


		return status;
	}

	//delete
	public int deleteWithID_Tel(String id, String tel) {
		closeDataBase();
		db = dataBaseHelper.getWritableDatabase();
		int status;
		try {
			status = db.delete(TBTelConstants.TABLE_NAME,
					TBTelConstants.ID + "=? and " + TBTelConstants.TEL + "=?", new String[]{id, tel});
		} finally {
			db.close();
		}


		return status;
	}


	//update ALL
	public long updateTelWithID(String tel,
	                            String byID) {
		closeDataBase();
		db = dataBaseHelper.getWritableDatabase();
		ContentValues value = new ContentValues();
		value.put(TBTelConstants.TEL, tel);
		value.put(TBTelConstants.ID, byID);

		long status;
		try {
			status = db.update(TBTelConstants.TABLE_NAME, value,
					TBTelConstants.ID + "=?", new String[]{byID});
		} finally {
			db.close();
		}
		return status;
	}

	//update ALL
	public long updateWithID_Tel(String tel,
	                             String id) {
		closeDataBase();
		db = dataBaseHelper.getWritableDatabase();
		ContentValues value = new ContentValues();
		value.put(TBTelConstants.TEL, tel);
		value.put(TBTelConstants.ID, id);

		long status;
		try {
			status = db.update(TBTelConstants.TABLE_NAME, value,
					TBTelConstants.ID + "=? and " + TBTelConstants.TEL + "=?", new String[]{id, tel});
		} finally {
			db.close();
		}
		return status;
	}

    //query
    public Cursor selectTelWithID(String ID) {
        closeDataBase();
        db = dataBaseHelper.getReadableDatabase();
        Cursor c;
        c = db.rawQuery("select " + TBTelConstants.TEL + " from "
                        + TBTelConstants.FTS_TABLE_NAME + " where " + TBTelConstants.ID + " match ?",
                new String[]{"'" + ID + "'"});
//		c = db.query(TBTelConstants.TABLE_NAME, new String[]{TBTelConstants.TEL},
//				TBTelConstants.ID + " = ?", new String[]{ID}, null, null, null);
        //db.close();
        return c;
    }

	//query
	public List<String> selectTelListWithID(String ID) {
		closeDataBase();
		db = dataBaseHelper.getReadableDatabase();
		Cursor c;
		c = db.rawQuery("select " + TBTelConstants.TEL + " from "
						+ TBTelConstants.FTS_TABLE_NAME + " where " + TBTelConstants.ID + " match ?",
				new String[]{"'" + ID + "'"});

        List<String> phones = new ArrayList<>();
        for (int i = 0; i < c.getCount(); i++) {
            c.moveToPosition(i);
            phones.add(StringUtils.recoverWordFromDB(
                            c.getString(c.getColumnIndex(TBTelConstants.TEL))
                    )
            );
        }
        c.close();
        closeDataBase();
        return phones;
	}

//	public Cursor selectIDWithTel(String tel) {
//		closeDataBase();
//		db = dataBaseHelper.getReadableDatabase();
//		Cursor c;
//		c = db.rawQuery("select " + TBTelConstants.ID + " from "
//						+ TBTelConstants.FTS_TABLE_NAME + " where " + TBTelConstants.TEL + " match ?",
//				new String[]{"'" + tel + "'"});
////		c = db.query(TBTelConstants.TABLE_NAME, new String[]{TBTelConstants.ID},
////				TBTelConstants.TEL + " = ?", new String[]{tel}, null, null, null);
//		//db.close();
//		return c;
//	}

	public void closeDataBase() {
		if (db == null) return;
		if (db.isOpen()) db.close();
	}
}
