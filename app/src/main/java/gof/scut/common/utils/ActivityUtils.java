package gof.scut.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;


public class ActivityUtils {
	public static void ActivitySkip(Context context, Class<?> toClass) {
		Intent intent = new Intent();
		intent.setClass(context, toClass);
		context.startActivity(intent);
	}

	public static void ActivityChange(Context context, Class<?> toClass) {
		((Activity) context).finish();
		Intent intent = new Intent();
		intent.setClass(context, toClass);
		context.startActivity(intent);
	}

	public static void startActivityWithObjectForResult
			(Context context, Class<?> toClass, String key, Serializable obj, int requestCode) {
		Intent intent = new Intent();
		intent.setClass(context, toClass);
		Bundle bundle = new Bundle();
		bundle.putSerializable(key, obj);
		intent.putExtras(bundle);
//        int id =((IdObj)bundle.getSerializable("  ")).getId();
		((Activity) context).startActivityForResult(intent, requestCode);
	}


	public static void ActivitySkipWithObject(Context context, Class<?> toClass, String key, Serializable obj) {
		Intent intent = new Intent();
		intent.setClass(context, toClass);
		Bundle bundle = new Bundle();
		bundle.putSerializable(key, obj);
		intent.putExtras(bundle);
//        int id =((IdObj)bundle.getSerializable("  ")).getId();
		context.startActivity(intent);
	}

	public static void sysGallery(Context context, int resultCode) {
		Intent i = new Intent(
				Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		((Activity) context).startActivityForResult(i, resultCode);
	}

	public static View getRootView(Activity context) {
		return ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
	}

	public static Intent getBundleIntent(Bundle bundle) {
		Intent intent = new Intent();
		intent.putExtras(bundle);
		return intent;
	}

	public static Bundle getSingleObjectBundle(String key, Serializable obj) {
		Bundle bundle = new Bundle();
		bundle.putSerializable(key, obj);
		return bundle;
	}

	public static void setActivityResult(Context context, int requestCode, String key, Serializable obj) {
		Intent intent = getBundleIntent(getSingleObjectBundle(key, obj));
		((Activity) context).setResult(requestCode, intent);
	}
}
