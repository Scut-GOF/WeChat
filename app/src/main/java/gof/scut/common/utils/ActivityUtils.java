package gof.scut.common.utils;

import android.app.Activity;
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

    public static void sysGallery(Context context, int resultCode) {
        Intent i = new Intent(
                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        ((Activity) context).startActivityForResult(i, resultCode);
    }
}
