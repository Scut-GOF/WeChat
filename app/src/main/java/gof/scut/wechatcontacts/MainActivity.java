package gof.scut.wechatcontacts;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import gof.scut.common.utils.ActivityUtils;
import gof.scut.common.utils.MainTableUtils;
import gof.scut.cwh.models.adapter.ContactsAdapter;


public class MainActivity extends Activity implements View.OnClickListener {

    //Views
    private Button btSearch, btMenu;
    private LinearLayout searchLayout;
    private Button searchGo, btHide;
    private ListView contacts;
    private Button btAdd, btGroup, btMe;


    //Constants

    //Models
    MainTableUtils mainTableUtils;

    //Controllers


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        //init database
        initDatabase();
        //init Views
        findViews();
        //init handlers //init button listeners, list
        eventHandler();
    }

    private void initDatabase() {
        mainTableUtils = new MainTableUtils(this);
    }

    private void findViews() {
        btSearch = (Button) findViewById(R.id.bt_search);
        btMenu = (Button) findViewById(R.id.bt_menu);
        searchLayout = (LinearLayout) findViewById(R.id.search_layout);
        searchGo = (Button) findViewById(R.id.search_go);
        btHide = (Button) findViewById(R.id.bt_hide);
        contacts = (ListView) findViewById(R.id.search_list);
        btAdd = (Button) findViewById(R.id.bt_add);
        btGroup = (Button) findViewById(R.id.bt_group);
        btMe = (Button) findViewById(R.id.bt_me);
    }

    private void eventHandler() {
        btSearch.setOnClickListener(this);
        btMenu.setOnClickListener(this);
        searchGo.setOnClickListener(this);
        btHide.setOnClickListener(this);
        btSearch.setOnClickListener(this);
        btAdd.setOnClickListener(this);
        btGroup.setOnClickListener(this);
        btMe.setOnClickListener(this);

    }

    private void initList() {
        //When clickOnNameAndTel, view
        //When click on image, view label group
        //edit when view
        Cursor cursor = mainTableUtils.selectAll();
        ContactsAdapter adapter = new ContactsAdapter(this, cursor);
        contacts.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bt_search:
                searchLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.bt_menu:
                //A MENU WITH SETTING IN IT
                ActivityUtils.ActivitySkip(this, SettingActivity.class);
                break;
            case R.id.search_go:
                //QUERY
                break;
            case R.id.bt_hide:
                searchLayout.setVisibility(View.GONE);
                break;
            case R.id.bt_add:
                //TO ADD CONTACTS ACTIVITY
                ActivityUtils.ActivitySkip(this, AddContactActivity.class);
                break;
            case R.id.bt_group:
                //TO GROUP VIEW
                ActivityUtils.ActivitySkip(this, LabelsActivity.class);
                break;
            case R.id.bt_me:
                //TO SELF ACTIVITY
                ActivityUtils.ActivitySkip(this, SelfInfoActivity.class);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initList();
    }
}
