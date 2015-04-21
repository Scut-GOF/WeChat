package gof.scut.wechatcontacts;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;


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
		searchKey.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				//TODO call when change text
				fullTextSearch(v.getText().toString());
				return false;
			}
		});
	}

	void fullTextSearch(String condition) {

	}

}
