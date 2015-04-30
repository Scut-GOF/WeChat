package gof.scut.cwh.models.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import gof.scut.common.utils.ActivityUtils;
import gof.scut.common.utils.BitmapUtils;
import gof.scut.common.utils.BundleNames;
import gof.scut.common.utils.database.IDLabelTableUtils;
import gof.scut.common.utils.database.TBLabelConstants;
import gof.scut.cwh.models.object.ActivityConstants;
import gof.scut.cwh.models.object.IdObj;
import gof.scut.cwh.models.object.LabelListObj;
import gof.scut.cwh.models.object.LabelObj;
import gof.scut.wechatcontacts.LabelDetailActivity;
import gof.scut.wechatcontacts.R;


/**
 * Created by Administrator on 2015/4/10.
 */
public class LabelsAdapter extends BaseAdapter {
	private Context context;
	private Cursor cursor;
	private LinearLayout layout;
	private int fromActivity;
	private LabelListObj labels;
	private IdObj contact;
//	private ContactLabelAdapter existsLabels;

	public LabelsAdapter(Context context, Cursor cursor, int fromActivity) {
		this.context = context;
		this.cursor = cursor;
		this.fromActivity = fromActivity;
	}

	public LabelsAdapter(Context context, Cursor cursor, int fromActivity, LabelListObj labels) {
		this.context = context;
		this.cursor = cursor;
		this.fromActivity = fromActivity;
		this.labels = labels;
	}

	public LabelsAdapter(Context context, Cursor cursor, int fromActivity, LabelListObj labels, IdObj contact) {
		this.context = context;
		this.cursor = cursor;
		this.fromActivity = fromActivity;
		this.labels = labels;
		this.contact = contact;
	}

	public LabelsAdapter(Context context, Cursor cursor) {
		fromActivity = ActivityConstants.MAIN_ACTIVITY;
		this.context = context;
		this.cursor = cursor;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		cursor.moveToPosition(position);
		LayoutInflater inflater = LayoutInflater.from(context);
		//layout = (LinearLayout) inflater.inflate(R.layout.cell_label_grid, null);
		layout = (LinearLayout) inflater.inflate(R.layout.cell_label_list, parent, false);
		final ImageView labelIcon = (ImageView) layout.findViewById(R.id.label_icon);
		final TextView labelName = (TextView) layout.findViewById(R.id.label_name);
		final TextView memberCount = (TextView) layout.findViewById(R.id.member_count);

		final String strLabelName = cursor.getString(cursor.getColumnIndex(TBLabelConstants.LABEL));
		final String iconPath = cursor.getString(cursor.getColumnIndex(TBLabelConstants.LABEL_ICON));
		final String strMemberCount = cursor.getString(cursor.getColumnIndex(TBLabelConstants.MEMBER_COUNT));

		labelName.setText(strLabelName);
		memberCount.setText("(" + strMemberCount + ")");
		labelIcon.setBackgroundDrawable(null);
		if (iconPath.equals("")) labelIcon.setBackgroundResource(R.drawable.label50);
		else labelIcon.setImageBitmap(BitmapUtils.decodeBitmapFromPath(iconPath));


		if (fromActivity == ActivityConstants.ADD_CONTACTS_ACTIVITY || fromActivity == ActivityConstants.CONTACT_INFO_ACTIVITY) {
			if (labels.inList(strLabelName)) {
				setLayoutBg(R.color.alice_blue);
			} else {
				setLayoutBg(R.color.white);
			}
		}

		layout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				LabelObj label = new LabelObj(labelName.getText().toString(), iconPath,
						Integer.parseInt(strMemberCount));
				switch (fromActivity) {
					case ActivityConstants.MAIN_ACTIVITY:
						ActivityUtils.ActivitySkipWithObject(context, LabelDetailActivity.class, BundleNames.LABEL_OBJ, label);
						break;
					case ActivityConstants.CONTACT_INFO_ACTIVITY:
						//TODO INSERT INTO DATABASE
						if (!labels.inList(strLabelName)) {
							IDLabelTableUtils idLabelTableUtils = new IDLabelTableUtils(context);
							idLabelTableUtils.insertAll("" + contact.getId(), strLabelName);
							labels.addLabel(strLabelName);
						} else {
							IDLabelTableUtils idLabelTableUtils = new IDLabelTableUtils(context);
							idLabelTableUtils.deleteWithID_Label("" + contact.getId(), strLabelName);
							labels.removeLabel(strLabelName);
						}
						notifyDataSetInvalidated();
						break;
					case ActivityConstants.ADD_CONTACTS_ACTIVITY:
//						ActivityUtils.setActivityResult
//								(context, ActivityConstants.REQUEST_CODE_LABEL, BundleNames.LABEL_OBJ,
//										new LabelObj(strLabelName, iconPath, Integer.parseInt(strMemberCount)));
//						((Activity) context).finish();
						if (!labels.inList(strLabelName)) {
							labels.addLabel(strLabelName);
						} else {
							labels.removeLabel(strLabelName);
						}
						notifyDataSetInvalidated();
						break;
				}

			}
		});

		return layout;
	}

	private void setLayoutBg(int colorId) {
		layout.findViewById(R.id.label_cell).setBackgroundColor(context.getResources().getColor(colorId));
	}

}
