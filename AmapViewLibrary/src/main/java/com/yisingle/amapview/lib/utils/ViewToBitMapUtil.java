package com.yisingle.amapview.lib.utils;

import android.graphics.Bitmap;
import android.view.View;

/**
 * @author jikun
 * Created by jikun on 2018/5/31.
 */
public class ViewToBitMapUtil {


    public static Bitmap convertViewToBitmap(View view) {
        view.destroyDrawingCache();
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        return view.getDrawingCache(true);
    }
}
