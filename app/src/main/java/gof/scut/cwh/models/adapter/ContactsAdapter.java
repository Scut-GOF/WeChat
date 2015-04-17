package gof.scut.cwh.models.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

import gof.scut.common.utils.ActivityUtils;
import gof.scut.common.utils.BundleNames;
import gof.scut.common.utils.database.CursorUtils;
import gof.scut.common.utils.database.TBMainConstants;
import gof.scut.common.utils.database.TBTelConstants;
import gof.scut.common.utils.database.TelTableUtils;
import gof.scut.common.utils.UseSystemUtils;
import gof.scut.cwh.models.object.IdObj;
import gof.scut.wechatcontacts.ContactInfoActivity;
import gof.scut.wechatcontacts.R;


public class ContactsAdapter extends BaseAdapter {
    private Context context;
    private Cursor cursor;
    private LinearLayout layout;
    PopupWindow telChoiceWindow = null;
    View telChoiceView = null;

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
    public View getView(int position, View convertView, final ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        layout = (LinearLayout) inflater.inflate(R.layout.main_list_cell, null);

        TextView name = (TextView) layout.findViewById(R.id.name);

        Button msg = (Button) layout.findViewById(R.id.send_msg);
        Button call = (Button) layout.findViewById(R.id.call);

        cursor.moveToPosition(position);

        final String cName = cursor.getString(cursor.getColumnIndex(TBMainConstants.NAME));
        final String id = cursor.getString(cursor.getColumnIndex(TBMainConstants.ID));

        //more details got when view
        //....

        name.setText(cName);

        //TODO MOVE TO CONTACTS DETAIL
        msg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TelTableUtils telTableUtil = new TelTableUtils(context);
                //TODO TEST insert several data
                telTableUtil.insertAll("" + id, "10086");
                telTableUtil.insertAll("" + id, "10086" + id);

                final Cursor cTel = telTableUtil.selectTelWithID(id);

                if (cTel.getCount() > 1) {
                    initPopChoice(TelsAdapter.CHOICE_MSG, cTel);
                    popPhoneSelector();
                } else if (cTel.getCount() == 1) {
                    ArrayList<String> phoneList = new ArrayList<String>();
                    CursorUtils.cursorToStringArray(cTel, phoneList, TBTelConstants.TEL);
                    UseSystemUtils.sendMsg(context, phoneList.get(0));
                }
            }
        });
        call.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TelTableUtils telTableUtil = new TelTableUtils(context);
                //TODO TEST insert several data
                telTableUtil.insertAll("" + id, "10086");
                telTableUtil.insertAll("" + id, "10086" + id);

                final Cursor cTel = telTableUtil.selectTelWithID(id);

                if (cTel.getCount() > 1) {
                    initPopChoice(TelsAdapter.CHOICE_CALL, cTel);
                    popPhoneSelector();
                } else if (cTel.getCount() == 1) {
                    ArrayList<String> phoneList = new ArrayList<String>();
                    CursorUtils.cursorToStringArray(cTel, phoneList, TBTelConstants.TEL);
                    UseSystemUtils.sysCall(context, phoneList.get(0));
                }
            }
        });
        name.setClickable(true);
        name.setFocusable(true);
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(Integer.parseInt(id));
            }
        });
        return layout;
    }

    private void onItemClick(int id) {
        IdObj obj = new IdObj(id);
        ActivityUtils.ActivitySkipWithObject(context, ContactInfoActivity.class, BundleNames.ID_OBJ, obj);
    }

    public void popPhoneSelector() {
        telChoiceWindow.update();
        telChoiceWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
        Animation anim1 = AnimationUtils.loadAnimation(context, R.anim.scale_center_enter);
        telChoiceView.findViewById(R.id.phone_choices).startAnimation(anim1);
    }

    private void initPopChoice(int telOrMsg, Cursor cursor) {
        telChoiceView = LayoutInflater.from(context).inflate(
                R.layout.pop_phone_choice, null, false);
        telChoiceWindow = new PopupWindow(telChoiceView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);

        ColorDrawable dw = new ColorDrawable(0x00000000);
        telChoiceView.setBackgroundDrawable(dw);

        TextView telsTitle = (TextView) telChoiceView.findViewById(R.id.list_title);
        if (TelsAdapter.CHOICE_MSG == telOrMsg) telsTitle.setText("Message");
        else if (TelsAdapter.CHOICE_CALL == telOrMsg) telsTitle.setText("Call");

        ListView telList = (ListView) telChoiceView.findViewById(R.id.phone_list);
        TelsAdapter telsAdapter = new TelsAdapter(context, cursor, telOrMsg, telChoiceWindow);
        telList.setAdapter(telsAdapter);

        telChoiceView.setFocusable(true);
        telChoiceView.setFocusableInTouchMode(true);
        telChoiceView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
                if (arg1 == KeyEvent.KEYCODE_BACK) {
                    telChoiceWindow.dismiss();
                    return true;
                }
                return false;
            }
        });
        telChoiceView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                telChoiceWindow.dismiss();
            }

        });

        telChoiceWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }

        });

    }
}
