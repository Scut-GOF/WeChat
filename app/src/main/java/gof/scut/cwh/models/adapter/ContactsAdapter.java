package gof.scut.cwh.models.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import gof.scut.common.utils.ActivityUtils;
import gof.scut.common.utils.BundleNames;
import gof.scut.common.utils.UseSystemUtils;
import gof.scut.common.utils.database.CursorUtils;
import gof.scut.common.utils.database.MainTableUtils;
import gof.scut.common.utils.database.TBTelConstants;
import gof.scut.common.utils.database.TelTableUtils;
import gof.scut.common.utils.popup.PopConfirmUtils;
import gof.scut.common.utils.popup.TodoOnResult;
import gof.scut.cwh.models.object.IdObj;
import gof.scut.cwh.models.object.LightIdObj;
import gof.scut.wechatcontacts.ContactInfoActivity;
import gof.scut.wechatcontacts.R;


public class ContactsAdapter extends BaseAdapter {
	private Context context;
	//	private Cursor cursor;
	List<LightIdObj> contacts;
	private LinearLayout layout;
	PopupWindow telChoiceWindow = null;
	View telChoiceView = null;
	LayoutInflater inflater;

	public ContactsAdapter(Context context, List<LightIdObj> contacts) {
		this.context = context;
		this.contacts = contacts;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return contacts.size();
	}

	@Override
	public Object getItem(int position) {
		return contacts.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	static class ViewHolder {
		TextView name;
		Button msg;
		Button call;
	}

	@Override
	public View getView(int position, View convertView, final ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.cell_main_list, null);
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.msg = (Button) convertView.findViewById(R.id.send_msg);
			holder.call = (Button) convertView.findViewById(R.id.call);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		layout = (LinearLayout) convertView;
//
//		TextView name = (TextView) convertView.findViewById(R.id.name);
//
//		Button msg = (Button) convertView.findViewById(R.id.send_msg);
//		Button call = (Button) convertView.findViewById(R.id.call);

		LightIdObj contact = contacts.get(position);
		final String cName = contact.getName();
		final String id = contact.getId();

		//more details got when view
		//....

		holder.name.setText(cName);

		holder.msg.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				TelTableUtils telTableUtil = new TelTableUtils(context);
//				telTableUtil.insertAll("" + id, "10086");
//				telTableUtil.insertAll("" + id, "10086" + id);

				Cursor cTel = telTableUtil.selectTelWithID(id);
				//close when dismiss
				if (cTel.getCount() > 1) {
					initPopChoice(TelsAdapter.CHOICE_MSG, cTel, telTableUtil);
					popPhoneSelector();
				} else if (cTel.getCount() == 1) {
					ArrayList<String> phoneList = new ArrayList<>();
					CursorUtils.cursorToStringArray(cTel, phoneList, TBTelConstants.TEL);
					UseSystemUtils.sendMsg(context, phoneList.get(0));
				}


			}
		});
		holder.call.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				TelTableUtils telTableUtil = new TelTableUtils(context);

//				telTableUtil.insertAll("" + id, "10086");
//				telTableUtil.insertAll("" + id, "10086" + id);

				Cursor cTel = telTableUtil.selectTelWithID(id);
				//close when dismiss
				if (cTel.getCount() > 1) {
					initPopChoice(TelsAdapter.CHOICE_CALL, cTel, telTableUtil);
					popPhoneSelector();
				} else if (cTel.getCount() == 1) {
					ArrayList<String> phoneList = new ArrayList<>();
					CursorUtils.cursorToStringArray(cTel, phoneList, TBTelConstants.TEL);
					UseSystemUtils.sysCall(context, phoneList.get(0));
				}


			}
		});
		holder.name.setClickable(true);
		holder.name.setFocusable(true);
		holder.name.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onItemClick(Integer.parseInt(id));
			}
		});
		holder.name.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				PopConfirmUtils popConfirmUtils = new PopConfirmUtils();
				popConfirmUtils.prepare(context, R.layout.pop_confirm);
				popConfirmUtils.initPopupWindow();
				popConfirmUtils.setTitle("Delete this contact?");
				popConfirmUtils.initTodo(new TodoOnResult() {
					@Override
					public void doOnPosResult(String[] params) {
						MainTableUtils mainTableUtils = new MainTableUtils(context);
						mainTableUtils.deleteWithID(id + "");
						contacts = mainTableUtils.selectAllIDName();
						notifyDataSetChanged();
					}

					@Override
					public void doOnNegResult(String[] params) {

					}
				});
				popConfirmUtils.popWindowAtCenter(R.id.name, R.id.confirm_title);
				return false;
			}
		});
		return convertView;
	}

	private void onItemClick(int id) {
		IdObj obj = new IdObj(id);
		ActivityUtils.ActivitySkipWithObject(context, ContactInfoActivity.class, BundleNames.ID_OBJ, obj);
	}

	public void popPhoneSelector() {
		telChoiceWindow.update();
		telChoiceWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
		Animation anim1 = AnimationUtils.loadAnimation(context, R.anim.scale_center_enter);
		telChoiceView.findViewById(R.id.phone_choices).startAnimation(anim1);
	}

	private void initPopChoice(int telOrMsg, final Cursor cursor, final TelTableUtils telTableUtil) {
		telChoiceView = LayoutInflater.from(context).inflate(
				R.layout.pop_phone_choice, null, false);
		telChoiceWindow = new PopupWindow(telChoiceView,
				ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);

		ColorDrawable dw = new ColorDrawable(0x00000000);
		telChoiceView.setBackgroundDrawable(dw);

		TextView telsTitle = (TextView) telChoiceView.findViewById(R.id.list_title);
		if (TelsAdapter.CHOICE_MSG == telOrMsg) telsTitle.setText("Message");
		else if (TelsAdapter.CHOICE_CALL == telOrMsg) telsTitle.setText("Call");

		ListView telList = (ListView) telChoiceView.findViewById(R.id.phone_list);
		List<String> tels = new ArrayList<>();
		for (int i = 0; i < cursor.getCount(); i++) {
			cursor.moveToPosition(i);
			tels.add(cursor.getString(cursor.getColumnIndex(TBTelConstants.TEL)));
		}
		cursor.close();
		TelsAdapter telsAdapter = new TelsAdapter(context, telOrMsg, telChoiceWindow, tels);
		telList.setAdapter(telsAdapter);

		telChoiceView.setFocusable(true);
		telChoiceView.setFocusableInTouchMode(true);
		telChoiceView.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
				if (arg1 == KeyEvent.KEYCODE_BACK) {
					telChoiceWindow.dismiss();
					return true;
				}
				return false;
			}
		});
		telChoiceView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				telChoiceWindow.dismiss();
			}

		});

		telChoiceWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			@Override
			public void onDismiss() {

				closeTelDataBase(telTableUtil);
			}

		});

	}

	void closeTelDataBase(TelTableUtils telTableUtils) {
		telTableUtils.closeDataBase();
	}
}
