package gof.scut.wechatcontacts;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import gof.scut.common.utils.database.CursorUtils;
import gof.scut.common.utils.database.MainTableUtils;
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
		IdObj contact = mainTableUtils.selectAllWithID("" + id);
		if (contact.getId() < 0) {
			Toast.makeText(ContactInfoActivity.this, "联系人不存在！", Toast.LENGTH_LONG).show();
			return;
		}
		String name = contact.getName();
		String address = contact.getAddress();
		String notes = contact.getNotes();
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
