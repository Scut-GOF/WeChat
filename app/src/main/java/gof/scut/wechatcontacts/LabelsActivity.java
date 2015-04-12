package gof.scut.wechatcontacts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.GridView;
import android.widget.PopupWindow;

import gof.scut.common.utils.ActivityUtils;
import gof.scut.common.utils.Log;
import gof.scut.common.utils.database.LabelTableUtils;
import gof.scut.cwh.models.adapter.LabelsAdapter;


public class LabelsActivity extends Activity {
    Button addLabel;
    GridView labels;
    PopupWindow addLabelWindow;
    View addLabelView;

    LabelTableUtils labelTableUtils;

    final int RESULT_LOAD_IMAGE = 1;
    String pathSelectedToAdd = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labels);

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initGrids();
    }

    void initDatabase() {
        labelTableUtils = new LabelTableUtils(this);

    }

    void init() {
        initDatabase();
        findView();
        initPopAddLabel();
        setListener();

    }

    void setListener() {
        addLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //labelTableUtils.insertAll()
                popPhoneSelector();
            }
        });
    }

    void findView() {
        addLabel = (Button) findViewById(R.id.add_label);
        labels = (GridView) findViewById(R.id.labels);
    }

    void initGrids() {
        Cursor cursorLabels = labelTableUtils.selectAll();
        LabelsAdapter labelsAdapter = new LabelsAdapter(this, cursorLabels);
        labels.setAdapter(labelsAdapter);
    }

    void initPopAddLabel() {
        addLabelView = LayoutInflater.from(this).inflate(
                R.layout.pop_add_label, null, false);
        addLabelWindow = new PopupWindow(addLabelView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);

        ColorDrawable dw = new ColorDrawable(-00000);
        addLabelView.setBackgroundDrawable(dw);

        final Context context = this;
        final EditText labelName = (EditText) addLabelView.findViewById(R.id.add_label_name);
        Button labelIcon = (Button) addLabelView.findViewById(R.id.add_label_icon);
        Button confirm = (Button) addLabelView.findViewById(R.id.btn_confirm);
        labelIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.sysGallery(context, RESULT_LOAD_IMAGE);
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String addLabelName, addLabelIcon;
                addLabelName = labelName.getText().toString();
                addLabelIcon = pathSelectedToAdd;
                long state = labelTableUtils.insertAll(addLabelName, addLabelIcon);
                if (state < 0) Log.e("LabelsActivity", "add label failed");
                addLabelWindow.dismiss();
                initGrids();
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

    public void popPhoneSelector() {
        addLabelWindow.update();
        addLabelWindow.showAtLocation(findViewById(R.id.label_layout), Gravity.CENTER, 0, 0);
        Animation anim1 = AnimationUtils.loadAnimation(this, R.anim.scale_center_enter);
        addLabelView.findViewById(R.id.add_label_name).startAnimation(anim1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            pathSelectedToAdd = cursor.getString(columnIndex);
            cursor.close();
        }
    }
}
