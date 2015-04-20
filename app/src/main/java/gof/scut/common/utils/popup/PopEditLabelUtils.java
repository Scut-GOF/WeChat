package gof.scut.common.utils.popup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import gof.scut.common.utils.ActivityUtils;
import gof.scut.common.utils.Log;
import gof.scut.common.utils.database.LabelTableUtils;
import gof.scut.wechatcontacts.R;

/**
 * Created by Administrator on 2015/4/20.
 */
public class PopEditLabelUtils {
    PopupWindow addLabelWindow;
    View addLabelView;
    Context context;


    final int RESULT_LOAD_IMAGE = 1;
    String pathSelectedToAdd = "";
    TodoOnResult todoOnResult;

    int parentId;

    public void initPopAddLabel(Context context, TodoOnResult todoOnResult, String popTitleStr, int parentId) {
        this.context = context;
        this.todoOnResult = todoOnResult;
        this.parentId = parentId;
        addLabelView = LayoutInflater.from(context).inflate(
                R.layout.pop_add_label, (ViewGroup) ActivityUtils.getRootView((Activity) context), false);
        TextView popTitle = (TextView) addLabelView.findViewById(R.id.pop_title);
        popTitle.setText(popTitleStr);
        addLabelWindow = new PopupWindow(addLabelView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);

        ColorDrawable dw = new ColorDrawable(0x00000000);
        addLabelView.setBackgroundDrawable(dw);

        final Context innerContext = context;
        final EditText labelName = (EditText) addLabelView.findViewById(R.id.add_label_name);
        Button labelIcon = (Button) addLabelView.findViewById(R.id.add_label_icon);
        Button confirm = (Button) addLabelView.findViewById(R.id.btn_confirm);
        labelIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.sysGallery(innerContext, RESULT_LOAD_IMAGE);
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String addLabelName, addLabelIcon;
                addLabelName = labelName.getText().toString();
                addLabelIcon = pathSelectedToAdd;
                doOnConfirm(new String[]{addLabelName, addLabelIcon});
                addLabelWindow.dismiss();

            }
        });
        addLabelView.setFocusable(true);
        addLabelView.setFocusableInTouchMode(true);
        addLabelView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
                if (arg1 == KeyEvent.KEYCODE_BACK) {
                    addLabelWindow.dismiss();
                    return true;
                }
                return false;
            }
        });
        addLabelView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addLabelWindow.dismiss();
            }

        });

        addLabelWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }

        });

    }

    public void doOnConfirm(String[] params) {
        todoOnResult.doOnPosResult(params);
    }

    public void handleResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = context.getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            pathSelectedToAdd = cursor.getString(columnIndex);
            cursor.close();
        }
    }

    public void popEditLabel() {
        addLabelWindow.update();
        addLabelWindow.showAtLocation(((Activity) context).findViewById(parentId), Gravity.CENTER, 0, 0);
        Animation anim1 = AnimationUtils.loadAnimation(context, R.anim.scale_center_enter);
        addLabelView.findViewById(R.id.add_label_name).startAnimation(anim1);
    }
}
