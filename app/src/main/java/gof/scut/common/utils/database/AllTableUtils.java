package gof.scut.common.utils.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Administrator on 2015/4/17.
 */
public class AllTableUtils {
	private static DataBaseHelper dataBaseHelper;

	public AllTableUtils(Context context) {
		dataBaseHelper = new DataBaseHelper(context);
		//insert several data
		/*for (int i=0;i<10;i++){
            insertAll("Friend"+i,""+i,""+i,"10086",i%3,"","");
        }*/
	}

	public Cursor selectAllIDNameOnLabel(String labelName) {
		SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
		//SELECT  _id,name FROM contacts WHERE _id IN ( SELECT _id FROM idLabel WHERE label = '" + labelName + "')"
		Cursor c = db.rawQuery("SELECT " + TBMainConstants.ID + "," + TBMainConstants.NAME
				+ " FROM " + TBMainConstants.TABLE_NAME + " WHERE " + TBMainConstants.ID
				+ " IN ( SELECT " + TBMainConstants.ID + " FROM " + TBIDLabelConstants.TABLE_NAME
				+ " WHERE " + TBIDLabelConstants.LABEL + " = '" + labelName + "')", null);

		return c;
	}
}
