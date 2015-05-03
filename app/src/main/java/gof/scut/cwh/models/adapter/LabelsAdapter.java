package gof.scut.cwh.models.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import gof.scut.common.utils.ActivityUtils;
import gof.scut.common.utils.BitmapUtils;
import gof.scut.common.utils.BundleNames;
import gof.scut.common.utils.database.IDLabelTableUtils;
import gof.scut.cwh.models.object.ActivityConstants;
import gof.scut.cwh.models.object.IdObj;
import gof.scut.cwh.models.object.LabelListObj;
import gof.scut.cwh.models.object.LabelObj;
import gof.scut.wechatcontacts.LabelDetailActivity;
import gof.scut.wechatcontacts.R;


public class LabelsAdapter extends BaseAdapter {
	private Context context;
	//private Cursor cursor;
	private List<LabelObj> labels;
	private LinearLayout layout;
	private int fromActivity;
	private LabelListObj selectedLabels;
	private IdObj contact;
	LayoutInflater inflater;
//	private ContactLabelAdapter existsLabels;

	public LabelsAdapter(Context context, List<LabelObj> labels, int fromActivity) {
		this.context = context;
		this.labels = labels;
		this.fromActivity = fromActivity;
		inflater = LayoutInflater.from(context);
	}

	public LabelsAdapter(Context context, List<LabelObj> labels, int fromActivity, LabelListObj selectedLabels) {
		this.context = context;
		this.labels = labels;
		this.fromActivity = fromActivity;
		this.selectedLabels = selectedLabels;
		inflater = LayoutInflater.from(context);
	}

	public LabelsAdapter(Context context, List<LabelObj> labels, int fromActivity, LabelListObj selectedLabels, IdObj contact) {
		this.context = context;
		this.labels = labels;
		this.fromActivity = fromActivity;
		this.selectedLabels = selectedLabels;
		this.contact = contact;
		inflater = LayoutInflater.from(context);
	}

	public LabelsAdapter(Context context, List<LabelObj> labels) {
		fromActivity = ActivityConstants.MAIN_ACTIVITY;
		this.context = context;
		this.labels = labels;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return labels.size();
	}

	@Override
	public Object getItem(int position) {
		return labels.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	static class ViewHolder {
		ImageView labelIcon;
		TextView labelName;
		TextView memberCount;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LabelObj label = labels.get(position);
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.cell_label_list, null);
			holder = new ViewHolder();
			holder.labelIcon = (ImageView) convertView.findViewById(R.id.label_icon);
			holder.labelName = (TextView) convertView.findViewById(R.id.label_name);
			holder.memberCount = (TextView) convertView.findViewById(R.id.member_count);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		layout = (LinearLayout) convertView;
		//layout = (LinearLayout) inflater.inflate(R.layout.cell_label_grid, null);
//		layout = (LinearLayout) inflater.inflate(R.layout.cell_label_list, parent, false);
//		final ImageView labelIcon = (ImageView) layout.findViewById(R.id.label_icon);
//		final TextView labelName = (TextView) layout.findViewById(R.id.label_name);
//		final TextView memberCount = (TextView) layout.findViewById(R.id.member_count);

		final String strLabelName = label.getLabelName();
		final String iconPath = label.getIconPath();
		final String strMemberCount = label.getMemCount() + "";

		holder.labelName.setText(strLabelName);
		holder.memberCount.setText("(" + strMemberCount + ")");
		holder.labelIcon.setBackgroundDrawable(null);
		if (iconPath.equals("")) holder.labelIcon.setBackgroundResource(R.drawable.label50);
		else holder.labelIcon.setImageBitmap(BitmapUtils.decodeBitmapFromPath(iconPath));


		if (fromActivity == ActivityConstants.ADD_CONTACTS_ACTIVITY || fromActivity == ActivityConstants.CONTACT_INFO_ACTIVITY) {
			if (selectedLabels.inList(strLabelName)) {
				setLayoutBg(R.color.alice_blue);
			} else {
				setLayoutBg(R.color.white);
			}
		}

		layout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				LabelObj label = new LabelObj(strLabelName, iconPath,
						Integer.parseInt(strMemberCount));
				switch (fromActivity) {
					case ActivityConstants.MAIN_ACTIVITY:
						ActivityUtils.ActivitySkipWithObject(context, LabelDetailActivity.class, BundleNames.LABEL_OBJ, label);
						break;
					case ActivityConstants.CONTACT_INFO_ACTIVITY:
						//TODO INSERT INTO DATABASE
						if (!selectedLabels.inList(strLabelName)) {
							IDLabelTableUtils idLabelTableUtils = new IDLabelTableUtils(context);
							idLabelTableUtils.insertAll("" + contact.getId(), strLabelName);
							selectedLabels.addLabel(strLabelName);
						} else {
							IDLabelTableUtils idLabelTableUtils = new IDLabelTableUtils(context);
							idLabelTableUtils.deleteWithID_Label("" + contact.getId(), strLabelName);
							selectedLabels.removeLabel(strLabelName);
						}
						notifyDataSetInvalidated();
						break;
					case ActivityConstants.ADD_CONTACTS_ACTIVITY:
//						ActivityUtils.setActivityResult
//								(context, ActivityConstants.REQUEST_CODE_LABEL, BundleNames.LABEL_OBJ,
//										new LabelObj(strLabelName, iconPath, Integer.parseInt(strMemberCount)));
//						((Activity) context).finish();
						if (!selectedLabels.inList(strLabelName)) {
							selectedLabels.addLabel(strLabelName);
						} else {
							selectedLabels.removeLabel(strLabelName);
						}
						notifyDataSetInvalidated();
						break;
				}

			}
		});

		return convertView;
	}

	private void setLayoutBg(int colorId) {
		layout.findViewById(R.id.label_cell).setBackgroundColor(context.getResources().getColor(colorId));
	}

}
