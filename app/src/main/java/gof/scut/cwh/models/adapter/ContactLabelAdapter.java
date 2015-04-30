package gof.scut.cwh.models.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import gof.scut.common.utils.BitmapUtils;
import gof.scut.common.utils.database.IDLabelTableUtils;
import gof.scut.common.utils.database.TBLabelConstants;
import gof.scut.cwh.models.object.IdObj;
import gof.scut.wechatcontacts.R;

/**
 * Created by cwh on 15-4-30.
 */
public class ContactLabelAdapter extends BaseAdapter {
	private Context context;
	private Cursor cursor;
	private LinearLayout layout;
	private IdObj idObj;


	public ContactLabelAdapter(Context context, Cursor cursor, IdObj idObj) {
		this.context = context;
		this.cursor = cursor;
		this.idObj = idObj;
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
		layout = (LinearLayout) inflater.inflate(R.layout.cell_label_grid, parent, false);
		ImageView labelIcon = (ImageView) layout.findViewById(R.id.label_icon);
		final TextView labelName = (TextView) layout.findViewById(R.id.label_name);
		final Button removeLabel = (Button) layout.findViewById(R.id.label_remove);
		final String strLabelName = cursor.getString(cursor.getColumnIndex(TBLabelConstants.LABEL));
		final String iconPath = cursor.getString(cursor.getColumnIndex(TBLabelConstants.LABEL_ICON));

		labelName.setText(strLabelName);
		labelIcon.setBackgroundDrawable(null);
		if (iconPath.equals("")) labelIcon.setBackgroundResource(R.drawable.label50);
		else labelIcon.setImageBitmap(BitmapUtils.decodeBitmapFromPath(iconPath));

		removeLabel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				IDLabelTableUtils idLabelTableUtils = new IDLabelTableUtils(context);
				idLabelTableUtils.deleteWithID_Label(idObj.getId() + "", strLabelName);
			}
		});

		return layout;
	}

}
