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

import gof.scut.common.utils.ActivityUtils;
import gof.scut.common.utils.BitmapUtils;
import gof.scut.common.utils.BundleNames;
import gof.scut.common.utils.Log;
import gof.scut.common.utils.StringUtils;
import gof.scut.common.utils.database.AllTableUtils;
import gof.scut.common.utils.database.IDLabelTableUtils;
import gof.scut.common.utils.database.LabelTableUtils;
import gof.scut.common.utils.popup.PopConfirmUtils;
import gof.scut.common.utils.popup.PopEditLabelUtils;
import gof.scut.common.utils.popup.TodoOnResult;
import gof.scut.cwh.models.adapter.ContactsAdapter;
import gof.scut.cwh.models.adapter.MemEditAdapter;
import gof.scut.cwh.models.object.LabelObj;


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
		initViewList();
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
			labelIcon.setBackgroundResource(R.drawable.chart_1_2);
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
				long state = labelTableUtils.updateAllWithLabel(labelObj, labelName.getText().toString());
				if (state < 0) Log.e("LabelsActivity", "update label failed");
				labelIcon.setImageBitmap(BitmapUtils.decodeBitmapFromPath(labelObj.getIconPath()));
				labelName.setText(labelObj.getLabelName());
			}

			@Override
			public void doOnNegResult(String[] params) {

			}
		}, "编辑标签", R.id.label_detail_layout);
		findViewById(R.id.barRelativeLayout).setEnabled(true);
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
		addMemberLayout.setOnClickListener(this);
		findViewById(R.id.add_id_to_label).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				idLabelTableUtils.insertAll("0", "好人");
			}
		});
		findViewById(R.id.delete_id_label).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				idLabelTableUtils.deleteWithID("0");
			}
		});
		findViewById(R.id.update_id_label).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				idLabelTableUtils.updateLabelWithID("坏人", "0");
			}
		});

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
		cursorView = allTableUtils.selectAllIDNameOnLabel(labelObj.getLabelName());
		ContactsAdapter adapter = new ContactsAdapter(this, cursorView);
		labelMembers.setAdapter(adapter);

	}

	void initEditList() {

		//cursor,contactsAdapter,labelMembers
		cursorEdit = allTableUtils.selectAllIDNameOnLabel(labelObj.getLabelName());
		MemEditAdapter adapter = new MemEditAdapter(this, cursorEdit, labelObj.getLabelName());
		labelMembers.setAdapter(adapter);

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
				//TODO it's a test
				idLabelTableUtils.insertAll("1", labelObj.getLabelName());
				initEditList();
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
						//TODO add trigger to delete id-label relation
						finish();
					}

					@Override
					public void doOnNegResult(String[] params) {

					}
				});
				popConfirmUtils.popWindowAtCenter(R.id.member_list, R.id.confirm_title);
				break;
			case R.id.add_member_layout:
				ActivityUtils.ActivitySkip(this, SearchActivity.class);
				break;
			case R.id.label_icon:
				popEditLabelUtils.popEditLabel();
				break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		popEditLabelUtils.handleResult(requestCode, resultCode, data);
	}

	protected void onDestroy() {
		super.onDestroy();
		if (cursorEdit != null) cursorEdit.close();
		if (cursorView != null) cursorView.close();
	}
}
