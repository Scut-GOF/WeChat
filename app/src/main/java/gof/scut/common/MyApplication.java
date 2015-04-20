package gof.scut.common;

import android.app.Application;
import android.content.res.Configuration;

import com.google.gson.Gson;

/**
 *
 * Created by zm on 2015/4/20.
 */
public class MyApplication extends Application{

    private static MyApplication myApplication;
    private static Gson mGson;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
    }

    public synchronized static MyApplication getInstance() {
        return myApplication;
    }

    public synchronized static Gson getGson() {
        if (mGson == null){
            mGson = new Gson();
        }
        return mGson;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
