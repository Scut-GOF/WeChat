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
import gof.scut.common.utils.database.TBLabelConstants;
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

    public LabelsAdapter(Context context, Cursor cursor) {
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
    public View getView(int position, View layout, ViewGroup parent) {
        cursor.moveToPosition(position);
        LayoutInflater inflater = LayoutInflater.from(context);
        //layout = (LinearLayout) inflater.inflate(R.layout.cell_label_grid, null);
        layout = (LinearLayout) inflater.inflate(R.layout.cell_label_list, parent, false);
        ImageView labelIcon = (ImageView) layout.findViewById(R.id.label_icon);
        final TextView labelName = (TextView) layout.findViewById(R.id.label_name);
        final TextView memberCount = (TextView) layout.findViewById(R.id.member_count);
        labelName.setText(cursor.getString(cursor.getColumnIndex(TBLabelConstants.LABEL)));
        final String iconPath = cursor.getString(cursor.getColumnIndex(TBLabelConstants.LABEL_ICON));
        final String strMemberCount = cursor.getString(cursor.getColumnIndex(TBLabelConstants.MEMBER_COUNT));
        memberCount.setText("(" + strMemberCount + ")");
        labelIcon.setBackgroundDrawable(null);
        if (iconPath.equals("")) labelIcon.setBackgroundResource(R.drawable.chart_1_2);
        else labelIcon.setImageBitmap(BitmapUtils.decodeBitmapFromPath(iconPath));
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LabelObj label = new LabelObj(labelName.getText().toString(), iconPath,
                        Integer.parseInt(strMemberCount));
                ActivityUtils.ActivitySkipWithObject(context, LabelDetailActivity.class, BundleNames.LABEL_OBJ, label);
            }
        });

        return layout;
    }

}
