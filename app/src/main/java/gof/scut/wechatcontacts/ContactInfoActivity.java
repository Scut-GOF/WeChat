package gof.scut.wechatcontacts;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import gof.scut.common.utils.database.CursorUtils;
import gof.scut.common.utils.database.MainTableUtils;
import gof.scut.common.utils.database.TBMainConstants;
import gof.scut.common.utils.database.TelTableUtils;
import gof.scut.cwh.models.object.IdObj;
import gof.scut.fental.models.adapter.PhonesAdapter;


public class ContactInfoActivity extends Activity {

	private TextView tvName;
	private TextView tvAddress;
	private TextView tvNotes;
	private ListView lvTels;
	private int id;

	Cursor cursorTels;
	TelTableUtils telTableUtils;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_info);
		tvName = (TextView) findViewById(R.id.tv_name);
		tvAddress = (TextView) findViewById(R.id.tv_address);
		tvNotes = (TextView) findViewById(R.id.tv_notes);
		lvTels = (ListView) findViewById(R.id.lv_tels);
		Bundle bundle = new Bundle();
		bundle = this.getIntent().getExtras();
		id = ((IdObj) bundle.getSerializable("IdObj")).getId();

		MainTableUtils mainTableUtils = new MainTableUtils(this);
		Cursor allInfoCursor = mainTableUtils.selectAllWithID("" + id);
		allInfoCursor.moveToNext();
		String name = allInfoCursor.getString(allInfoCursor.getColumnIndex(TBMainConstants.NAME));
		String address = allInfoCursor.getString(allInfoCursor.getColumnIndex(TBMainConstants.ADDRESS));
		String notes = allInfoCursor.getString(allInfoCursor.getColumnIndex(TBMainConstants.NOTES));
		allInfoCursor.close();
		mainTableUtils.closeDataBase();
		tvName.setText(name);
		tvAddress.setText(address);
		tvNotes.setText(notes);
	}

	private void initList() {
		telTableUtils = new TelTableUtils(this);
		cursorTels = telTableUtils.selectTelWithID("" + id);
		PhonesAdapter phonesAdapter = new PhonesAdapter(this, cursorTels);
		lvTels.setAdapter(phonesAdapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_contact_info, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	protected void onResume() {
		super.onResume();
		initList();
	}

	protected void onPause() {
		super.onPause();
		if (cursorTels != null) cursorTels.close();
		telTableUtils.closeDataBase();
	}

	protected void onStop() {
		super.onStop();
		if (cursorTels != null) cursorTels.close();
		telTableUtils.closeDataBase();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		CursorUtils.closeExistsCursor(cursorTels);
		telTableUtils.closeDataBase();
	}

}
