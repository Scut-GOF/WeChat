package gof.scut.common.utils.popup;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import gof.scut.wechatcontacts.R;

/**
 * Created by Administrator on 2015/4/17.
 */
public class PopConfirmUtils extends PopupWindowUtils implements View.OnClickListener {
	TextView confirmTitle;
	Button yes, no;
	TodoOnResult todoOnResult;

	@Override
	public void initPopupWindow() {
		confirmTitle = (TextView) popupView.findViewById(R.id.confirm_title);
		yes = (Button) popupView.findViewById(R.id.bt_yes);
		no = (Button) popupView.findViewById(R.id.bt_no);
		yes.setOnClickListener(this);
		no.setOnClickListener(this);
	}

	public void setTitle(String title) {
		confirmTitle.setText(title);
	}

	public void initTodo(TodoOnResult todoOnResult) {
		this.todoOnResult = todoOnResult;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.bt_yes:
				popupWindow.dismiss();
				todoOnResult.doOnPosResult(null);
				break;
			case R.id.bt_no:
				popupWindow.dismiss();
				todoOnResult.doOnNegResult(null);
				break;
		}
	}
}
