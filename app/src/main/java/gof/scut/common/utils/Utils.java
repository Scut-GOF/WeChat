package gof.scut.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Hashtable;

import gof.scut.cwh.models.adapter.PhoneListAdapter;
import gof.scut.wechatcontacts.R;

/**
*
* Created by zm on 2015/5/7.
*/
public class Utils {

    // 定义函数动态控制listView的高度
    public static void setListViewHeightBasedOnChildren(ListView listView) {

        PhoneListAdapter listAdapter = (PhoneListAdapter)listView.getAdapter();

        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i,null, listView);

            listItem.measure(0, 0); //计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight();//统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight;

        listView.setLayoutParams(params);
    }

    //检查手机号码格式
    public static boolean checkPhone(Context mContext,String phoneNumber) {
       String telRegex = "[1]\\d{10}";//第一位是1，后10位为0-9任意数字，共计11位；

       if (TextUtils.isEmpty(phoneNumber))
           return false;

       if (!phoneNumber.matches(telRegex)) {
            Toast.makeText(mContext, R.string.error_phone_format, Toast.LENGTH_SHORT).show();
            return false;
       }
       return true;
    }

    /**
    * create QRImage.
    * @param imageView
    * @param url
    */
    public static void createQRImage(ImageView imageView,String url) {
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
                        }else{
                            pixels[y * QR_WIDTH + x] = 0xffffffff;
                        }
                }
            }
            //生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
            //显示到一个ImageView上面
            imageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}