package gof.scut.common.utils;

import android.content.Context;
import android.content.Intent;


public class ActivityUtils {
    public static void ActivitySkip(Context context, Class<?> toClass) {
        Intent intent = new Intent();
        intent.setClass(context, toClass);
        context.startActivity(intent);
    }
}
