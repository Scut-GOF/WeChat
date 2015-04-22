package gof.scut.cwh.models.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import gof.scut.common.utils.database.AllTableUtils;
import gof.scut.common.utils.database.IDLabelTableUtils;
import gof.scut.common.utils.database.TBMainConstants;
import gof.scut.common.utils.popup.PopConfirmUtils;
import gof.scut.common.utils.popup.TodoOnResult;
import gof.scut.wechatcontacts.R;

/**
 * Created by Administrator on 2015/4/18.
 */
public class MemEditAdapter extends BaseAdapter {
	private Context context;
	private Cursor cursor;
	private String labelName;

	public MemEditAdapter(Context context, Cursor cursor, String labelName) {
		this.context = context;
		this.cursor = cursor;
		this.labelName = labelName;
	}

	@Override
	public int getCount() {
		return cursor.getCount();
	}

	@Override
	public Object getItem(int position) {
		return cursor.getPosition();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View layout, ViewGroup parent) {
		cursor.moveToPosition(position);
		LayoutInflater inflater = LayoutInflater.from(context);
		//layout = (LinearLayout) inflater.inflate(R.layout.cell_label_grid, null);
		layout = inflater.inflate(R.layout.cell_edit_member_list, parent, false);
		Button removeMember;
		TextView memberName;
		removeMember = (Button) layout.findViewById(R.id.remove_member);
		memberName = (TextView) layout.findViewById(R.id.name);
		final String strMemberName = cursor.getString(cursor.getColumnIndex(TBMainConstants.NAME));
		final String strMemberID = cursor.getString(cursor.getColumnIndex(TBMainConstants.ID));
		memberName.setText(strMemberName);
		removeMember.setOnClickListener(new View.OnClickListener() {
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
						AllTableUtils allTableUtils = new AllTableUtils(context);
						cursor = allTableUtils.selectAllIDNameOnLabel(labelName);
						MemEditAdapter.this.notifyDataSetChanged();
					}

					@Override
					public void doOnNegResult(String[] params) {

					}
				});
				popConfirmUtils.popWindowAtCenter(R.id.member_list, R.id.confirm_title);

			}
		});
		return layout;
	}

}
