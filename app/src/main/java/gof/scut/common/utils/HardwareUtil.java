package gof.scut.common.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by Administrator on 2015/4/9.
 */
public class HardwareUtil {

    public static double getScreenWidthPhysicalSize(Activity ctx) {
        DisplayMetrics dm = new DisplayMetrics();
        ctx.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels / (160 * dm.density);
    }

    public static double getScreenHeightPhysicalSize(Activity ctx) {
        DisplayMetrics dm = new DisplayMetrics();
        ctx.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels / (160 * dm.density);
    }
}
