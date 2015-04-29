package gof.scut.wechatcontacts;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import gof.scut.common.utils.ActivityUtils;
import gof.scut.common.utils.database.CursorUtils;
import gof.scut.common.utils.database.MainTableUtils;
import gof.scut.cwh.models.adapter.ContactsAdapter;
import gof.scut.cwh.models.object.ActivityConstants;
import gof.scut.cwh.models.object.Signal;


public class MainActivity extends Activity implements View.OnClickListener {

	//Views
	private Button btSearch;

	private ListView contacts;
	private Button btAdd, btGroup, btMe;


	//Constants

	//Models
	Cursor cursor;
	MainTableUtils mainTableUtils;

	//Controllers


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}

	private void init() {
		//init database
		initDatabase();
		//init Views
		findViews();
		//init handlers //init button listeners, list
		eventHandler();
	}

	private void initDatabase() {
		mainTableUtils = new MainTableUtils(this);

	}

	private void findViews() {
		btSearch = (Button) findViewById(R.id.bt_search);


		contacts = (ListView) findViewById(R.id.search_list);
		btAdd = (Button) findViewById(R.id.bt_add);
		btGroup = (Button) findViewById(R.id.bt_group);
		btMe = (Button) findViewById(R.id.bt_me);
	}

	private void eventHandler() {
		btSearch.setOnClickListener(this);
		btAdd.setOnClickListener(this);
		btGroup.setOnClickListener(this);
		btMe.setOnClickListener(this);

	}

	private void initList() {
		//When clickOnNameAndTel, view
		//When click on image, view label group
		//edit when view
		CursorUtils.closeExistsCursor(cursor);
		cursor = mainTableUtils.selectAllIDName();
		ContactsAdapter adapter = new ContactsAdapter(this, cursor);
		contacts.setAdapter(adapter);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
			case R.id.bt_search:
				ActivityUtils.ActivitySkipWithObject(this, SearchActivity.class, Signal.NAME,
						new Signal(ActivityConstants.MAIN_ACTIVITY, ActivityConstants.SEARCH_ACTIVITY));

				break;
			case R.id.bt_add:
				//TO ADD CONTACTS ACTIVITY
				ActivityUtils.ActivitySkip(this, AddContactActivity.class);
				break;
			case R.id.bt_group:
				//TO GROUP VIEW
				ActivityUtils.ActivitySkip(this, LabelsActivity.class);
				break;
			case R.id.bt_me:
				//TO SELF ACTIVITY
				ActivityUtils.ActivitySkip(this, SelfInfoActivity.class);
				break;
		}
	}

	//	@Override
//	protected void onRestart(){
//		super.onRestart();
//		initList();
//	}
	@Override
	protected void onResume() {
		super.onResume();
		initList();
	}

	@Override
	protected void onPause() {
		super.onPause();
		CursorUtils.closeExistsCursor(cursor);
		mainTableUtils.closeDataBase();
	}

	protected void onStop() {
		super.onStop();
		CursorUtils.closeExistsCursor(cursor);
		mainTableUtils.closeDataBase();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		CursorUtils.closeExistsCursor(cursor);
		mainTableUtils.closeDataBase();
	}
}
