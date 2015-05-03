package gof.scut.cwh.models.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import gof.scut.common.utils.database.IDLabelTableUtils;
import gof.scut.common.utils.popup.PopConfirmUtils;
import gof.scut.common.utils.popup.TodoOnResult;
import gof.scut.cwh.models.object.LightIdObj;
import gof.scut.wechatcontacts.R;


public class MemEditAdapter extends BaseAdapter {
	private Context context;
	//	private Cursor cursor;
	List<LightIdObj> members;
	private String labelName;
	LayoutInflater inflater;

	public MemEditAdapter(Context context, String labelName, List<LightIdObj> members) {
		this.context = context;
		this.members = members;
		this.labelName = labelName;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return members.size();
	}

	@Override
	public Object getItem(int position) {
		return members.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	static class ViewHolder {
		Button removeMember;
		TextView memberName;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LightIdObj memeber = members.get(position);
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.cell_edit_member_list, null);
			holder = new ViewHolder();
			holder.removeMember = (Button) convertView.findViewById(R.id.remove_member);
			holder.memberName = (TextView) convertView.findViewById(R.id.name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}


		holder.removeMember = (Button) convertView.findViewById(R.id.remove_member);
		holder.memberName = (TextView) convertView.findViewById(R.id.name);
		final String strMemberName = memeber.getName();
		final String strMemberID = memeber.getId();
		holder.memberName.setText(strMemberName);
		holder.removeMember.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PopConfirmUtils popConfirmUtils = new PopConfirmUtils();
				popConfirmUtils.prepare(context, R.layout.pop_confirm);
				popConfirmUtils.initPopupWindow();
				popConfirmUtils.setTitle("Sure to delete?");
				popConfirmUtils.initTodo(new TodoOnResult() {
					@Override
					public void doOnPosResult(String[] params) {
						IDLabelTableUtils idLabelTableUtils = new IDLabelTableUtils(context);
						idLabelTableUtils.deleteWithID_Label(strMemberID, labelName);
						members.remove(position);
						MemEditAdapter.this.notifyDataSetChanged();
					}

					@Override
					public void doOnNegResult(String[] params) {

					}
				});
				popConfirmUtils.popWindowAtCenter(R.id.member_list, R.id.confirm_title);

			}
		});
		return convertView;
	}

}
