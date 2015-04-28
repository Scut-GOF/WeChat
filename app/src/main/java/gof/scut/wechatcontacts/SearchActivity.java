package gof.scut.wechatcontacts;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import gof.scut.common.utils.Log;
import gof.scut.common.utils.StringUtils;
import gof.scut.common.utils.database.CursorUtils;
import gof.scut.common.utils.database.MainTableUtils;
import gof.scut.cwh.models.adapter.SearchResultAdapter;


public class SearchActivity extends Activity {

	EditText searchKey;
	ListView searchResult;
	MainTableUtils mainTableUtils;
	Cursor cursorResult;

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
		initDataBase();
		findView();
		setListener();
	}

	void initDataBase() {
		mainTableUtils = new MainTableUtils(this);
	}

	void findView() {
		searchKey = (EditText) findViewById(R.id.search_key);
		searchResult = (ListView) findViewById(R.id.search_result);
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
	}

	void fullTextSearch(String keyword) {
//		Log.d("Search", keyword);
		if (keyword.equals("")) return;
		CursorUtils.closeExistsCursor(cursorResult);
		//非数字不搜索电话
		if (!StringUtils.isNumber(keyword))
			cursorResult = mainTableUtils.fullTextSearchWithWord(keyword);
		else cursorResult = mainTableUtils.fullTextSearchWithNumOrWord(keyword);
		SearchResultAdapter searchResultAdapter = new SearchResultAdapter(this, cursorResult, keyword);
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
}
