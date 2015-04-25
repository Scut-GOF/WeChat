package gof.scut.common.utils.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import gof.scut.cwh.models.object.LabelObj;


public class LabelTableUtils {
	private static DataBaseHelper dataBaseHelper;
	SQLiteDatabase db;

	public LabelTableUtils(Context context) {
		dataBaseHelper = new DataBaseHelper(context);
		//insert several data
		/*for (int i=0;i<10;i++){
		    insertAll("Friend"+i,""+i,""+i,"10086",i%3,"","");
        }*/
	}

	//insert
	public long insertAll(String label, String iconPath) {
		closeDataBase();
		ContentValues value = new ContentValues();
		value.put(TBLabelConstants.LABEL, label);
		value.put(TBLabelConstants.LABEL_ICON, iconPath);
		value.put(TBLabelConstants.MEMBER_COUNT, 0 + "");
		db = dataBaseHelper.getWritableDatabase();
		long status;
		try {
			status = db.insert(TBLabelConstants.TABLE_NAME, null, value);
		} finally {
			db.close();
		}
		return status;
	}

	public long insertAll(LabelObj labelObj) {
		closeDataBase();
		ContentValues value = new ContentValues();
		value.put(TBLabelConstants.LABEL, labelObj.getLabelName());
		value.put(TBLabelConstants.LABEL_ICON, labelObj.getIconPath());
		value.put(TBLabelConstants.MEMBER_COUNT, 0 + "");
		db = dataBaseHelper.getWritableDatabase();
		long status;
		try {
			status = db.insert(TBLabelConstants.TABLE_NAME, null, value);
		} finally {
			db.close();
		}

		return status;
	}

	//delete
	public int deleteWithLabel(String label) {
		closeDataBase();
		db = dataBaseHelper.getWritableDatabase();
		int status;
		try {
			status = db.delete(TBLabelConstants.TABLE_NAME,
					TBLabelConstants.LABEL + "=?", new String[]{label});
		} finally {
			db.close();
		}

		return status;
	}

	public long updateAllWithLabel(LabelObj labelObj, String onLabel) {
		closeDataBase();
		db = dataBaseHelper.getWritableDatabase();
		ContentValues value = new ContentValues();
		value.put(TBLabelConstants.LABEL, labelObj.getLabelName());
		value.put(TBLabelConstants.LABEL_ICON, labelObj.getIconPath());
		value.put(TBLabelConstants.MEMBER_COUNT, labelObj.getMemCount());
		long status;
		try {
			status = db.update(TBLabelConstants.TABLE_NAME, value,
					TBLabelConstants.LABEL + "=?", new String[]{onLabel});
		} finally {
			db.close();
		}

		return status;
	}

	//update ALL
	public long updateAllWithLabel(String label, String iconPath, String memCount,
	                               String onLabel) {
		closeDataBase();
		db = dataBaseHelper.getWritableDatabase();
		ContentValues value = new ContentValues();
		value.put(TBLabelConstants.LABEL, label);
		value.put(TBLabelConstants.LABEL_ICON, iconPath);
		value.put(TBLabelConstants.MEMBER_COUNT, memCount);
		long status;
		try {
			status = db.update(TBLabelConstants.TABLE_NAME, value,
					TBLabelConstants.LABEL + "=?", new String[]{onLabel});
		} finally {
			db.close();
		}


		return status;
	}

	public long updateMemCountWithLabel(String memCount,
	                                    String onLabel) {
		closeDataBase();
		db = dataBaseHelper.getWritableDatabase();
		ContentValues value = new ContentValues();
		value.put(TBLabelConstants.LABEL, memCount);
		long status;
		try {
			status = db.update(TBLabelConstants.TABLE_NAME, value,
					TBLabelConstants.LABEL + "=?", new String[]{onLabel});
		} finally {
			db.close();
		}


		return status;
	}

	public void addMemCountByName(int addNum, String byName) {
		closeDataBase();
		db = dataBaseHelper.getWritableDatabase();
		db.execSQL("update " + TBLabelConstants.TABLE_NAME + " set " + TBLabelConstants.MEMBER_COUNT
				+ " = " + TBLabelConstants.MEMBER_COUNT + "+" + addNum +
				" where " + TBLabelConstants.LABEL + "=" + byName);
		db.close();
	}

	public void minusMemCountByName(int minusNum, String byName) {
		closeDataBase();
		db = dataBaseHelper.getWritableDatabase();
		db.execSQL("update " + TBLabelConstants.TABLE_NAME + " set " + TBLabelConstants.MEMBER_COUNT
				+ " = " + TBLabelConstants.MEMBER_COUNT + "-" + minusNum
				+ " where " + TBLabelConstants.LABEL + "=" + byName);
		db.close();
	}

	public long updateIconWithLabel(String iconPath,
	                                String onLabel) {
		closeDataBase();
		db = dataBaseHelper.getWritableDatabase();
		ContentValues value = new ContentValues();
		value.put(TBLabelConstants.LABEL, iconPath);
		long status;
		try {
			status = db.update(TBLabelConstants.TABLE_NAME, value,
					TBLabelConstants.LABEL + "=?", new String[]{onLabel});
		} finally {
			db.close();
		}


		return status;
	}

	public long updateLabelWithLabel(String label,
	                                 String onLabel) {
		closeDataBase();
		db = dataBaseHelper.getWritableDatabase();
		ContentValues value = new ContentValues();
		value.put(TBLabelConstants.LABEL, label);

		long status;
		try {
			status = db.update(TBLabelConstants.TABLE_NAME, value,
					TBLabelConstants.LABEL + "=?", new String[]{onLabel});
		} finally {
			db.close();
		}


		return status;
	}

	//query
	public Cursor selectLabel(String label) {
		closeDataBase();
		db = dataBaseHelper.getReadableDatabase();
		Cursor c = db.query(TBLabelConstants.TABLE_NAME, new String[]{TBLabelConstants.LABEL},
				TBLabelConstants.LABEL + " = ?", new String[]{label}, null, null, null);
		//db.close();
		return c;
	}

	//query
	public Cursor selectIcon(String label) {
		closeDataBase();
		db = dataBaseHelper.getReadableDatabase();
		Cursor c = db.query(TBLabelConstants.TABLE_NAME, new String[]{TBLabelConstants.LABEL_ICON},
				TBLabelConstants.LABEL + " = ?", new String[]{label}, null, null, null);
		//db.close();
		return c;
	}

	//query
	public Cursor selectMemCount(String label) {
		closeDataBase();
		db = dataBaseHelper.getReadableDatabase();
		Cursor c = db.query(TBLabelConstants.TABLE_NAME, new String[]{TBLabelConstants.MEMBER_COUNT},
				TBLabelConstants.LABEL + " = ?", new String[]{label}, null, null, null);
		//db.close();
		return c;
	}

	//query
	public Cursor selectAllOnLabel(String label) {
		closeDataBase();
		db = dataBaseHelper.getReadableDatabase();
		Cursor c = db.query(TBLabelConstants.TABLE_NAME, new String[]{TBLabelConstants.LABEL},
				TBLabelConstants.LABEL + " = ?", new String[]{label}, null, null, null);
		//db.close();
		return c;
	}

	//query
	public Cursor selectAll() {
		closeDataBase();
		db = dataBaseHelper.getReadableDatabase();
//		String name = dataBaseHelper.getDatabaseName();
		Cursor c = db.query(TBLabelConstants.TABLE_NAME, null,
				null, null, null, null, null);
		//db.close();
		return c;
	}

	public void closeDataBase() {
		if (db == null) return;
		if (db.isOpen()) db.close();
	}
}
