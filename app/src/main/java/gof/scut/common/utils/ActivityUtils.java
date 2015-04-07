package gof.scut.common.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.io.Serializable;


public class ActivityUtils {
    public static void ActivitySkip(Context context, Class<?> toClass) {
        Intent intent = new Intent();
        intent.setClass(context, toClass);
        context.startActivity(intent);
    }

    public static void ActivitySkipWithObject(Context context, Class<?> toClass, Serializable obj) {
        Intent intent = new Intent();
        intent.setClass(context, toClass);
        Bundle bundle = new Bundle();
        bundle.putSerializable("IdObj", obj);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

}
