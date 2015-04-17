package gof.scut.wechatcontacts;


import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import gof.scut.common.utils.BitmapUtils;
import gof.scut.common.utils.BundleNames;
import gof.scut.common.utils.Log;
import gof.scut.common.utils.StringUtils;
import gof.scut.common.utils.database.AllTableUtils;
import gof.scut.common.utils.database.IDLabelTableUtils;
import gof.scut.common.utils.database.LabelTableUtils;
import gof.scut.common.utils.database.MainTableUtils;
import gof.scut.common.utils.database.TBIDLabelConstants;
import gof.scut.cwh.models.adapter.ContactsAdapter;
import gof.scut.cwh.models.object.LabelObj;


public class LabelDetailActivity extends Activity implements View.OnClickListener {
    final static String TAG = "labelDetail";
    LabelObj labelObj;
    boolean inEdit = false;

    LabelTableUtils labelTableUtils;
    IDLabelTableUtils idLabelTableUtils;
    MainTableUtils mainTableUtils;
    AllTableUtils allTableUtils;

    TextView labelsBack;
    Button editLabel;

    ImageView labelIcon;
    TextView labelName;
    TextView memberCount;

    LinearLayout addLabelLayout;
    Button addMember;

    ListView labelMembers;

    Button deleteLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label_detail);
        init();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initViewList();
    }

    void init() {
        getLabelFromBundle();
        initDataBase();
        findView();
        initComponent();
    }

    void getLabelFromBundle() {
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            labelObj = (LabelObj) bundle.getSerializable(BundleNames.LABEL_OBJ);
        } else {
            Log.e(TAG, "Bundle null");
            finish();
        }
    }

    void initDataBase() {
        labelTableUtils = new LabelTableUtils(this);
        idLabelTableUtils = new IDLabelTableUtils(this);
        mainTableUtils = new MainTableUtils(this);
        allTableUtils = new AllTableUtils(this);
    }

    void initComponent() {
        labelName.setText(labelObj.getLabelName());
        labelIcon.setImageBitmap(BitmapUtils.decodeBitmapFromPath(labelObj.getIconPath()));
        memberCount.setText(StringUtils.addBrackets(labelObj.getMemCount() + ""));

    }

    void checkState() {
        int viewState;
        if (inEdit) {
            viewState = View.VISIBLE;
        } else {
            viewState = View.GONE;
        }
        addLabelLayout.setVisibility(viewState);
        deleteLabel.setVisibility(viewState);
    }

    void findView() {
        labelsBack = (TextView) findViewById(R.id.labels_back);
        editLabel = (Button) findViewById(R.id.edit_label);
        labelIcon = (ImageView) findViewById(R.id.label_icon);
        labelName = (TextView) findViewById(R.id.label_name);
        memberCount = (TextView) findViewById(R.id.member_count);
        addLabelLayout = (LinearLayout) findViewById(R.id.add_label_layout);
        addMember = (Button) findViewById(R.id.add_member);
        labelMembers = (ListView) findViewById(R.id.member_list);
        deleteLabel = (Button) findViewById(R.id.delete_label);
        checkState();
    }

    void initViewList() {
        //cursor,contactsAdapter,labelMembers
        Cursor c = idLabelTableUtils.selectLabelWithID("0");
        Cursor cursor = allTableUtils.selectAllIDNameOnLabel(labelObj.getLabelName());
        ContactsAdapter adapter = new ContactsAdapter(this, cursor);
        labelMembers.setAdapter(adapter);
    }

    void initEditList() {
        //TODO select id from label where label = bundleLabel
        //TODO select * from main where id = id;
        //cursor,contactsAdapter,labelMembers
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.labels_back:
                finish();
                break;
            case R.id.add_member:

                break;
        }
    }
}
