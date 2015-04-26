package gof.scut.wechatcontacts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;

import gof.scut.common.MyApplication;
import gof.scut.common.utils.Log;
import gof.scut.common.utils.database.MainTableUtils;
import gof.scut.cwh.models.object.IdObj;

public class AddContactActivity extends Activity {

	private static final String TAG = AddContactActivity.class.getSimpleName();
	private final Context mContext = AddContactActivity.this;

	private final static int SCANNIN_GREQUEST_CODE = 1;
	private MainTableUtils mainTableUtils;
	private Gson gson;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_contact);

		initView();
	}

	private void initView() {
		//点击按钮跳转到二维码扫描界面，这里用的是startActivityForResult跳转
		//扫描完了之后调到该界面
		findViewById(R.id.scan).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(AddContactActivity.this, gof.scut.common.zixng.codescan.MipcaActivityCapture.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
			}
		});

		mainTableUtils = new MainTableUtils(mContext);
		gson = MyApplication.getGson();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case SCANNIN_GREQUEST_CODE:
				if (resultCode == RESULT_OK) {
					Bundle bundle = data.getExtras();

					String resultString = bundle.getString("result");

					IdObj object = gson.fromJson(resultString, IdObj.class);
					mainTableUtils.insertAll(object.getName(),
							object.getlPinYin(), object.getsPinYin(), object.getAddress()
							, object.getNotes());

					Log.d(null, object.toString());
				}
				break;
		}
	}
}
