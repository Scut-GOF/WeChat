package gof.scut.wechatcontacts;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import gof.scut.common.MyApplication;
import gof.scut.cwh.models.object.UserInfo;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;


public class MyInfoActivity extends RoboActivity {

    private final static String TAG = MyInfoActivity.class.getSimpleName();
    private final Context mContext = MyInfoActivity.this;

    //views
    @InjectView(R.id.cancel)
    private TextView back_image;
    @InjectView(R.id.save)
    private TextView save;
    @InjectView(R.id.my_binary_code)
    private ImageView my_binary_code;
    @InjectView(R.id.name)
    private EditText name;
    @InjectView(R.id.add_phone_button)
    private ImageView addPhone;
    @InjectView(R.id.phone)
    private EditText phone;
    @InjectView(R.id.phoneList)
    private ListView phoneListView;
    @InjectView(R.id.address)
    private EditText address;
    @InjectView(R.id.addition)
    private EditText addition;

    //values
    private Gson gson;
    private UserInfo userInfo;
    private ArrayList<String> phoneList;
    private MyAdapter phoneAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_info);
        init();
        eventHandler();
	}

    private void init(){
        gson = MyApplication.getGson();
        phoneList = new ArrayList<>();

        userInfo = UserInfo.getInstance();

        if(TextUtils.isEmpty(userInfo.getName()) || userInfo.getTels().isEmpty()){
            Toast.makeText(mContext,"请完善个人信息！",Toast.LENGTH_SHORT).show();
        }else{
            name.setText(userInfo.getName());
            address.setText(userInfo.getAddress());
            addition.setText(userInfo.getNotes());
            phoneList = userInfo.getTels();
            createQRImage( gson.toJson(userInfo, UserInfo.class) );
        }
        phoneAdapter = new MyAdapter(this,phoneList);
        phoneListView.setAdapter(phoneAdapter);
    }

    private void eventHandler() {
        //add phone number
        addPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = phone.getText().toString();
                if (checkPhone(phoneNumber)) {
                    phoneList.add(phoneNumber);
                    phoneAdapter.notifyDataSetChanged();
                    phone.setText("");
                }
            }
        });
        //save
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(name.getText())){

                    Toast.makeText(mContext,"未输入姓名！",Toast.LENGTH_SHORT).show();
                }else if(phoneList.isEmpty()){

                    Toast.makeText(mContext,"未输入号码！",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(address.getText())){

                    Toast.makeText(mContext,"未输入地址！",Toast.LENGTH_SHORT).show();
                }else{
                    userInfo.setName(name.getText().toString());
                    userInfo.setTels(phoneList);
                    userInfo.setAddress(address.getText().toString());
                    userInfo.setNotes(addition.getText().toString());
                    Toast.makeText(mContext,"已保存",Toast.LENGTH_SHORT).show();
                    createQRImage( gson.toJson(userInfo, UserInfo.class) );
                }
            }
        });

        //back
        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //检查手机号码格式
    private boolean checkPhone(String phoneNumber) {
        String telRegex = "[1]\\d{10}";//第一位是1，后10位为0-9任意数字，共计11位；

        if (TextUtils.isEmpty(phoneNumber))
            return false;

        if (!phoneNumber.matches(telRegex)) {
            Toast.makeText(mContext, "手机号码格式错误!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public void createQRImage(String url) {
        int QR_WIDTH = 500;
        int QR_HEIGHT = 500;
        try {
            //判断URL合法性
            if (url == null || "".equals(url) || url.length() < 1) {
                return;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            //下面这里按照二维码的算法，逐个生成二维码的图片，
            //两个for循环是图片横列扫描的结果
            for (int y = 0; y < QR_HEIGHT; y++) {
                for (int x = 0; x < QR_WIDTH; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * QR_WIDTH + x] = 0xff000000;
                    } else {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;
                    }
                }
            }
            //生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
            //显示到一个ImageView上面
            my_binary_code.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }


    private class MyAdapter extends BaseAdapter {

        private List<String> data;
        private Context context;

        public MyAdapter(Context mContext,List<String> datas) {
            this.context = mContext;
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

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.cell_edit_member_list, parent, false);
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
                    MyAdapter.this.notifyDataSetChanged();
                }
            });

            return convertView;
        }
    }

    static class ViewHolder {
        Button remove;
        TextView name;
    }
}
