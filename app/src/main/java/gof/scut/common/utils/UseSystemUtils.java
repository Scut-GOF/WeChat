package gof.scut.common.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;


public class UseSystemUtils {
    public static void sendMsg(Context context, String sentTo) {
        Uri smsToUri = Uri.parse("smsto:" + sentTo);
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        intent.putExtra("sms_body", "");
        context.startActivity(intent);
    }

    public static void sysCall(Context context, String callTo) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + callTo));
        context.startActivity(intent);
    }

}
