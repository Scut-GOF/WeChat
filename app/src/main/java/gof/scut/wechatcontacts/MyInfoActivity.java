package gof.scut.wechatcontacts;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import gof.scut.common.MyApplication;
import gof.scut.common.utils.Utils;
import gof.scut.cwh.models.adapter.PhoneListAdapter;
import gof.scut.cwh.models.object.UserInfo;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;


public class MyInfoActivity extends RoboActivity {

    private final static String TAG = MyInfoActivity.class.getSimpleName();
    private final Context mContext = MyInfoActivity.this;

    //views
    @InjectView(R.id.cancel)
    private TextView back_image;
    @InjectView(R.id.save)
    private TextView save;
    @InjectView(R.id.my_binary_code)
    private ImageView my_binary_code;
    @InjectView(R.id.name)
    private EditText name;
    @InjectView(R.id.add_phone_button)
    private ImageView addPhone;
    @InjectView(R.id.phone)
    private EditText phone;
    @InjectView(R.id.phoneList)
    private ListView phoneListView;
    @InjectView(R.id.address)
    private EditText address;
    @InjectView(R.id.addition)
    private EditText addition;

    //values
    private Gson gson;
    private UserInfo userInfo;
    private ArrayList<String> phoneList;
    private PhoneListAdapter phoneAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_info);
        init();
        eventHandler();
	}

    private void init(){
        phoneList = new ArrayList<>();
        gson = MyApplication.getGson();
        userInfo = UserInfo.getInstance();

        if(TextUtils.isEmpty(userInfo.getName()) || userInfo.getTels().isEmpty() || TextUtils.isEmpty(userInfo.getAddress())){
            Toast.makeText(mContext,R.string.information_lost,Toast.LENGTH_SHORT).show();
        }else{
            name.setText(userInfo.getName());
            address.setText(userInfo.getAddress());
            addition.setText(userInfo.getNotes());
            phoneList = userInfo.getTels();

            Utils.createQRImage(my_binary_code , gson.toJson(userInfo, UserInfo.class) );
        }
        phoneAdapter = new PhoneListAdapter(this,phoneList,phoneListView);
        Utils.setListViewHeightBasedOnChildren(phoneListView);
        phoneListView.setAdapter(phoneAdapter);
    }

    private void eventHandler() {
        //add phone number
        addPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = phone.getText().toString();
                if (Utils.checkPhone(mContext ,phoneNumber)) {
                    phoneList.add(phoneNumber);
                    Utils.setListViewHeightBasedOnChildren(phoneListView);
                    phoneAdapter.notifyDataSetChanged();
                    phone.setText("");
                }
            }
        });
        //save
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(name.getText())){

                    Toast.makeText(mContext, R.string.no_name,Toast.LENGTH_SHORT).show();
                }else if(phoneList.isEmpty()){

                    Toast.makeText(mContext,R.string.no_phone,Toast.LENGTH_SHORT).show();
                }else{
                    userInfo.setName(name.getText().toString());
                    userInfo.setTels(phoneList);
                    userInfo.setAddress(address.getText().toString());
                    userInfo.setNotes(addition.getText().toString());

                    Toast.makeText(mContext,R.string.is_save,Toast.LENGTH_SHORT).show();
                    Utils.createQRImage(my_binary_code , gson.toJson(userInfo, UserInfo.class) );
                }
            }
        });

        //back
        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
