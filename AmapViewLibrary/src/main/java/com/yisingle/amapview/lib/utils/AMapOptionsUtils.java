package com.yisingle.amapview.lib.utils;


import android.graphics.Color;

import com.amap.api.maps.model.CircleOptions;

/**
 * @author jikun
 * Created by jikun on 2018/4/11.
 */
public class AMapOptionsUtils {

    public static final float Z_INDEX = 2f;


    public static CircleOptions getDefaultCircleOptions() {
        CircleOptions options = new CircleOptions();
        options.strokeWidth(1f);
        options.fillColor(Color.argb(40, 3, 145, 255));
        options.strokeColor(Color.argb(10, 0, 0, 180));
        return options;
    }
}
