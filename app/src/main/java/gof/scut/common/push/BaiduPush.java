package gof.scut.common.push;


import android.app.Notification;
import android.content.Context;
import android.content.res.Resources;

import com.baidu.android.pushservice.CustomPushNotificationBuilder;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Map;

import gof.scut.common.MyApplication;
import gof.scut.common.utils.Log;


public class BaiduPush {
    
    private final static String TAG = BaiduPush.class.getSimpleName();
    
    public final static String mUrl = "http://channel.api.duapp.com/rest/2.0/channel/";// 基础url

    public final static String HTTP_METHOD_POST = "POST";

    public static final String SEND_MSG_ERROR = "send_msg_error";
    
    private final static int HTTP_CONNECT_TIMEOUT = 10000;// 连接超时时间，10s
    private final static int HTTP_READ_TIMEOUT = 10000;// 读消息超时时间，10s
    
    public String mHttpMethod;// 请求方式，Post or Get
    public String mSecretKey;// 安全key

    /**
     * 构造函数
     * 
     * @param http_mehtod
     *            请求方式
     * @param secret_key
     *            安全key
     * @param api_key
     *            应用key
     */
    public BaiduPush(String http_mehtod, String secret_key, String api_key) {
        mHttpMethod = http_mehtod;
        mSecretKey = secret_key;
        RestApi.mApiKey = api_key;
    }

    //
    public static void PushInit(Context context){
        String pkgName = context.getPackageName();
        Resources resource = context.getResources();
        CustomPushNotificationBuilder cBuilder = new CustomPushNotificationBuilder(
                context, resource.getIdentifier(
                "notification_custom_builder", "layout", pkgName),
                resource.getIdentifier("notification_icon", "id", pkgName),
                resource.getIdentifier("notification_title", "id", pkgName),
                resource.getIdentifier("notification_text", "id", pkgName));
        cBuilder.setNotificationFlags(Notification.FLAG_AUTO_CANCEL);
        cBuilder.setNotificationDefaults(Notification.DEFAULT_SOUND
                | Notification.DEFAULT_VIBRATE);
        cBuilder.setStatusbarIcon(context.getApplicationInfo().icon);
        cBuilder.setLayoutDrawable(resource.getIdentifier(
                "simple_notification_icon", "drawable", pkgName));
        PushManager.setNotificationBuilder(context, 1, cBuilder);

    }

    public static void StartWork(Context context){
        PushManager.startWork(context,
                PushConstants.LOGIN_TYPE_API_KEY,
                MyApplication.API_KEY);
    }

    /**
     * url编码方式
     * 
     * @param str
     *            指定编码方式，未指定默认为utf-8
     * @return
     * @throws UnsupportedEncodingException
     */
    private String urlencode(String str) throws UnsupportedEncodingException {
        String rc = URLEncoder.encode(str, "utf-8");
        return rc.replace("*", "%2A");
    }

    /**
     * 将字符串转换称json格式
     * 
     * @param str
     * @return
     */
    public String jsonencode(String str) {
        String rc = str.replace("\\", "\\\\");
        rc = rc.replace("\"", "\\\"");
        rc = rc.replace("\'", "\\\'");
        return rc;
    }

    /**
     * 执行Post请求前数据处理，加密之类
     * 
     * @param data
     *            请求的数据
     * @return
     */
    public String PostHttpRequest(RestApi data) {

        StringBuilder sb = new StringBuilder();

        String channel = data.remove(RestApi._CHANNEL_ID);
        if (channel == null)
            channel = "channel";

        try {
            data.put(RestApi._TIMESTAMP,
                    Long.toString(System.currentTimeMillis() / 1000));
            data.remove(RestApi._SIGN);

            sb.append(mHttpMethod);
            sb.append(mUrl);
            sb.append(channel);
            for (Map.Entry<String, String> i : data.entrySet()) {
                sb.append(i.getKey());
                sb.append('=');
                sb.append(i.getValue());
            }
            sb.append(mSecretKey);

            Log.d(TAG, "PostHttpRequest before" + sb.toString());

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();

            md.update(urlencode(sb.toString()).getBytes());
            byte[] md5 = md.digest();

            sb.setLength(0);
            for (byte b : md5)
                sb.append(String.format("%02x", b & 0xff));
            data.put(RestApi._SIGN, sb.toString());

            Log.d(TAG, "PostHttpRequest MD5:" + urlencode(sb.toString()));

            sb.setLength(0);
            for (Map.Entry<String, String> i : data.entrySet()) {
                sb.append(i.getKey());
                sb.append('=');
                sb.append(urlencode(i.getValue()));
                sb.append('&');
            }
            sb.setLength(sb.length() - 1);


        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "PostHttpRequest Exception:" + e.getMessage());
            return SEND_MSG_ERROR;//消息发送失败，返回错误，执行重发
        }

        StringBuilder response = new StringBuilder();
        HttpRequest(mUrl + channel, sb.toString(), response);
        return response.toString();
    }

    /**
     * 执行Post请求
     * 
     * @param url
     *            基础url
     * @param query
     *            提交的数据
     * @param out
     *            服务器回复的字符串
     * @return
     */
    private int HttpRequest(String url, String query, StringBuilder out) {

        URL urlobj;
        HttpURLConnection connection = null;

        try {
            urlobj = new URL(url);
            connection = (HttpURLConnection) urlobj.openConnection();
            connection.setRequestMethod("POST");

            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            connection
                    .setRequestProperty("Content-Length", "" + query.length());
            connection.setRequestProperty("charset", "utf-8");

            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            connection.setConnectTimeout(HTTP_CONNECT_TIMEOUT);
            connection.setReadTimeout(HTTP_READ_TIMEOUT);

            // Send request
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.writeBytes(query.toString());
            wr.flush();
            wr.close();

            // Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;

            while ((line = rd.readLine()) != null) {
                out.append(line);
                out.append('\r');
            }
            rd.close();

        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "HttpRequest Exception:" + e.getMessage());
            out.append(SEND_MSG_ERROR);//消息发送失败，返回错误，执行重发
        }

        if (connection != null)
            connection.disconnect();

        return 0;
    }

    //
    // REST APIs
    //

    private final static String MSGKEY = "msgkey";

    /**
     * 给指定用户推送通知
     * 
     * @param title
     * @param message
     * @param userid
     * @return
     */
    public String PushNotify(String title, String message, String userid,int id) {
        RestApi ra = new RestApi(RestApi.METHOD_PUSH_MESSAGE);
        ra.put(RestApi._MESSAGE_TYPE, RestApi.MESSAGE_TYPE_NOTIFY);

        String msg = String
                .format("{'title':'%s','description':'%s'," +
                                "'custom_content':{'test':'%s'}"
                                + ",'notification_builder_id':0,'notification_basic_style':7,'open_type':3}",
                        title, jsonencode(message),id);

        Log.d(TAG, msg);

        ra.put(RestApi._MESSAGES, msg);
        ra.put(RestApi._MESSAGE_KEYS, MSGKEY);
        ra.put(RestApi._PUSH_TYPE, RestApi.PUSH_TYPE_USER);
        ra.put(RestApi._USER_ID, userid);
        return PostHttpRequest(ra);
    }

    /**
     * 给所有用户推送通知
     * 
     * @param title
     * @param message
     * @return
     */
    public String PushNotifyAll(String title, String message ,int id) {
        RestApi ra = new RestApi(RestApi.METHOD_PUSH_MESSAGE);
        ra.put(RestApi._MESSAGE_TYPE, RestApi.MESSAGE_TYPE_NOTIFY);

        String msg = String
                .format("{'title':'%s','description':'%s','custom_content':{'test':'%s'},'notification_builder_id':0,'notification_basic_style':7,'open_type':3}",
                        title, jsonencode(message),id);

        Log.d(TAG, msg);

        ra.put(RestApi._MESSAGES, msg);
        ra.put(RestApi._MESSAGE_KEYS, MSGKEY);
        ra.put(RestApi._PUSH_TYPE, RestApi.PUSH_TYPE_ALL);
        return PostHttpRequest(ra);
    }

}