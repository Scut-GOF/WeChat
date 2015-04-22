package gof.scut.common.utils.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import gof.scut.cwh.models.object.LabelObj;


public class LabelTableUtils {
	private static DataBaseHelper dataBaseHelper;

	public LabelTableUtils(Context context) {
		dataBaseHelper = new DataBaseHelper(context);
		//insert several data
		/*for (int i=0;i<10;i++){
            insertAll("Friend"+i,""+i,""+i,"10086",i%3,"","");
        }*/
	}

	//insert
	public long insertAll(String label, String iconPath) {
		ContentValues value = new ContentValues();
		value.put(TBLabelConstants.LABEL, label);
		value.put(TBLabelConstants.LABEL_ICON, iconPath);
		value.put(TBLabelConstants.MEMBER_COUNT, 0 + "");
		SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
		long status = db.insert(TBLabelConstants.TABLE_NAME, null, value);
		db.close();
		return status;
	}

	public long insertAll(LabelObj labelObj) {
		ContentValues value = new ContentValues();
		value.put(TBLabelConstants.LABEL, labelObj.getLabelName());
		value.put(TBLabelConstants.LABEL_ICON, labelObj.getIconPath());
		value.put(TBLabelConstants.MEMBER_COUNT, 0 + "");
		SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
		long status = db.insert(TBLabelConstants.TABLE_NAME, null, value);
		db.close();
		return status;
	}

	//delete
	public int deleteWithLabel(String label) {
		SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
		int status = db.delete(TBLabelConstants.TABLE_NAME,
				TBLabelConstants.LABEL + "=?", new String[]{label});
		db.close();
		return status;
	}

	public long updateAllWithLabel(LabelObj labelObj, String onLabel) {
		SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
		ContentValues value = new ContentValues();
		value.put(TBLabelConstants.LABEL, labelObj.getLabelName());
		value.put(TBLabelConstants.LABEL_ICON, labelObj.getIconPath());
		value.put(TBLabelConstants.MEMBER_COUNT, labelObj.getMemCount());
		long status;
		status = db.update(TBLabelConstants.TABLE_NAME, value,
				TBLabelConstants.LABEL + "=?", new String[]{onLabel});
		db.close();
		return status;
	}

	//update ALL
	public long updateAllWithLabel(String label, String iconPath, String memCount,
	                               String onLabel) {
		SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
		ContentValues value = new ContentValues();
		value.put(TBLabelConstants.LABEL, label);
		value.put(TBLabelConstants.LABEL_ICON, iconPath);
		value.put(TBLabelConstants.MEMBER_COUNT, memCount);
		long status;
		status = db.update(TBLabelConstants.TABLE_NAME, value,
				TBLabelConstants.LABEL + "=?", new String[]{onLabel});
		db.close();
		return status;
	}

	public long updateMemCountWithLabel(String memCount,
	                                    String onLabel) {
		SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
		ContentValues value = new ContentValues();
		value.put(TBLabelConstants.LABEL, memCount);
		long status;
		status = db.update(TBLabelConstants.TABLE_NAME, value,
				TBLabelConstants.LABEL + "=?", new String[]{onLabel});
		db.close();
		return status;
	}

	public long updateIconWithLabel(String iconPath,
	                                String onLabel) {
		SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
		ContentValues value = new ContentValues();
		value.put(TBLabelConstants.LABEL, iconPath);
		long status;
		status = db.update(TBLabelConstants.TABLE_NAME, value,
				TBLabelConstants.LABEL + "=?", new String[]{onLabel});
		db.close();
		return status;
	}

	public long updateLabelWithLabel(String label,
	                                 String onLabel) {
		SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
		ContentValues value = new ContentValues();
		value.put(TBLabelConstants.LABEL, label);

		long status;
		status = db.update(TBLabelConstants.TABLE_NAME, value,
				TBLabelConstants.LABEL + "=?", new String[]{onLabel});
		db.close();
		return status;
	}

	//query
	public Cursor selectLabel(String label) {
		SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
		Cursor c = db.query(TBLabelConstants.TABLE_NAME, new String[]{TBLabelConstants.LABEL},
				TBLabelConstants.LABEL + " = ?", new String[]{label}, null, null, null);
		//db.close();
		return c;
	}

	//query
	public Cursor selectIcon(String label) {
		SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
		Cursor c = db.query(TBLabelConstants.TABLE_NAME, new String[]{TBLabelConstants.LABEL_ICON},
				TBLabelConstants.LABEL + " = ?", new String[]{label}, null, null, null);
		//db.close();
		return c;
	}

	//query
	public Cursor selectMemCount(String label) {
		SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
		Cursor c = db.query(TBLabelConstants.TABLE_NAME, new String[]{TBLabelConstants.MEMBER_COUNT},
				TBLabelConstants.LABEL + " = ?", new String[]{label}, null, null, null);
		//db.close();
		return c;
	}

	//query
	public Cursor selectAllOnLabel(String label) {
		SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
		Cursor c = db.query(TBLabelConstants.TABLE_NAME, new String[]{TBLabelConstants.LABEL},
				TBLabelConstants.LABEL + " = ?", new String[]{label}, null, null, null);
		//db.close();
		return c;
	}

	//query
	public Cursor selectAll() {
		SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
		String name = dataBaseHelper.getDatabaseName();
		Cursor c = db.query(TBLabelConstants.TABLE_NAME, null,
				null, null, null, null, null);
		//db.close();
		return c;
	}

}
