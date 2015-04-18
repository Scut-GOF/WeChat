package gof.scut.fental.models.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import gof.scut.common.utils.UseSystemUtils;
import gof.scut.common.utils.database.TBTelConstants;
import gof.scut.wechatcontacts.R;


public class PhonesAdapter extends BaseAdapter {
    private Context context;
    private Cursor cursor;

    public PhonesAdapter(Context context, Cursor cursor) {
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
    public View getView(int position, View convertView, final ViewGroup parent) {
        LinearLayout layout;
        LayoutInflater inflater = LayoutInflater.from(context);
        layout = (LinearLayout) inflater.inflate(R.layout.cell_contact_phone_list, null);

        TextView tv_phone_title = (TextView) layout.findViewById(R.id.tv_phone_title);
        TextView tv_phone_content = (TextView) layout.findViewById(R.id.tv_phone_content);

        Button msg = (Button) layout.findViewById(R.id.send_msg);
        Button call = (Button) layout.findViewById(R.id.call);

        cursor.moveToPosition(position);

        final String pTitle = "手机";
        final String pTel = cursor.getString(cursor.getColumnIndex(TBTelConstants.TEL));

        //more details got when view
        //....

        tv_phone_title.setText(pTitle+position);
        tv_phone_content.setText(pTel);

        //TODO MOVE TO CONTACTS DETAIL
        msg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                UseSystemUtils.sendMsg(context,pTel);
            }
        });
        call.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                UseSystemUtils.sysCall(context,pTel);
            }
        });
        return layout;
    }

}
