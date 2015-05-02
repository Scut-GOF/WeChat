package gof.scut.cwh.models.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.PopupWindow;

import java.util.List;

import gof.scut.common.utils.UseSystemUtils;
import gof.scut.wechatcontacts.R;


public class TelsAdapter extends BaseAdapter {
	private Context context;
	//	private Cursor cursor;
	private List<String> telList;
	private LayoutInflater inflater;


	public final static int CHOICE_CALL = 0;
	public final static int CHOICE_MSG = 1;
	private int telOrMsg;
	private PopupWindow parentWindow;

	public TelsAdapter(Context context, int choice, PopupWindow parentWindow, List<String> telList) {
		this.context = context;
		this.telList = telList;
		this.telOrMsg = choice;
		this.parentWindow = parentWindow;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return telList.size();
	}

	@Override
	public Object getItem(int position) {
		return telList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	static class ViewHolder {
		Button tel;
	}

	@Override
	public View getView(int position, View convertView, final ViewGroup parent) {


		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.cell_phone_chose_pop, null);
			holder = new ViewHolder();
			holder.tel = (Button) convertView.findViewById(R.id.tel_number);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}


		final String cTel = telList.get(position);
		holder.tel.setText(cTel);
		holder.tel.setOnClickListener(new View.OnClickListener() {


			@Override
			public void onClick(View v) {
				if (CHOICE_MSG == telOrMsg)
					UseSystemUtils.sendMsg(context, cTel);
				else if (CHOICE_CALL == telOrMsg) UseSystemUtils.sysCall(context, cTel);
				parentWindow.dismiss();
			}
		});

		return convertView;
	}
}
