package gof.scut.cwh.models.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;

import gof.scut.common.utils.UseSystemUtils;
import gof.scut.wechatcontacts.R;

/**
 * Created by Administrator on 2015/4/8.
 */
public class TelsAdapter extends BaseAdapter {
    private Context context;
    private Cursor cursor;
    private LinearLayout layout;
    public final static int CHOICE_CALL = 0;
    public final static int CHOICE_MSG = 1;
    private int telOrMsg;

    public TelsAdapter(Context context, Cursor cursor, int choice) {
        this.context = context;
        this.cursor = cursor;
        this.telOrMsg = choice;
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public Object getItem(int position) {
        return cursor.getPosition();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        layout = (LinearLayout) inflater.inflate(R.layout.pop_phone_choice, null);
        Button tel = (Button) layout.findViewById(R.id.tel_number);

        tel.setOnClickListener(new View.OnClickListener() {
            String cTel;

            //TODO select tel by ID
            @Override
            public void onClick(View v) {
                if (CHOICE_MSG == telOrMsg)
                    UseSystemUtils.sendMsg(context, cTel);
                else if (CHOICE_CALL == telOrMsg) UseSystemUtils.sysCall(context, cTel);
            }
        });

        return null;
    }
}
