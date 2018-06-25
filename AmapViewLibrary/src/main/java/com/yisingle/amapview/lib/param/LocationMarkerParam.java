package com.yisingle.amapview.lib.param;

import android.support.annotation.DrawableRes;

import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CircleOptions;
import com.yisingle.amap.lib.R;
import com.yisingle.amapview.lib.base.param.BaseMarkerParam;
import com.yisingle.amapview.lib.utils.AmapOptionsUtils;

/**
 * @author jikun
 * Created by jikun on 2018/5/16.
 */
public class LocationMarkerParam extends BaseMarkerParam {
    /**
     * 没有方向传感器的时候的图片
     */
    private @DrawableRes
    int withOutSensorDrawableId = 0;

    /**
     * 设置一些默认的CircleOptions的值
     */
    private CircleOptions circleOptions;


    /**
     * 每次定位的时间
     */
    private int locationDurtion;


    public LocationMarkerParam() {
        //在这里设置一些默认参数

        getOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.navi_map_gps_locked));
        setWithOutSensorDrawableId(R.mipmap.gps_point);
        //设置锚点在图片中间
        getOptions().anchor(0.5f, 0.5f);
        locationDurtion = 5000;
        circleOptions = AmapOptionsUtils.getDefaultCircleOptions();

    }

    public int getWithOutSensorDrawableId() {
        return withOutSensorDrawableId;
    }

    public void setWithOutSensorDrawableId(int withOutSensorDrawableId) {
        this.withOutSensorDrawableId = withOutSensorDrawableId;
    }


    public CircleOptions getCircleOptions() {
        return circleOptions;
    }

    public void setCircleOptions(CircleOptions circleOptions) {
        this.circleOptions = circleOptions;
    }

    public int getLocationDurtion() {
        return locationDurtion;
    }

    public void setLocationDurtion(int locationDurtion) {
        this.locationDurtion = locationDurtion;
    }
}
