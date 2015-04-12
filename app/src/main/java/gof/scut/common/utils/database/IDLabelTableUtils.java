package gof.scut.common.utils.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class IDLabelTableUtils {
    private static DataBaseHelper dataBaseHelper;

    public IDLabelTableUtils(Context context) {
        dataBaseHelper = new DataBaseHelper(context, TBIDLabelConstants.TABLE_NAME);
        //insert several data
        /*for (int i=0;i<10;i++){
            insertAll("Friend"+i,""+i,""+i,"10086",i%3,"","");
        }*/
    }

    //insert
    public long insertAll(String id, int label) {
        ContentValues value = new ContentValues();
        value.put(TBIDLabelConstants.ID, id);
        value.put(TBIDLabelConstants.LABEL, label);
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        long status = db.insert(TBIDLabelConstants.TABLE_NAME, null, value);
        db.close();
        return status;
    }

    //delete
    public int deleteWithID(String id) {
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        int status = db.delete(TBIDLabelConstants.TABLE_NAME,
                TBIDLabelConstants.ID + "=?", new String[]{id});
        db.close();
        return status;
    }

    //delete
    public int deleteWithLabel(String label) {
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        int status = db.delete(TBIDLabelConstants.TABLE_NAME,
                TBIDLabelConstants.LABEL + "=?", new String[]{label});
        db.close();
        return status;
    }

    //delete
    public int deleteWithID_Label(String id, String label) {
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        int status = db.delete(TBIDLabelConstants.TABLE_NAME,
                TBIDLabelConstants.ID + "=?", new String[]{id});
        db.close();
        return status;
    }


    //update ALL
    public long updateLabelWithID(String label,
                                  String byID) {
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(TBIDLabelConstants.LABEL, label);
        value.put(TBIDLabelConstants.ID, byID);

        long status;
        status = db.update(TBIDLabelConstants.TABLE_NAME, value,
                TBIDLabelConstants.ID + "=?", new String[]{byID});
        db.close();
        return status;
    }

    //query
    public Cursor selectLabelWithID(String ID) {
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        Cursor c = db.query(TBIDLabelConstants.TABLE_NAME, new String[]{TBIDLabelConstants.LABEL},
                TBIDLabelConstants.ID + " = ?", new String[]{ID}, null, null, null);
        //db.close();
        return c;
    }


}
