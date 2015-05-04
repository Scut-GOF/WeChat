package gof.scut.common.push;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import gof.scut.common.AppConfig;
import gof.scut.common.MyApplication;
import gof.scut.common.utils.Log;
import gof.scut.wechatcontacts.R;

public class PushDemoActivity extends Activity {

    private static final String TAG = PushDemoActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_demo);

        findViewById(R.id.push).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTask task = new MyTask();
                task.execute();
            }
        });
    }

    class MyTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            //周萌  599685947433395315
            //伟航 723559864759604254
            return MyApplication.getInstance().getBaiduPush().
                    PushNotify("来自周萌的问候", "伟航小婊砸", "599685947433395315", 111);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG,"AppConfig.appid" + AppConfig.appid);
        Log.d(TAG,"AppConfig.requestId" + AppConfig.requestId);
        Log.d(TAG,"AppConfig.channelId" +  AppConfig.channelId);
        Log.d(TAG,"AppConfig.userId" +  AppConfig.userId);
    }

}
