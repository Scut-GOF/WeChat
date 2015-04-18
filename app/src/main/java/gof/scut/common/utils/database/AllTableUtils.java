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
        //SELECT ID,NAME FROM (SELECT ID FROM IDLABEL WHERE LABEL = THIS.LABEL) INNER JOIN MAIN WHERE IDLABEL.ID=MAIN.ID
//        Cursor c = db.rawQuery("SELECT "+TBMainConstants.ID+","+TBMainConstants.NAME+" FROM "
//                +TBIDLabelConstants.TABLE_NAME+" INNER JOIN "+TBMainConstants.TABLE_NAME
//                +" ON "+TBMainConstants.TABLE_NAME+"."+TBMainConstants.ID
//                +" = ",null);
        //TODO
        Cursor c = db.rawQuery("SELECT contacts._id,name FROM idLabel INNER JOIN contacts ON idLabel._id=contacts._id  WHERE label = '" + labelName + "'", null);
        //db.close();
        return c;
    }
}
