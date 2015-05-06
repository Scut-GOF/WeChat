package gof.scut.common.utils.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import gof.scut.common.utils.StringUtils;


public class IDLabelTableUtils {
	private static DataBaseHelper dataBaseHelper;
	SQLiteDatabase db;

	public IDLabelTableUtils(Context context) {
		dataBaseHelper = new DataBaseHelper(context);
		//insert several data
		/*for (int i=0;i<10;i++){
		    insertAll("Friend"+i,""+i,""+i,"10086",i%3,"","");
        }*/
	}

	//insert
	public long insertAll(String id, String labelName) {
		closeDataBase();
		ContentValues value = new ContentValues();
		value.put(TBIDLabelConstants.ID, id);
		labelName = StringUtils.splitChineseSingly(labelName);
		value.put(TBIDLabelConstants.LABEL, labelName);
		db = dataBaseHelper.getWritableDatabase();

		long status;
		try {
			status = db.insert(TBIDLabelConstants.TABLE_NAME, null, value);
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
			status = db.delete(TBIDLabelConstants.TABLE_NAME,
					TBIDLabelConstants.ID + "=?", new String[]{id});
		} finally {
			db.close();
		}
		return status;
	}

	//delete
	public int deleteWithLabel(String label) {
		closeDataBase();
		db = dataBaseHelper.getWritableDatabase();
		label = StringUtils.splitChineseSingly(label);
		int status;
		try {
			status = db.delete(TBIDLabelConstants.TABLE_NAME,
					TBIDLabelConstants.LABEL + "=?", new String[]{label});
		} finally {
			db.close();
		}
		return status;
	}

	//delete
	public int deleteWithID_Label(String id, String label) {
		closeDataBase();
		db = dataBaseHelper.getWritableDatabase();
		label = StringUtils.splitChineseSingly(label);
		int status;
		try {
			status = db.delete(TBIDLabelConstants.TABLE_NAME,
					TBIDLabelConstants.ID + "=? and "
							+ TBIDLabelConstants.LABEL + "=?", new String[]{id, label});
		} finally {
			db.close();
		}
		return status;
	}


	//update ALL
	public long updateLabelWithID(String label,
	                              String byID) {
		label = StringUtils.splitChineseSingly(label);
		closeDataBase();
		db = dataBaseHelper.getWritableDatabase();
		ContentValues value = new ContentValues();
		value.put(TBIDLabelConstants.LABEL, label);
		value.put(TBIDLabelConstants.ID, byID);

		long status;
		try {
			status = db.update(TBIDLabelConstants.TABLE_NAME, value,
					TBIDLabelConstants.ID + "=?", new String[]{byID});
		} finally {
			db.close();
		}

		return status;
	}

	public long updateLabelWithID_Label(String label,
	                                    String byID, String byLabel) {
		closeDataBase();
		label = StringUtils.splitChineseSingly(label);
		byLabel = StringUtils.splitChineseSingly(byLabel);
		db = dataBaseHelper.getWritableDatabase();
		ContentValues value = new ContentValues();
		value.put(TBIDLabelConstants.LABEL, label);
		value.put(TBIDLabelConstants.ID, byID);

		long status;
		try {
			status = db.update(TBIDLabelConstants.TABLE_NAME, value,
					TBIDLabelConstants.ID + "=? and " + TBIDLabelConstants.LABEL + "=?",
					new String[]{byID, byLabel});
		} finally {
			db.close();
		}

		return status;
	}

	//query
	public List<String> selectLabelWithID(String ID) {
		closeDataBase();
		db = dataBaseHelper.getReadableDatabase();

		Cursor cursorLabels;
		cursorLabels = db.rawQuery("select " + TBIDLabelConstants.LABEL + " from "
						+ TBIDLabelConstants.FTS_TABLE_NAME + " where " + TBIDLabelConstants.ID + " = ?",
				new String[]{ID});
		List<String> labelNames = new ArrayList<>();
		for (int i = 0; i < cursorLabels.getCount(); i++) {
			cursorLabels.moveToPosition(i);
			labelNames.add(StringUtils.recoverWordFromDB(
							cursorLabels.getString(cursorLabels.getColumnIndex(TBIDLabelConstants.LABEL))
					)
			);
		}
		cursorLabels.close();
		closeDataBase();
		return labelNames;
	}

	public void closeDataBase() {
		if (db == null) return;
		if (db.isOpen()) db.close();
	}
}
