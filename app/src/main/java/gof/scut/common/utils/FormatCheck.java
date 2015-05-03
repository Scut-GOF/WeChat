package gof.scut.common.utils;

import android.widget.EditText;

/**
 * Created by cwh on 15-4-27.
 */
public class FormatCheck {
	public static boolean isEditTextEmpty(EditText editText) {
		return editText.getText().toString().equals("");
	}
}
