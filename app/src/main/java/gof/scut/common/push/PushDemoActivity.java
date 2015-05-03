package gof.scut.common.push;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TextView;

import com.baidu.android.pushservice.CustomPushNotificationBuilder;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;

import gof.scut.common.MyApplication;
import gof.scut.common.utils.Log;
import gof.scut.wechatcontacts.R;

public class PushDemoActivity extends Activity {

    private static final String TAG = PushDemoActivity.class.getSimpleName();
    private final Context mContext = PushDemoActivity.this;

    private TextView logText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.logStringCache = Utils.getLogText(getApplicationContext());
        setContentView(R.layout.activity_push_demo);

        logText = (TextView) findViewById(R.id.text_log);

        PushManager.startWork(mContext,
                PushConstants.LOGIN_TYPE_API_KEY,
                MyApplication.SECRIT_KEY);

        notifications();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateDisplay();
    }

    @Override
    public void onDestroy() {
        Utils.setLogText(getApplicationContext(), Utils.logStringCache);
        super.onDestroy();
    }

    // 更新界面显示内容
    private void updateDisplay() {
        Log.d(TAG, "====" + Utils.logStringCache);

        logText.setText(Utils.logStringCache);
    }


    private void notifications() {
        Resources resource = this.getResources();
        String pkgName = this.getPackageName();
        CustomPushNotificationBuilder cBuilder = new CustomPushNotificationBuilder(
                getApplicationContext(), resource.getIdentifier(
                "notification_custom_builder", "layout", pkgName),
                resource.getIdentifier("notification_icon", "id", pkgName),
                resource.getIdentifier("notification_title", "id", pkgName),
                resource.getIdentifier("notification_text", "id", pkgName));
        cBuilder.setNotificationFlags(Notification.FLAG_AUTO_CANCEL);
        cBuilder.setNotificationDefaults(Notification.DEFAULT_SOUND
                | Notification.DEFAULT_VIBRATE);
        cBuilder.setStatusbarIcon(this.getApplicationInfo().icon);
        cBuilder.setLayoutDrawable(resource.getIdentifier(
                "simple_notification_icon", "drawable", pkgName));
        PushManager.setNotificationBuilder(this, 1, cBuilder);
    }

}
