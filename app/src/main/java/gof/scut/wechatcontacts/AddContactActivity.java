package gof.scut.wechatcontacts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import gof.scut.common.MyApplication;
import gof.scut.common.utils.BundleNames;
import gof.scut.common.utils.Log;
import gof.scut.common.utils.database.MainTableUtils;
import gof.scut.cwh.models.object.ActivityConstants;
import gof.scut.cwh.models.object.IdObj;
import gof.scut.cwh.models.object.LabelObj;
import gof.scut.cwh.models.object.Signal;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

public class AddContactActivity extends RoboActivity {

	//constant
	private final Context mContext = AddContactActivity.this;
	private final static int REQUEST_CODE_SCAN = 1;
	private final static int REQUEST_CODE_LABEL = 2;

	private MainTableUtils mainTableUtils;
	private Gson gson;
	@Inject
	InputMethodManager imm;

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
	private MyAdapter phoneAdapter;
	private MyAdapter labelAdapter;


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
		phoneAdapter = new MyAdapter(phoneList);
		phoneListView.setAdapter(phoneAdapter);

		labelList = new ArrayList<>();
		labelAdapter = new MyAdapter(labelList);
		labelListView.setAdapter(labelAdapter);
	}

	private void initView() {
		//点击按钮跳转到二维码扫描界面，这里用的是startActivityForResult跳转
		//扫描完了之后调到该界面
		findViewById(R.id.scan_layout).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(AddContactActivity.this, gof.scut.common.zixng.codescan.MipcaActivityCapture.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivityForResult(intent, REQUEST_CODE_SCAN);
			}
		});

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
				//TODO 验证
				mainTableUtils.insertAll(
						name.getText().toString(),
						"object.getlPinYin()",
						"object.getsPinYin()",
						address.getText().toString(),
						addition.getText().toString()
				);
			}
		});

		//add phone number
		addPhone.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//TODO 判断
				phoneList.add(phone.getText().toString());
				phoneAdapter.notifyDataSetChanged();
				phone.setText("");
			}
		});
		//add label number
		addLable.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(mContext, LabelsActivity.class);
//                startActivityForResult(intent, REQUEST_CODE_LABEL);
				Intent intent = new Intent();
				intent.setClass(mContext, EditContactLabelActivity.class);
				Bundle bundle = new Bundle();
				Signal signal = new Signal(ActivityConstants.ADD_CONTACTS_ACTIVITY, ActivityConstants.EditContactLabelActivity);
				bundle.putSerializable(Signal.NAME, signal);
				//bundle.putSerializable(BundleNames.ID_OBJ, new IdObj(0));
				intent.putExtras(bundle);
				startActivityForResult(intent, REQUEST_CODE_LABEL);

			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
			case REQUEST_CODE_SCAN:
				if (resultCode == RESULT_OK) {
					Bundle bundle = data.getExtras();
					String resultString = bundle.getString("result");
					IdObj object = gson.fromJson(resultString, IdObj.class);
					Log.d(null, object.toString());

					name.setText(object.getName());
					address.setText(object.getAddress());
					addition.setText(object.getNotes());
					phoneList.addAll(object.getTels());
					phoneAdapter.notifyDataSetChanged();
				}
				break;
			case REQUEST_CODE_LABEL:

				Bundle bundle = data.getExtras();
				LabelObj labelObj = (LabelObj) bundle.getSerializable(BundleNames.LABEL_OBJ);
				//TODO REFRESH LABEL LIST, and finally insert id_label records into database
				Toast.makeText(this, labelObj.toString(), Toast.LENGTH_LONG).show();
				break;
			default:
				break;
		}
	}

	private class MyAdapter extends BaseAdapter {

		private List<String> data;

		public MyAdapter(List<String> datas) {
			data = datas;
		}

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(mContext).inflate(R.layout.cell_edit_member_list, parent, false);
				holder.name = (TextView) convertView.findViewById(R.id.name);
				holder.remove = (Button) convertView.findViewById(R.id.remove_member);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.name.setText(data.get(position));
			holder.remove.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					data.remove(position);
					MyAdapter.this.notifyDataSetChanged();
				}
			});

			return convertView;
		}
	}

	static class ViewHolder {
		Button remove;
		TextView name;
	}

}
