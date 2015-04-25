package gof.scut.wechatcontacts;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import gof.scut.common.utils.Log;


public class SearchActivity extends Activity {

	TextView searchKey;
	ListView searchResult;

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

	}

	void findView() {
		searchKey = (TextView) findViewById(R.id.search_key);
		searchResult = (ListView) findViewById(R.id.search_result);
	}

	void setListener() {
		searchKey.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.equals("")) return;
				fullTextSearch(s.toString());
			}
		});
	}

	void fullTextSearch(String condition) {
		Log.d("Search", condition);

	}

}
