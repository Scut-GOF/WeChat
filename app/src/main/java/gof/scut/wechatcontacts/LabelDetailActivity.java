package gof.scut.wechatcontacts;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import gof.scut.common.utils.database.TBIDLabelConstants;


public class LabelDetailActivity extends Activity {
    TextView labelTitle;
    ListView labelMembers;
    String labelName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label_detail);
        init();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initList();
    }

    void init() {
        initDataBase();
        findView();
    }

    void getLabelIdFromBundle() {
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            labelName = bundle.getString(TBIDLabelConstants.LABEL);
        }
    }

    void initDataBase() {

    }

    void findView() {
        labelTitle.setText(labelName);
    }

    void initList() {
        //TODO select id from label where label = bundleLabel
        //TODO select * from main where id = id;
        //cursor,contactsAdapter,labelMembers
    }


}
