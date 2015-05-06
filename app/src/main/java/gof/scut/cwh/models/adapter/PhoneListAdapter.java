package gof.scut.cwh.models.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import gof.scut.wechatcontacts.R;

/**
 *
 * show the phone list!
 * Created by zm on 2015/5/6.
 */
public class PhoneListAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> data;

    public PhoneListAdapter(Context context , List<String> datas) {
        this.mContext = context;
        data = datas;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        Button remove;
        TextView name;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();

            convertView = LayoutInflater.from(mContext).inflate(R.layout.cell_edit_member_list, parent, false);

            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.remove = (Button) convertView.findViewById(R.id.remove_member);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(data.get(position));
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.remove(position);
                PhoneListAdapter.this.notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
