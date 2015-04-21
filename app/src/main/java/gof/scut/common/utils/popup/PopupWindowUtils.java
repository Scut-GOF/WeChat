package gof.scut.common.utils.popup;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.PopupWindow;

import gof.scut.common.utils.ActivityUtils;
import gof.scut.wechatcontacts.R;

/**
 * Created by Administrator on 2015/4/18.
 */
public abstract class PopupWindowUtils {
	PopupWindow popupWindow;
	View popupView;
	Context context;

	//prepare used in init;component event write in init
	public void prepare(Context paraContext, int rootLayoutRId) {
		context = paraContext;
		popupView = LayoutInflater.from(context).inflate(
				rootLayoutRId, (ViewGroup) ActivityUtils.getRootView((Activity) context), false);
		popupWindow = new PopupWindow(popupView,
				ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);

		ColorDrawable dw = new ColorDrawable(0x00000000);
		popupView.setBackgroundDrawable(dw);
		popupView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
			}

		});

		popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			@Override
			public void onDismiss() {

			}

		});
		popupView.setFocusable(true);
		popupView.setFocusableInTouchMode(true);
		popupView.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
				if (arg1 == KeyEvent.KEYCODE_BACK) {
					popupWindow.dismiss();
					return true;
				}
				return false;
			}
		});
	}

	public abstract void initPopupWindow();

	public void popWindowAtCenter(int parentID, int animStartID) {
		popupWindow.update();
		popupWindow.showAtLocation(((Activity) context).findViewById(parentID), Gravity.CENTER, 0, 0);
		Animation anim1 = AnimationUtils.loadAnimation(context, R.anim.scale_center_enter);
		(popupView.findViewById(animStartID)).startAnimation(anim1);
	}

	public void popWindowAsDropDown() {

	}
}
