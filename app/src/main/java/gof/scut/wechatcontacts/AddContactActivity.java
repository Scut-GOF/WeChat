package gof.scut.wechatcontacts;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import java.util.Collections;
import java.util.List;

import gof.scut.common.MyApplication;
import gof.scut.common.utils.ActivityUtils;
import gof.scut.common.utils.BundleNames;
import gof.scut.common.utils.database.MainTableUtils;
import gof.scut.common.utils.popup.PopConfirmUtils;
import gof.scut.common.utils.popup.TodoOnResult;
import gof.scut.cwh.models.adapter.PhoneListAdapter;
import gof.scut.cwh.models.object.ActivityConstants;
import gof.scut.cwh.models.object.IdObj;
import gof.scut.cwh.models.object.LabelListObj;
import gof.scut.cwh.models.object.Signal;
import gof.scut.cwh.models.object.UserInfo;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

public class AddContactActivity extends RoboActivity {

	//constant
    private final static String TAG = AddContactActivity.class.getSimpleName();
	private final Context mContext = AddContactActivity.this;
	private final static int REQUEST_CODE_SCAN = 1;
	private final static int REQUEST_CODE_LABEL = 2;

	private MainTableUtils mainTableUtils;
	private Gson gson;

	//views
	@InjectView(R.id.cancel)
	TextView cancel;
	@InjectView(R.id.save)
	TextView save;
	@InjectView(R.id.name)
	EditText name;
	@InjectView(R.id.add_phone_button)
	ImageView addPhone;
	@InjectView(R.id.phone)
	EditText phone;
	@InjectView(R.id.phoneList)
	ListView phoneListView;
	@InjectView(R.id.new_label_button)
	ImageView addLable;
	@InjectView(R.id.labelList)
	ListView labelListView;
	@InjectView(R.id.address)
	EditText address;
	@InjectView(R.id.addition)
	EditText addition;

	//values
	private List<String> phoneList;
	private List<String> labelList;
	private PhoneListAdapter phoneAdapter;
	private PhoneListAdapter labelAdapter;
    private PopConfirmUtils popConfirmUtils;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_contact);
		init();

		initView();
    }

	private void init() {
		mainTableUtils = new MainTableUtils(mContext);
		gson = MyApplication.getGson();

		phoneList = new ArrayList<>();
		phoneAdapter = new PhoneListAdapter(mContext , phoneList);
		phoneListView.setAdapter(phoneAdapter);

		labelList = new ArrayList<>();
		labelAdapter = new PhoneListAdapter(mContext , labelList);
		labelListView.setAdapter(labelAdapter);

        popConfirmUtils = new PopConfirmUtils();
        popConfirmUtils.prepare(mContext, R.layout.pop_confirm);
        popConfirmUtils.initPopupWindow();
        popConfirmUtils.setTitle(getString(R.string.whether_add_friend));
	}

	private void initView() {
		cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		//保存至数据库
		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (TextUtils.isEmpty(name.getText())) {

					Toast.makeText(mContext, R.string.no_name, Toast.LENGTH_SHORT).show();
				} else if (phoneList.isEmpty()) {

					Toast.makeText(mContext, R.string.no_phone, Toast.LENGTH_SHORT).show();
				} else if (TextUtils.isEmpty(address.getText())) {

					Toast.makeText(mContext, R.string.no_address, Toast.LENGTH_SHORT).show();
				} else {
					mainTableUtils.insertAll(
							name.getText().toString(),
							address.getText().toString(),
							addition.getText().toString()
					);
					int id = mainTableUtils.getMaxId();
					IdObj obj = new IdObj(id);
					ActivityUtils.ActivitySkipWithObject(mContext, ContactInfoActivity.class, BundleNames.ID_OBJ, obj);
				}
			}
		});

        //点击按钮跳转到二维码扫描界面，这里用的是startActivityForResult跳转
        findViewById(R.id.scan_layout).setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
		        Intent intent = new Intent();
		        intent.setClass(mContext, gof.scut.common.zixng.codescan.MipcaActivityCapture.class);
		        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		        startActivityForResult(intent, REQUEST_CODE_SCAN);
	        }
        });

		//add phone number
		addPhone.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String phoneNumber = phone.getText().toString();
				if (checkPhone(phoneNumber)) {
					phoneList.add(phoneNumber);
					phoneAdapter.notifyDataSetChanged();
					phone.setText("");
				}
			}
		});
		//add label number
		addLable.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(mContext, EditContactLabelActivity.class);
				Bundle bundle = new Bundle();
				Signal signal = new Signal(ActivityConstants.ADD_CONTACTS_ACTIVITY, ActivityConstants.EditContactLabelActivity);
				bundle.putSerializable(Signal.NAME, signal);
				intent.putExtras(bundle);
				startActivityForResult(intent, REQUEST_CODE_LABEL);
			}
		});
	}

	//检查手机号码格式
	private boolean checkPhone(String phoneNumber) {
		String telRegex = "[1]\\d{10}";//第一位是1，后10位为0-9任意数字，共计11位；

		if (TextUtils.isEmpty(phoneNumber))
			return false;

		if (!phoneNumber.matches(telRegex)) {
			Toast.makeText(mContext, R.string.error_phone_format, Toast.LENGTH_SHORT).show();
			return false;
		}

		return true;
	}

    @Override
    protected void onNewIntent(final Intent intent) {
        popConfirmUtils.initTodo(new TodoOnResult() {
            @Override
            public void doOnPosResult(String[] params) {
                name.setText(intent.getStringExtra("name"));
                address.setText(intent.getStringExtra("address"));
                addition.setText(intent.getStringExtra("addition"));
                String phones = intent.getStringExtra("phone");
                Collections.addAll(phoneList, phones.substring(1, phones.length() - 1).split(", "));
                phoneAdapter.notifyDataSetChanged();
            }

            @Override
            public void doOnNegResult(String[] params) {
            }
        });
        popConfirmUtils.popWindowAtCenter(R.id.phone, R.id.confirm_title);
    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
			case REQUEST_CODE_SCAN:
				if (resultCode == RESULT_OK) {

                    popConfirmUtils.initTodo(new TodoOnResult() {
                        @Override
                        public void doOnPosResult(String[] params) {

                            String resultString = data.getStringExtra("result");
                            UserInfo friend = gson.fromJson(resultString, UserInfo.class);
                            name.setText(friend.getName());
                            address.setText(friend.getAddress());
                            addition.setText(friend.getNotes());
                            phoneList.addAll(friend.getTels());
                            phoneAdapter.notifyDataSetChanged();

                            if(! TextUtils.isEmpty(friend.getUserId())){
                                PushTask task = new PushTask(friend.getUserId());
                                task.execute(1000);
                            }
                        }
                        @Override
                        public void doOnNegResult(String[] params) {
                        }
                    });
                    popConfirmUtils.popWindowAtCenter(R.id.phone, R.id.confirm_title);
				}
				break;
			case REQUEST_CODE_LABEL:
				Bundle bundle = data.getExtras();
				LabelListObj labelListObj = (LabelListObj) bundle.getSerializable(BundleNames.LABEL_LIST);
				if (labelListObj.getLabels().size() != 0) {
					Toast.makeText(this, labelListObj.toString(), Toast.LENGTH_LONG).show();
                    labelList.addAll(labelListObj.getLabels());
                    labelAdapter.notifyDataSetChanged();
				}
				break;
			default:
				break;
		}
	}

    private class PushTask extends AsyncTask {
        String userId;

        public PushTask(String userId) {
            super();
            this.userId = userId;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            UserInfo userInfo = UserInfo.getInstance();

            return MyApplication.getInstance().getBaiduPush().
                   PushNotify(
                           getResources().getString(R.string.add_friend),
                           userInfo.getName(),
                           userId,
                           userInfo.getName(),
                           userInfo.getAddress(),
                           userInfo.getNotes(),
                           userInfo.getTels().toString()
                    );
        }
    }
}
