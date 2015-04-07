package gof.scut.cwh.models.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import gof.scut.common.utils.ActivityUtils;
import gof.scut.common.utils.DBConstants;
import gof.scut.common.utils.DataBaseHelper;
import gof.scut.common.utils.UseSystemUtils;
import gof.scut.cwh.models.object.IdObj;
import gof.scut.wechatcontacts.ContactInfoActivity;
import gof.scut.wechatcontacts.R;


public class ContactsAdapter extends BaseAdapter {
    private Context context;
    private Cursor cursor;
    private LinearLayout layout;

    public ContactsAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
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
        layout = (LinearLayout) inflater.inflate(R.layout.main_list_cell, null);
        ImageView labelIcon = (ImageView) layout.findViewById(R.id.label_icon);
        TextView name = (TextView) layout.findViewById(R.id.name);
        TextView tel = (TextView) layout.findViewById(R.id.tel);
        Button msg = (Button) layout.findViewById(R.id.send_msg);
        Button call = (Button) layout.findViewById(R.id.call);

        cursor.moveToPosition(position);
        final int cLabel = cursor.getInt(cursor.getColumnIndex(DBConstants.LABEL));
        final String cName = cursor.getString(cursor.getColumnIndex(DBConstants.NAME));
        final String cTel = cursor.getString(cursor.getColumnIndex(DBConstants.TEL));
        //more details got when view
        //....
        //set label icon
        labelIcon.setImageResource(R.drawable.chart_1_2);
        name.setText(cName);
        tel.setText(cTel);
        msg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                UseSystemUtils.sendMsg(context, cTel);
            }
        });
        call.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                UseSystemUtils.sysCall(context, cTel);
            }
        });
        name.setClickable(true);
        name.setFocusable(true);
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(cTel);
            }
        });
        tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(cTel);
            }
        });
        return layout;
    }

    private void onItemClick(String tel) {
        IdObj obj = new IdObj(tel);
        ActivityUtils.ActivitySkipWithObject(context, ContactInfoActivity.class, obj);
    }

}
