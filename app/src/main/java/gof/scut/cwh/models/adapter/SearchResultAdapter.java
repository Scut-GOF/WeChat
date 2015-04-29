package gof.scut.cwh.models.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import gof.scut.common.utils.ActivityUtils;
import gof.scut.common.utils.BundleNames;
import gof.scut.common.utils.StringUtils;
import gof.scut.common.utils.database.TBIDLabelConstants;
import gof.scut.common.utils.database.TBMainConstants;
import gof.scut.common.utils.database.TBTelConstants;
import gof.scut.cwh.models.object.ActivityConstants;
import gof.scut.cwh.models.object.IdObj;
import gof.scut.wechatcontacts.ContactInfoActivity;
import gof.scut.wechatcontacts.R;

public class SearchResultAdapter extends BaseAdapter {
	private Context context;
	private Cursor cursor;
	private LinearLayout layout;
	private String keyword;
	private int fromActivity;

	//private boolean flag;

	public SearchResultAdapter(Context context, Cursor cursor, String keyword, int fromActivity) {
		this.context = context;
		this.cursor = cursor;
		this.keyword = keyword.toLowerCase();
		this.fromActivity = fromActivity;
		//flag = false;
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

		final String id = cursor.getString(cursor.getColumnIndex(TBMainConstants.ID));
		layout = (LinearLayout) inflater.inflate(R.layout.cell_search_list, parent, false);
		TextView tvName = (TextView) layout.findViewById(R.id.name);
		TextView searchDetail = (TextView) layout.findViewById(R.id.searched_detail);
		String name = cursor.getString(cursor.getColumnIndex(TBMainConstants.NAME));
		//flag = false;
		//if (name.toLowerCase().contains(keyword))flag = true;
		tvName.setText(StringUtils.simpleHighLight(keyword, name, "A4005B", "636362"));
		searchDetail.setText(getFirstMatchInfo(position, keyword));
		if (searchDetail.getText().toString().equals("")) searchDetail.setVisibility(View.GONE);
		//if (!flag)layout.setVisibility(View.GONE);
		layout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				IdObj obj = new IdObj(Integer.parseInt(id));
				switch (fromActivity) {
					case ActivityConstants.MAIN_ACTIVITY:
						ActivityUtils.ActivitySkipWithObject(context, ContactInfoActivity.class, BundleNames.ID_OBJ, obj);
						break;
					case ActivityConstants.LABEL_DETAIL_ACTIVITY:
						ActivityUtils.setActivityResult(context, ActivityConstants.RESULT_ADD_MEMBER, BundleNames.ID_OBJ, obj);
						((Activity) context).finish();
						break;
					default:
						ActivityUtils.ActivitySkipWithObject(context, ContactInfoActivity.class, BundleNames.ID_OBJ, obj);
						break;
				}

			}
		});
		return layout;
	}

	private Spanned getFirstMatchInfo(int position, String keyword) {
		cursor.moveToPosition(position);
		String matchInfo = "";
		if (StringUtils.isNumber(keyword)) {
			matchInfo = cursor.getString(cursor.getColumnIndex(TBTelConstants.TEL));
			if (matchInfo != null)
				if (matchInfo.toLowerCase().contains(keyword)) {
					matchInfo = "Tel:" + matchInfo;
					//flag = true;
					return StringUtils.simpleHighLight(keyword, matchInfo, "A4005B", "636362");
				}
		} else {
			matchInfo = cursor.getString(cursor.getColumnIndex(TBIDLabelConstants.LABEL));
			if (matchInfo != null) {
				if (matchInfo.toLowerCase().contains(keyword)) {
					matchInfo = "Label:" + matchInfo;
					//flag = true;
					return StringUtils.simpleHighLight(keyword, matchInfo, "A4005B", "636362");
				}
			}
			matchInfo = cursor.getString(cursor.getColumnIndex(TBMainConstants.ADDRESS));
			if (matchInfo.toLowerCase().contains(keyword)) {
				//flag = true;
				matchInfo = "Address:" + matchInfo;
				return StringUtils.simpleHighLight(keyword, matchInfo, "A4005B", "636362");
			}
			matchInfo = cursor.getString(cursor.getColumnIndex(TBMainConstants.NOTES));
			if (matchInfo.toLowerCase().contains(keyword)) {
				matchInfo = "Notes:" + matchInfo;
				//flag = true;
				return StringUtils.simpleHighLight(keyword, matchInfo, "A4005B", "636362");
			}
		}
		return Html.fromHtml("");
	}
}
