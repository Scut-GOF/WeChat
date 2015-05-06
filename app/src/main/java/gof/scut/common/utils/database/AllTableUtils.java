package gof.scut.common.utils.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import gof.scut.common.utils.StringUtils;
import gof.scut.cwh.models.object.LabelObj;
import gof.scut.cwh.models.object.LightIdObj;

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

	//	public Cursor selectAllIDNameOnLabel(String labelName) {
//		closeDataBase();
//		db = dataBaseHelper.getReadableDatabase();
////		Cursor c1 = db.rawQuery("SELECT  _id,name FROM contacts WHERE _id IN ( SELECT _id FROM idLabel WHERE label = '" + labelName + "')", null);
////		Cursor c2 = db.rawQuery("SELECT _id FROM idLabel WHERE label = '" + labelName + "'", null);
////		Cursor c3 = db.rawQuery("SELECT _id FROM fts_id_label WHERE label = '" + labelName + "'", null);
////		Cursor c4 = db.rawQuery("SELECT _id FROM fts_id_label ", null);
//
//		//SELECT  _id,name FROM contacts WHERE _id IN ( SELECT _id FROM idLabel WHERE label = '" + labelName + "')"
//		Cursor c = db.rawQuery("SELECT " + TBMainConstants.ID + "," + TBMainConstants.NAME
//				+ " FROM " + TBMainConstants.FTS_TABLE_NAME + " WHERE " + TBMainConstants.ID
//				+ " IN ( SELECT " + TBMainConstants.ID + " FROM " + TBIDLabelConstants.FTS_TABLE_NAME
//				+ " WHERE " + TBIDLabelConstants.LABEL + " match ? )", new String[]{"'" + labelName + "'"});
//
//		return c;
//	}
	public List<LightIdObj> selectLightIdObjOnLabel(String labelName) {
		closeDataBase();
		db = dataBaseHelper.getReadableDatabase();
		labelName = StringUtils.splitChineseSingly(labelName);
//		Cursor c1 = db.rawQuery("SELECT  _id,name FROM contacts WHERE _id IN ( SELECT _id FROM idLabel WHERE label = '" + labelName + "')", null);
//		Cursor c2 = db.rawQuery("SELECT _id FROM idLabel WHERE label = '" + labelName + "'", null);
//		Cursor c3 = db.rawQuery("SELECT _id FROM fts_id_label WHERE label = '" + labelName + "'", null);
//		Cursor c4 = db.rawQuery("SELECT _id FROM fts_id_label ", null);

		//SELECT  _id,name FROM contacts WHERE _id IN ( SELECT _id FROM idLabel WHERE label = '" + labelName + "')"
		Cursor cursorEdit = db.rawQuery("SELECT " + TBMainConstants.ID + "," + TBMainConstants.NAME
				+ " FROM " + TBMainConstants.FTS_TABLE_NAME + " WHERE " + TBMainConstants.ID
				+ " IN ( SELECT " + TBIDLabelConstants.ID + " FROM " + TBIDLabelConstants.FTS_TABLE_NAME
				+ " WHERE " + TBIDLabelConstants.LABEL + " = ? )", new String[]{labelName});
		List<LightIdObj> members = new ArrayList<>();
		for (int i = 0; i < cursorEdit.getCount(); i++) {
			cursorEdit.moveToPosition(i);
			members.add(
					new LightIdObj(cursorEdit.getString(cursorEdit.getColumnIndex(TBMainConstants.ID)),
							StringUtils.recoverWordFromDB(
									cursorEdit.getString(cursorEdit.getColumnIndex(TBMainConstants.NAME))
							)
					)
			);
		}
		cursorEdit.close();
		closeDataBase();
		return members;
	}

	public List<LabelObj> selectLabelDetailForID(String id) {
		closeDataBase();
		//select *from label where label in (select label from fts_idlabel where id match id);
		Cursor cursorLabel = db.rawQuery("select * from " + TBLabelConstants.TABLE_NAME
				+ " where " + TBLabelConstants.LABEL + " in ( select " + TBIDLabelConstants.LABEL + " from "
				+ TBIDLabelConstants.FTS_TABLE_NAME + " where " + TBIDLabelConstants.ID + " = ?)"
				, new String[]{id});
		List<LabelObj> labels = new ArrayList<>();
		for (int i = 0; i < cursorLabel.getCount(); i++) {
			cursorLabel.moveToPosition(i);
			labels.add(
					new LabelObj(
							StringUtils.recoverWordFromDB(
									cursorLabel.getString(cursorLabel.getColumnIndex(TBLabelConstants.LABEL))
							),
							cursorLabel.getString(cursorLabel.getColumnIndex(TBLabelConstants.LABEL_ICON)),
							cursorLabel.getInt(cursorLabel.getColumnIndex(TBLabelConstants.MEMBER_COUNT))
					));
		}
		cursorLabel.close();
		closeDataBase();
		return labels;
	}

	public void closeDataBase() {
		if (db == null) return;
		if (db.isOpen()) db.close();
	}
}
