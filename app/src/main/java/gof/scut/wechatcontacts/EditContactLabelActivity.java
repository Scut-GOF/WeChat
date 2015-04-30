package gof.scut.wechatcontacts;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import gof.scut.common.utils.ActivityUtils;
import gof.scut.common.utils.BundleNames;
import gof.scut.common.utils.database.AllTableUtils;
import gof.scut.common.utils.database.CursorUtils;
import gof.scut.common.utils.database.LabelTableUtils;
import gof.scut.cwh.models.adapter.ContactLabelAdapter;
import gof.scut.cwh.models.adapter.LabelsAdapter;
import gof.scut.cwh.models.object.ActivityConstants;
import gof.scut.cwh.models.object.IdObj;
import gof.scut.cwh.models.object.LabelObj;
import gof.scut.cwh.models.object.Signal;


public class EditContactLabelActivity extends Activity implements View.OnClickListener {

	TextView labelsBack;
	GridView existsLabels;
	ListView labelList;

	LabelTableUtils labelTableUtils;
	AllTableUtils allTableUtils;
	Cursor cursorLabels;
	Cursor cursorExistsLabels;

	IdObj contact;


	int fromActivity;

	public int getFromActivity() {
		return fromActivity;
	}

	public void setFromActivity(int fromActivity) {
		this.fromActivity = fromActivity;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_contact_label);

		init();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (fromActivity == ActivityConstants.CONTACT_INFO_ACTIVITY)
			existsLabels.setVisibility(View.VISIBLE);
		else
			existsLabels.setVisibility(View.GONE);
		if (fromActivity == ActivityConstants.CONTACT_INFO_ACTIVITY) initGrid();
		initList();
	}

	void initDatabase() {
		labelTableUtils = new LabelTableUtils(this);
		allTableUtils = new AllTableUtils(this);
	}

	void init() {
		intentCheck();
		initDatabase();
		findView();

		setListener();

	}

	void intentCheck() {
		//AddContact, ContactInfo
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		Signal signal = (Signal) bundle.getSerializable(Signal.NAME);
		setFromActivity(signal.getFrom());
		if (fromActivity == ActivityConstants.CONTACT_INFO_ACTIVITY)
			contact = (IdObj) bundle.getSerializable(BundleNames.ID_OBJ);
	}

	void setListener() {

		labelsBack.setOnClickListener(this);
	}

	void findView() {

		labelList = (ListView) findViewById(R.id.label_list);
		labelsBack = (TextView) findViewById(R.id.cancel);
		existsLabels = (GridView) findViewById(R.id.exists_labels);
		if (fromActivity == ActivityConstants.CONTACT_INFO_ACTIVITY)
			existsLabels.setVisibility(View.VISIBLE);
		else
			existsLabels.setVisibility(View.GONE);
	}

	void initList() {

		CursorUtils.closeExistsCursor(cursorLabels);
		cursorLabels = labelTableUtils.selectAll();
		LabelsAdapter labelsAdapter = new LabelsAdapter(this, cursorLabels, getFromActivity());
		labelList.setAdapter(labelsAdapter);

	}

	void initGrid() {

		CursorUtils.closeExistsCursor(cursorExistsLabels);
		cursorLabels = allTableUtils.selectLabelDetailForID(contact.getId() + "");
		ContactLabelAdapter labelsAdapter = new ContactLabelAdapter(this, cursorExistsLabels, contact);
		existsLabels.setAdapter(labelsAdapter);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.cancel:
				setResult();
				finish();
				break;

		}
	}

	protected void onPause() {
		super.onPause();
		//setResult();
		CursorUtils.closeExistsCursor(cursorLabels);
		CursorUtils.closeExistsCursor(cursorExistsLabels);
		labelTableUtils.closeDataBase();
		allTableUtils.closeDataBase();
	}

	protected void onStop() {
		super.onStop();
		//setResult();
		CursorUtils.closeExistsCursor(cursorLabels);
		CursorUtils.closeExistsCursor(cursorExistsLabels);
		labelTableUtils.closeDataBase();
		allTableUtils.closeDataBase();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		//setResult();
		CursorUtils.closeExistsCursor(cursorLabels);
		CursorUtils.closeExistsCursor(cursorExistsLabels);
		labelTableUtils.closeDataBase();
		allTableUtils.closeDataBase();
	}

	private void setResult() {
		if (getFromActivity() == ActivityConstants.ADD_CONTACTS_ACTIVITY)
			ActivityUtils.setActivityResult
					(this, ActivityConstants.REQUEST_CODE_LABEL, BundleNames.LABEL_OBJ,
							new LabelObj("", "", 0));
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			setResult();
			super.onKeyDown(keyCode, event);
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}

	}
}