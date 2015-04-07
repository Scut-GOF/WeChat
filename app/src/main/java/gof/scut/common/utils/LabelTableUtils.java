package gof.scut.common.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class LabelTableUtils {
    private static DataBaseHelper dataBaseHelper;

    public LabelTableUtils(Context context) {
        dataBaseHelper = new DataBaseHelper(context, TBLabelConstants.TABLE_NAME);
        //insert several data
        /*for (int i=0;i<10;i++){
            insertAll("Friend"+i,""+i,""+i,"10086",i%3,"","");
        }*/
    }

    //insert
    public long insertAll(String id, int label) {
        ContentValues value = new ContentValues();
        value.put(TBLabelConstants.ID, id);
        value.put(TBLabelConstants.LABEL, label);
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        long status = db.insert(TBLabelConstants.TABLE_NAME, null, value);
        db.close();
        return status;
    }

    //delete
    public int deleteWithID(String id) {
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        int status = db.delete(TBLabelConstants.TABLE_NAME,
                TBLabelConstants.ID + "=?", new String[]{id});
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

    //delete
    public int deleteWithID_Label(String id, String label) {
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        int status = db.delete(TBLabelConstants.TABLE_NAME,
                TBLabelConstants.ID + "=?", new String[]{id});
        db.close();
        return status;
    }


    //update ALL
    public long updateLabelWithID(String label,
                                  String byID) {
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(TBLabelConstants.LABEL, label);
        value.put(TBLabelConstants.ID, byID);

        long status;
        status = db.update(TBLabelConstants.TABLE_NAME, value,
                TBLabelConstants.ID + "=?", new String[]{byID});
        db.close();
        return status;
    }

    //query
    public Cursor selectLabelWithID(String ID) {
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        Cursor c = db.query(TBLabelConstants.TABLE_NAME, new String[]{TBLabelConstants.LABEL},
                TBLabelConstants.ID + " = ?", new String[]{ID}, null, null, null);
        //db.close();
        return c;
    }


}
