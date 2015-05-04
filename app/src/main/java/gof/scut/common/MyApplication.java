package gof.scut.common;

import android.content.res.Configuration;
import android.os.StrictMode;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.baidu.frontia.FrontiaApplication;
import com.google.gson.Gson;

import gof.scut.common.push.BaiduPush;

/**
 *
 * Created by zm on 2015/4/20.
 */
public class MyApplication extends FrontiaApplication {

    public final static String API_KEY = "2VpI4GUI1OsbCgeXvGKo3nAQ";
    public final static String SECRIT_KEY = "OdQjssW5QvWnHthLCyauWMA8YPjV43l5";

	private static MyApplication myApplication;
	public static RequestQueue requestQueue;
	private static Gson mGson;
    private BaiduPush mBaiduPushServer;

	@Override
	public void onCreate() {

		//StrictMode模式
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()

				.detectDiskReads()

				.detectDiskWrites()

				.detectNetwork()

				.penaltyLog()  //以日志的方式输出

				.build());

		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()

				.detectLeakedSqlLiteObjects()

				.penaltyLog() //以日志的方式输出

				.penaltyDeath()

				.build());


		super.onCreate();

		myApplication = this;
		requestQueue = Volley.newRequestQueue(this);

        mBaiduPushServer = new BaiduPush(BaiduPush.HTTP_METHOD_POST,
                SECRIT_KEY, API_KEY);
        BaiduPush.PushInit(getApplicationContext());
	}

	public synchronized static MyApplication getInstance() {
		return myApplication;
	}

	public synchronized static Gson getGson() {
		if (mGson == null) {
			mGson = new Gson();
		}
		return mGson;
	}

    public synchronized BaiduPush getBaiduPush() {
        if (mBaiduPushServer == null)
            mBaiduPushServer = new BaiduPush(BaiduPush.HTTP_METHOD_POST,
                    SECRIT_KEY, API_KEY);
        return mBaiduPushServer;

    }

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
}
