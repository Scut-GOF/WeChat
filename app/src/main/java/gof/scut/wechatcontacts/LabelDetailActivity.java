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
import android.widget.Toast;

import gof.scut.common.utils.ActivityUtils;
import gof.scut.common.utils.BitmapUtils;
import gof.scut.common.utils.BundleNames;
import gof.scut.common.utils.Log;
import gof.scut.common.utils.StringUtils;
import gof.scut.common.utils.database.AllTableUtils;
import gof.scut.common.utils.database.IDLabelTableUtils;
import gof.scut.common.utils.database.LabelTableUtils;
import gof.scut.common.utils.database.MainTableUtils;
import gof.scut.common.utils.database.TBIDLabelConstants;
import gof.scut.common.utils.popup.PopConfirmUtils;
import gof.scut.common.utils.popup.TodoOnResult;
import gof.scut.cwh.models.adapter.ContactsAdapter;
import gof.scut.cwh.models.adapter.MemEditAdapter;
import gof.scut.cwh.models.object.LabelObj;


public class LabelDetailActivity extends Activity implements View.OnClickListener {
    final static String TAG = "labelDetail";
    LabelObj labelObj;
    boolean inEdit = false;

    AllTableUtils allTableUtils;
    LabelTableUtils labelTableUtils;
    IDLabelTableUtils idLabelTableUtils;

    TextView labelsBack;
    TextView editLabel;

    ImageView labelIcon;
    TextView labelName;
    TextView memberCount;

    LinearLayout addMemberLayout;
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
        allTableUtils = new AllTableUtils(this);
        labelTableUtils = new LabelTableUtils(this);
        idLabelTableUtils = new IDLabelTableUtils(this);
    }

    void initComponent() {
        labelName.setText(labelObj.getLabelName());
        labelIcon.setBackgroundDrawable(null);
        labelIcon.setImageBitmap(BitmapUtils.decodeBitmapFromPath(labelObj.getIconPath()));
        memberCount.setText(StringUtils.addBrackets(labelObj.getMemCount() + ""));
        findViewById(R.id.barRelativeLayout).setEnabled(true);
        setListener();

    }

    void checkState() {
        int viewState;
        if (inEdit) {
            initEditList();
            viewState = View.VISIBLE;
            editLabel.setText("Save");
        } else {
            saveEditResult();
            initViewList();
            viewState = View.GONE;
            editLabel.setText("Edit");
        }
        addMemberLayout.setVisibility(viewState);
        deleteLabel.setVisibility(viewState);

    }

    void setListener() {
        labelsBack.setOnClickListener(this);
        editLabel.setOnClickListener(this);
        deleteLabel.setOnClickListener(this);
        addMember.setOnClickListener(this);
        addMemberLayout.setOnClickListener(this);
    }

    void saveEditResult() {

    }

    void findView() {
        labelsBack = (TextView) findViewById(R.id.labels_back);
        editLabel = (TextView) findViewById(R.id.edit_label);
        labelIcon = (ImageView) findViewById(R.id.label_icon);
        labelName = (TextView) findViewById(R.id.label_name);
        memberCount = (TextView) findViewById(R.id.member_count);
        addMemberLayout = (LinearLayout) findViewById(R.id.add_member_layout);
        addMember = (Button) findViewById(R.id.add_member);
        labelMembers = (ListView) findViewById(R.id.member_list);
        deleteLabel = (Button) findViewById(R.id.delete_label);
        checkState();
    }

    void initViewList() {
        //cursor,contactsAdapter,labelMembers
        Cursor cursor = allTableUtils.selectAllIDNameOnLabel(labelObj.getLabelName());
        ContactsAdapter adapter = new ContactsAdapter(this, cursor);
        labelMembers.setAdapter(adapter);
    }

    void initEditList() {

        //cursor,contactsAdapter,labelMembers
        Cursor cursor = allTableUtils.selectAllIDNameOnLabel(labelObj.getLabelName());
        MemEditAdapter adapter = new MemEditAdapter(this, cursor, labelObj.getLabelName());
        labelMembers.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.labels_back:

                finish();
                break;
            case R.id.edit_label:
                inEdit = !inEdit;
                checkState();
                break;
            case R.id.add_member:
                //TODO it's a test
                idLabelTableUtils.insertAll("1", labelObj.getLabelName());
                initEditList();
                break;
            case R.id.delete_label:
                PopConfirmUtils popConfirmUtils = new PopConfirmUtils();
                popConfirmUtils.prepare(this, R.layout.pop_confirm);
                popConfirmUtils.initPopupWindow();
                popConfirmUtils.setTitle("Sure to delete?");
                popConfirmUtils.initTodo(new TodoOnResult() {
                    @Override
                    public void doOnPosResult() {
                        labelTableUtils.deleteWithLabel(labelObj.getLabelName());
                        //TODO add trigger to delete id-label relation
                        finish();
                    }

                    @Override
                    public void doOnNegResult() {

                    }
                });
                popConfirmUtils.popWindowAtCenter(R.id.member_list, R.id.confirm_title);
                break;
            case R.id.add_member_layout:
                ActivityUtils.ActivitySkip(this, SearchActivity.class);
                break;
        }
    }
}
