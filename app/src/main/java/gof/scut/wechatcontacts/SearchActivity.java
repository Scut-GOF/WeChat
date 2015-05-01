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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import gof.scut.common.utils.ActivityUtils;
import gof.scut.common.utils.BundleNames;
import gof.scut.common.utils.StringUtils;
import gof.scut.common.utils.database.CursorUtils;
import gof.scut.common.utils.database.MainTableUtils;
import gof.scut.common.utils.database.TBLabelConstants;
import gof.scut.common.utils.database.TBMainConstants;
import gof.scut.common.utils.database.TBTelConstants;
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
	private Cursor cursorResult;


	final Semaphore semaphore = new Semaphore(1, true);

	String keyword;


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

				keyword = s.toString();
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
		CursorUtils.closeExistsCursor(cursorResult);
		//非数字不搜索电话
		if (!StringUtils.isNumber(keyword))
			cursorResult = mainTableUtils.fullTextSearchWithWord(keyword);
		else cursorResult = mainTableUtils.fullTextSearchWithNumOrWord(keyword);

		if (cursorResult.getCount() == 0) semaphore.release();

		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				fullTextSearchSetUI();
			}
		});

	}

	void fullTextSearchSetUI() {
		if (keyword.equals("")) {
			return;
		}
		List<SearchObj> results = new ArrayList<>();
		try {

			for (int i = 0; i < cursorResult.getCount(); i++) {
				cursorResult.moveToPosition(i);
				results.add(new SearchObj(
						cursorResult.getString(cursorResult.getColumnIndex(TBMainConstants.ID)),
						cursorResult.getString(cursorResult.getColumnIndex(TBMainConstants.NAME)),
						cursorResult.getString(cursorResult.getColumnIndex(TBMainConstants.L_PINYIN)),
						cursorResult.getString(cursorResult.getColumnIndex(TBMainConstants.S_PINYIN)),
						cursorResult.getString(cursorResult.getColumnIndex(TBMainConstants.ADDRESS)),
						cursorResult.getString(cursorResult.getColumnIndex(TBMainConstants.NOTES)),
						cursorResult.getString(cursorResult.getColumnIndex(TBLabelConstants.LABEL)),
						cursorResult.getString(cursorResult.getColumnIndex(TBTelConstants.TEL))
				));
			}
		} finally {
			CursorUtils.closeExistsCursor(cursorResult);
		}
		SearchResultAdapter searchResultAdapter
				= new SearchResultAdapter(this, results, keyword, getFromActivity());
		searchResult.setAdapter(searchResultAdapter);
		semaphore.release();

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
