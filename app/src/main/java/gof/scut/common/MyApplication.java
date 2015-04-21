package gof.scut.common;

import android.app.Application;
import android.content.res.Configuration;
import android.os.StrictMode;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

/**
 *
 * Created by zm on 2015/4/20.
 */
public class MyApplication extends Application{

    private static MyApplication myApplication;
    public static RequestQueue requestQueue;
    private static Gson mGson;

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
        requestQueue=Volley.newRequestQueue(this);
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
