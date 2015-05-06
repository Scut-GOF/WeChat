package gof.scut.wechatcontacts;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;
import java.util.concurrent.Semaphore;

import gof.scut.common.utils.ActivityUtils;
import gof.scut.common.utils.BundleNames;
import gof.scut.common.utils.StringUtils;
import gof.scut.common.utils.database.MainTableUtils;
import gof.scut.cwh.models.adapter.SearchResultAdapter;
import gof.scut.cwh.models.object.ActivityConstants;
import gof.scut.cwh.models.object.IdObj;
import gof.scut.cwh.models.object.SearchObj;
import gof.scut.cwh.models.object.Signal;


public class SearchActivity extends Activity implements View.OnClickListener {
	private int fromActivity;

	private EditText searchKey;
	private Button cancel;
	private ListView searchResult;
	private MainTableUtils mainTableUtils;
	//	private Cursor cursorResult;
	List<SearchObj> results;


	final Semaphore semaphore = new Semaphore(1, true);


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		init();

	}

	@Override
	protected void onResume() {
		super.onResume();
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

			}

			@Override
			public void afterTextChanged(Editable s) {

				final String keyword = s.toString().replace("龘", " ");
				new Thread() {
					public void run() {
						fullTextSearch(keyword);
					}
				}.start();
			}
		});
		cancel.setOnClickListener(this);
	}

	void fullTextSearch(String keyword) {
		if (keyword.equals("")) {
			return;
		}

		try {
			semaphore.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
//		CursorUtils.closeExistsCursor(cursorResult);
		//非数字不搜索电话
		if (!StringUtils.isNumber(keyword))
			results = mainTableUtils.fullTextSearchWithWord(keyword);
		else results = mainTableUtils.fullTextSearchWithNumOrWord(keyword);

		if (results.size() == 0) semaphore.release();
		final String kwToUI = keyword;
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				fullTextSearchSetUI(kwToUI);
			}
		});

	}

	void fullTextSearchSetUI(String keyword) {
		SearchResultAdapter searchResultAdapter
				= new SearchResultAdapter(this, results, keyword, getFromActivity());
		searchResult.setAdapter(searchResultAdapter);
		semaphore.release();

	}

//	@Override
//	protected void onPause() {
//		super.onPause();
//		returnEmptyObj();
//	}
//
//	protected void onStop() {
//		super.onStop();
//		returnEmptyObj();
//	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
//		returnEmptyObj();
		semaphore.release();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.cancel:
				returnEmptyObj();
				finish();
				break;
		}
	}

	private void returnEmptyObj() {
		if (getFromActivity() == ActivityConstants.LABEL_DETAIL_ACTIVITY) {
			ActivityUtils.setActivityResult(
					this, ActivityConstants.RESULT_ADD_MEMBER, BundleNames.ID_OBJ,
					new IdObj(0));
		}
		semaphore.release();

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getRepeatCount() == 0) {
			returnEmptyObj();
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
