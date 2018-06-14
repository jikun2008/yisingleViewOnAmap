package com.yisingle.amapview.lib.utils;

import android.util.Log;

import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.MarkerOptions;

import java.math.BigDecimal;

/**
 * @author jikun
 * Created by jikun on 2018/5/31.
 */
public class InfoWindowUtils {

    /**
     * 计算infoWindow占位高度
     *
     * @param markerOptions MarkerOptions
     * @return 高度
     */
    public static int calueInfoWindowSpaceHeight(MarkerOptions markerOptions) {
        return new BigDecimal(markerOptions.getAnchorV()).multiply(new BigDecimal(markerOptions.getIcon().getHeight())).intValue();
    }

    /**
     * 计算infoWindow水平锚点值
     * <p>
     * 公式为  return 值=(0.5*bigWidth-0.5*littleWidth+锚点值*littleWidth)/bigWidth
     *
     * @return 锚点值
     */
    public static float calueHorizontalInfoAnchor(MarkerOptions markerOptions, BitmapDescriptor viewBitmapDescriptor) {

        int bigWidth = viewBitmapDescriptor != null ? viewBitmapDescriptor.getWidth() : 0;
        int littleWidth = markerOptions.getIcon().getWidth();

        float anchorU = markerOptions.getAnchorU();
        //Log.e("测试代码", "测试代码calueHorizontalInfoAnchor----bigWidth=" + bigWidth + "----littleWidth=" + littleWidth + "---原来的anchorU=" + anchorU);
        if (bigWidth > littleWidth) {
            //x 的 值
            BigDecimal x = new BigDecimal(bigWidth);
            BigDecimal y = new BigDecimal(littleWidth);
            BigDecimal u = new BigDecimal(anchorU);
            BigDecimal x2 = x.divide(new BigDecimal(2), 4, BigDecimal.ROUND_HALF_UP);
            BigDecimal y2 = y.divide(new BigDecimal(2), 4, BigDecimal.ROUND_HALF_UP);
            BigDecimal u_y = y.multiply(u);
            BigDecimal subtractX2_Y2 = x2.subtract(y2);
            float now = subtractX2_Y2.add(u_y).divide(x, 4, BigDecimal.ROUND_HALF_UP).floatValue();
            //Log.e("测试代码", "测试代码now" + now);
            return now;

        } else {
            return markerOptions.getAnchorU();
        }

    }
}
