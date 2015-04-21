package gof.scut.common.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Administrator on 2015/4/10.
 */
public class BitmapUtils {
	private static final String TAG = "BitmapUtil";

	public static Bitmap decodeBitmapFromPath(String path) {
		try {
			File file = new File(path);
			if (!file.exists() || !file.isFile())
				return null;

			BitmapFactory.Options bfOptions = new BitmapFactory.Options();
			bfOptions.inPreferredConfig = Bitmap.Config.RGB_565;
			bfOptions.inInputShareable = true;
			bfOptions.inDither = false;
			bfOptions.inPurgeable = true;
			bfOptions.inTempStorage = new byte[32 * 1024];

			FileInputStream fs = null;
			try {
				fs = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				Log.e(TAG, e.getMessage());
			}
			Bitmap bmp = null;
			if (fs != null)
				try {
					bmp = BitmapFactory.decodeFileDescriptor(fs.getFD(), null,
							bfOptions);
				} catch (IOException e) {
					Log.e(TAG, e.getMessage());
				} finally {
					if (fs != null) {
						try {
							fs.close();
						} catch (IOException e) {
							Log.e(TAG, e.getMessage());
						}
					}
				}
			return bmp;
		} catch (Throwable t) {
			if (t != null)
				Log.e("exception", "BitmapUtil.decodeBitmapFromPath(String path) Exception " + t.getMessage());
			return null;
		}
	}
}
