package com.example.micha.puzzle;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;

/**
 * Everything what is connected with screen cases.
 */
public class Screen {

    public static DimensionsInt getScreenDimensions(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        DimensionsInt dim = new DimensionsInt(size.x, size.y);

        return dim;
    }

    public static int dpToPx(int dp, Activity activity) {
        Resources resources = activity.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return (int) px;
    }
}
