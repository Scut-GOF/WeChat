package gof.scut.wechatcontacts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import gof.scut.common.MyApplication;
import gof.scut.common.utils.BundleNames;
import gof.scut.common.utils.Utils;
import gof.scut.common.utils.database.IDLabelTableUtils;
import gof.scut.common.utils.database.MainTableUtils;
import gof.scut.common.utils.database.TelTableUtils;
import gof.scut.common.utils.popup.PopConfirmUtils;
import gof.scut.common.utils.popup.TodoOnResult;
import gof.scut.cwh.models.adapter.PhoneListAdapter;
import gof.scut.cwh.models.object.ActivityConstants;
import gof.scut.cwh.models.object.IdObj;
import gof.scut.cwh.models.object.LabelListObj;
import gof.scut.cwh.models.object.Signal;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;


public class ContactInfoActivity extends RoboActivity {

    private final static String TAG = ContactInfoActivity.class.getSimpleName();
    private final Context mContext = ContactInfoActivity.this;

    //views
    @InjectView(R.id.cancel)
    private TextView back_image;
    @InjectView(R.id.save)
    private TextView save;
    @InjectView(R.id.binary_code)
    private ImageView binary_code;
    @InjectView(R.id.name)
    private EditText name;
    @InjectView(R.id.add_phone_button)
    private ImageView addPhone;
    @InjectView(R.id.phone)
    private EditText phone;
    @InjectView(R.id.phoneList)
    private ListView phoneListView;
    @InjectView(R.id.add_label_button)
    private ImageView addLabel;
    @InjectView(R.id.labelList)
    private ListView labelListView;
    @InjectView(R.id.address)
    private EditText address;
    @InjectView(R.id.addition)
    private EditText addition;
	@InjectView(R.id.delete_contact)
	private Button deleteContact;

    //values
    private int id;
    private Gson gson;
    private MainTableUtils mainTableUtils;
    private IDLabelTableUtils labelTableUtils;
    private TelTableUtils telTableUtils;
    private IdObj contact;
    private List<String> phoneList;
    private PhoneListAdapter phoneAdapter;
    private List<String> labelList;
    private PhoneListAdapter labelAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_info);

        init();
        initViews();
        eventHandler();
	}

    private void init(){
        Bundle bundle = getIntent().getExtras();
        id = ((IdObj) bundle.getSerializable("IdObj")).getId();

        mainTableUtils = new MainTableUtils(mContext);
        contact = mainTableUtils.selectAllWithID(String.valueOf(id));
        if (contact.getId() < 0) {
            Toast.makeText(mContext,R.string.error_no_person,Toast.LENGTH_SHORT).show();
            finish();
        }

        gson = MyApplication.getGson();

        telTableUtils = new TelTableUtils(mContext);
        phoneList = new ArrayList<>();
        phoneAdapter = new PhoneListAdapter(mContext,phoneList,phoneListView);
        phoneListView.setAdapter(phoneAdapter);

        labelTableUtils = new IDLabelTableUtils(mContext);
        labelList = new ArrayList<>();
        labelAdapter = new PhoneListAdapter(mContext,labelList,labelListView);
        labelListView.setAdapter(labelAdapter);

        contact.setTels((ArrayList<String>) phoneList);
    }

	private void initViews() {
        name.setText(contact.getName());
        address.setText(contact.getAddress());
        addition.setText(contact.getNotes());

        phoneList.addAll(telTableUtils.selectTelListWithID(String.valueOf(id)));
        Utils.setListViewHeightBasedOnChildren(phoneListView);
        phoneAdapter.notifyDataSetChanged();

        labelList.addAll(labelTableUtils.selectLabelWithID(String.valueOf(id)));
        Utils.setListViewHeightBasedOnChildren(labelListView);
        labelAdapter.notifyDataSetChanged();

        Utils.createQRImage(binary_code , gson.toJson(contact, IdObj.class));
	}

    private void eventHandler(){
        //back
        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
	    deleteContact.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
			    PopConfirmUtils popConfirmUtils = new PopConfirmUtils();
			    popConfirmUtils.prepare(mContext, R.layout.pop_confirm);
			    popConfirmUtils.initPopupWindow();
			    popConfirmUtils.setTitle("Sure to delete?");
			    popConfirmUtils.initTodo(new TodoOnResult() {
				    @Override
				    public void doOnPosResult(String[] params) {
					    mainTableUtils.deleteWithID(id + "");
					    finish();
				    }

				    @Override
				    public void doOnNegResult(String[] params) {

				    }
			    });
			    popConfirmUtils.popWindowAtCenter(R.id.phone, R.id.confirm_title);
		    }
	    });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(name.getText())){

                    Toast.makeText(mContext, R.string.no_name,Toast.LENGTH_SHORT).show();
                }else if(phoneList.isEmpty()){

                    Toast.makeText(mContext,R.string.no_phone,Toast.LENGTH_SHORT).show();
                }else{
                    mainTableUtils.updateAllWithID(name.getText().toString(),

		                    address.getText().toString(),
		                    addition.getText().toString(),
		                    String.valueOf(id));

                    telTableUtils.deleteWithID(String.valueOf(id));
                    for(String phone:phoneList){
                        telTableUtils.insertAll(String.valueOf(id), phone);
                    }

                    labelTableUtils.deleteWithID(String.valueOf(id));
                    for(String label:labelList){
                        labelTableUtils.insertAll(String.valueOf(id), label);
                    }

                    contact.setName(name.getText().toString());
                    contact.setAddress(address.getText().toString());
                    contact.setNotes(addition.getText().toString());
                    contact.setTels((ArrayList<String>)phoneList);
                    Toast.makeText(mContext,R.string.is_save,Toast.LENGTH_SHORT).show();
                    Utils.createQRImage(binary_code , gson.toJson(contact, IdObj.class));
                }
            }
        });

        //add phone number
        addPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = phone.getText().toString();
                if (Utils.checkPhone(mContext,phoneNumber)) {
                    phoneList.add(phoneNumber);
                    Utils.setListViewHeightBasedOnChildren(phoneListView);
                    phoneAdapter.notifyDataSetChanged();
                    phone.setText("");
                }
            }
        });
        //add label number
        addLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, EditContactLabelActivity.class);
                Bundle bundle = new Bundle();
                Signal signal = new Signal(ActivityConstants.ADD_CONTACTS_ACTIVITY, ActivityConstants.EditContactLabelActivity);
                bundle.putSerializable(Signal.NAME, signal);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bundle bundle = data.getExtras();
        LabelListObj labelListObj = (LabelListObj) bundle.getSerializable(BundleNames.LABEL_LIST);
        if (labelListObj.getLabels().size() != 0) {
            Toast.makeText(this, labelListObj.toString(), Toast.LENGTH_SHORT).show();
            labelList.addAll(labelListObj.getLabels());
            Utils.setListViewHeightBasedOnChildren(labelListView);
            labelAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainTableUtils.closeDataBase();
        telTableUtils.closeDataBase();
        labelTableUtils.closeDataBase();
    }
}
