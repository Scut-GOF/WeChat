package gof.scut.wechatcontacts;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import gof.scut.common.utils.ActivityUtils;
import gof.scut.common.utils.BundleNames;
import gof.scut.common.utils.StringUtils;
import gof.scut.common.utils.database.CursorUtils;
import gof.scut.common.utils.database.MainTableUtils;
import gof.scut.cwh.models.adapter.SearchResultAdapter;
import gof.scut.cwh.models.object.ActivityConstants;
import gof.scut.cwh.models.object.IdObj;
import gof.scut.cwh.models.object.Signal;


public class SearchActivity extends Activity implements View.OnClickListener {

	private EditText searchKey;
	private Button cancel;
	private ListView searchResult;
	private MainTableUtils mainTableUtils;
	private Cursor cursorResult;

	private int fromActivity;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		init();

	}

	@Override
	protected void onResume() {
		super.onResume();
		fullTextSearch(searchKey.getText().toString());
	}

	void init() {
		callTypeCheck();
		initDataBase();
		findView();
		setListener();
	}

	void callTypeCheck() {
		//可能调用搜索的地方有main，label detail,
		Bundle bundle = getIntent().getExtras();
		setFromActivity(((Signal) bundle.getSerializable(Signal.NAME)).getFrom());
	}

	public int getFromActivity() {
		return fromActivity;
	}

	public void setFromActivity(int fromActivity) {
		this.fromActivity = fromActivity;
	}

	void initDataBase() {
		mainTableUtils = new MainTableUtils(this);
	}

	void findView() {
		searchKey = (EditText) findViewById(R.id.search_key);
		searchResult = (ListView) findViewById(R.id.search_result);
		cancel = (Button) findViewById(R.id.cancel);
	}

	void setListener() {
		searchKey.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				fullTextSearch(s.toString());
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		cancel.setOnClickListener(this);
	}

	void fullTextSearch(String keyword) {
//		Log.d("Search", keyword);
		if (keyword.equals("")) return;
		CursorUtils.closeExistsCursor(cursorResult);
		//非数字不搜索电话
		if (!StringUtils.isNumber(keyword))
			cursorResult = mainTableUtils.fullTextSearchWithWord(keyword);
		else cursorResult = mainTableUtils.fullTextSearchWithNumOrWord(keyword);
		SearchResultAdapter searchResultAdapter
				= new SearchResultAdapter(this, cursorResult, keyword, getFromActivity());
		searchResult.setAdapter(searchResultAdapter);
	}

	@Override
	protected void onPause() {
		super.onPause();
		CursorUtils.closeExistsCursor(cursorResult);
		mainTableUtils.closeDataBase();
	}

	protected void onStop() {
		super.onStop();
		CursorUtils.closeExistsCursor(cursorResult);
		mainTableUtils.closeDataBase();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		CursorUtils.closeExistsCursor(cursorResult);
		mainTableUtils.closeDataBase();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.cancel:
				if (getFromActivity() == ActivityConstants.LABEL_DETAIL_ACTIVITY) {
					ActivityUtils.setActivityResult(
							this, ActivityConstants.RESULT_ADD_MEMBER, BundleNames.ID_OBJ,
							new IdObj(0));
				}
				finish();
				break;
		}
	}
}
