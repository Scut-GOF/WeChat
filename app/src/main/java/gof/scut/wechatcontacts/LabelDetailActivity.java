package gof.scut.wechatcontacts;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import gof.scut.common.utils.ActivityUtils;
import gof.scut.common.utils.BitmapUtils;
import gof.scut.common.utils.BundleNames;
import gof.scut.common.utils.Log;
import gof.scut.common.utils.StringUtils;
import gof.scut.common.utils.database.AllTableUtils;
import gof.scut.common.utils.database.CursorUtils;
import gof.scut.common.utils.database.IDLabelTableUtils;
import gof.scut.common.utils.database.LabelTableUtils;
import gof.scut.common.utils.database.TBLabelConstants;
import gof.scut.common.utils.popup.PopConfirmUtils;
import gof.scut.common.utils.popup.PopEditLabelUtils;
import gof.scut.common.utils.popup.TodoOnResult;
import gof.scut.cwh.models.adapter.ContactsAdapter;
import gof.scut.cwh.models.adapter.MemEditAdapter;
import gof.scut.cwh.models.object.ActivityConstants;
import gof.scut.cwh.models.object.IdObj;
import gof.scut.cwh.models.object.LabelObj;
import gof.scut.cwh.models.object.Signal;


public class LabelDetailActivity extends Activity implements View.OnClickListener {
	final static String TAG = "labelDetail";
	LabelObj labelObj;
	boolean inEdit = false;

	AllTableUtils allTableUtils;
	LabelTableUtils labelTableUtils;
	IDLabelTableUtils idLabelTableUtils;
	Cursor cursorEdit;
	Cursor cursorView;

	TextView labelsBack;
	TextView editLabel;

	ImageView labelIcon;
	TextView labelName;
	TextView memberCount;

	LinearLayout addMemberLayout;
	Button addMember;

	ListView labelMembers;

	Button deleteLabel;

	PopEditLabelUtils popEditLabelUtils;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_label_detail);
		init();

	}

	@Override
	protected void onResume() {
		super.onResume();
		checkState();
	}

	void init() {
		getLabelFromBundle();
		initDataBase();
		findView();
		initComponent();
	}

	void getLabelFromBundle() {
		Bundle bundle = this.getIntent().getExtras();
		if (bundle != null) {
			labelObj = (LabelObj) bundle.getSerializable(BundleNames.LABEL_OBJ);
		} else {
			Log.e(TAG, "Bundle null");
			finish();
		}
	}

	void initDataBase() {
		allTableUtils = new AllTableUtils(this);
		labelTableUtils = new LabelTableUtils(this);
		idLabelTableUtils = new IDLabelTableUtils(this);
	}

	void initComponent() {
		labelName.setText(labelObj.getLabelName());
		labelIcon.setBackgroundDrawable(null);
		if (labelObj.getIconPath().equals("")) {
			labelIcon.setBackgroundResource(R.drawable.label50);
		} else {
			labelIcon.setImageBitmap(BitmapUtils.decodeBitmapFromPath(labelObj.getIconPath()));
		}
		memberCount.setText(StringUtils.addBrackets(labelObj.getMemCount() + ""));
		popEditLabelUtils = new PopEditLabelUtils();
		popEditLabelUtils.initPopAddLabel(this, new TodoOnResult() {
			@Override
			public void doOnPosResult(String[] params) {
				//add label id
				labelObj.setLabelName(params[0]);
				labelObj.setIconPath(params[1]);
				//check if label exists
//				Cursor labelsWithName = labelTableUtils.selectAllOnLabel(labelObj.getLabelName());
//				if (labelsWithName.getCount() != 0) {
//					Toast.makeText(LabelDetailActivity.this, "标签已存在，请指定其他标签名", Toast.LENGTH_LONG).show();
//					labelsWithName.close();
//					labelTableUtils.closeDataBase();
//					return;
//				}
//				labelsWithName.close();
//				labelTableUtils.closeDataBase();

				long state = labelTableUtils.updateAllWithLabel(labelObj, labelName.getText().toString());
				if (state < 0) Log.e("LabelsActivity", "update label failed");
				labelIcon.setImageBitmap(BitmapUtils.decodeBitmapFromPath(labelObj.getIconPath()));
				labelName.setText(labelObj.getLabelName());
				popEditLabelUtils.dismissWindow();
			}

			@Override
			public void doOnNegResult(String[] params) {

			}
		}, "编辑标签", R.id.label_detail_layout, labelObj);
		findViewById(R.id.title).setEnabled(true);
		setListener();

	}

	void checkState() {
		int viewState;
		if (inEdit) {
			initEditList();
			viewState = View.VISIBLE;
			editLabel.setText("Save");
		} else {
			saveEditResult();
			initViewList();
			viewState = View.GONE;
			editLabel.setText("Edit");
		}
		addMemberLayout.setVisibility(viewState);
		deleteLabel.setVisibility(viewState);

	}

	void setListener() {
		labelsBack.setOnClickListener(this);
		editLabel.setOnClickListener(this);
		deleteLabel.setOnClickListener(this);
		addMember.setOnClickListener(this);
		labelIcon.setOnClickListener(this);
		labelName.setOnClickListener(this);
		addMemberLayout.setOnClickListener(this);

	}

	void saveEditResult() {

	}

	void findView() {
		labelsBack = (TextView) findViewById(R.id.labels_back);
		editLabel = (TextView) findViewById(R.id.edit_label);
		labelIcon = (ImageView) findViewById(R.id.label_icon);
		labelName = (TextView) findViewById(R.id.label_name);
		memberCount = (TextView) findViewById(R.id.member_count);
		addMemberLayout = (LinearLayout) findViewById(R.id.add_member_layout);
		addMember = (Button) findViewById(R.id.add_member);
		labelMembers = (ListView) findViewById(R.id.member_list);
		deleteLabel = (Button) findViewById(R.id.delete_label);
		checkState();
	}

	void initViewList() {
		//cursor,contactsAdapter,labelMembers
		CursorUtils.closeExistsCursor(cursorEdit);
		CursorUtils.closeExistsCursor(cursorView);
		cursorView = allTableUtils.selectAllIDNameOnLabel(labelObj.getLabelName());
		ContactsAdapter adapter = new ContactsAdapter(this, cursorView);
		labelMembers.setAdapter(adapter);

	}

	void initEditList() {
		getLabelMemberCount();
		//cursor,contactsAdapter,labelMembers
		CursorUtils.closeExistsCursor(cursorEdit);
		CursorUtils.closeExistsCursor(cursorView);
		cursorEdit = allTableUtils.selectAllIDNameOnLabel(labelObj.getLabelName());
		MemEditAdapter adapter = new MemEditAdapter(this, cursorEdit, labelObj.getLabelName());
		labelMembers.setAdapter(adapter);

	}

	private void getLabelMemberCount() {
		Cursor cursorCount = labelTableUtils.selectMemCount(labelObj.getLabelName());
		try {
			cursorCount.moveToNext();
			String count = cursorCount.getString(cursorCount.getColumnIndex(TBLabelConstants.MEMBER_COUNT));
			memberCount.setText(StringUtils.addBrackets(count));
		} finally {
			CursorUtils.closeExistsCursor(cursorCount);
			labelTableUtils.closeDataBase();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.labels_back:

				finish();
				break;
			case R.id.edit_label:
				inEdit = !inEdit;
				checkState();
				break;
			case R.id.add_member:

//				allTableUtils.insertAll("1", labelObj.getLabelName());
//
//				initEditList();
//				break;
			case R.id.add_member_layout:

				ActivityUtils.startActivityWithObjectForResult
						(this, SearchActivity.class, Signal.NAME,
								new Signal(ActivityConstants.LABEL_DETAIL_ACTIVITY, ActivityConstants.SEARCH_ACTIVITY),
								ActivityConstants.RESULT_ADD_MEMBER);
				break;

			case R.id.delete_label:
				PopConfirmUtils popConfirmUtils = new PopConfirmUtils();
				popConfirmUtils.prepare(this, R.layout.pop_confirm);
				popConfirmUtils.initPopupWindow();
				popConfirmUtils.setTitle("Sure to delete?");
				popConfirmUtils.initTodo(new TodoOnResult() {
					@Override
					public void doOnPosResult(String[] params) {
						labelTableUtils.deleteWithLabel(labelObj.getLabelName());
						getLabelMemberCount();
						finish();
					}

					@Override
					public void doOnNegResult(String[] params) {

					}
				});
				popConfirmUtils.popWindowAtCenter(R.id.member_list, R.id.confirm_title);
				break;

			case R.id.label_icon:
			case R.id.label_name:
				popEditLabelUtils.popEditLabel();
				break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case ActivityConstants.RESULT_LOAD_IMAGE:
				popEditLabelUtils.handleResult(requestCode, resultCode, data);
				break;
			case ActivityConstants.RESULT_ADD_MEMBER:
				Bundle bundle = data.getExtras();
				int id = ((IdObj) bundle.getSerializable(BundleNames.ID_OBJ)).getId();
				if (id != 0) {
					long status = idLabelTableUtils.insertAll("" + id, labelObj.getLabelName());
					if (status < 0)
						Toast.makeText(this, "添加联系人标签失败，\n是否已经在标签中？", Toast.LENGTH_LONG).show();
					initEditList();
				}
				break;
		}

	}

	protected void onPause() {
		super.onPause();
		CursorUtils.closeExistsCursor(cursorEdit);
		CursorUtils.closeExistsCursor(cursorView);
		allTableUtils.closeDataBase();

	}

	protected void onStop() {
		super.onStop();
		CursorUtils.closeExistsCursor(cursorEdit);
		CursorUtils.closeExistsCursor(cursorView);
		allTableUtils.closeDataBase();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		CursorUtils.closeExistsCursor(cursorEdit);
		CursorUtils.closeExistsCursor(cursorView);
		allTableUtils.closeDataBase();
	}

}
