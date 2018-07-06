package com.yisingle.amapview.lib.view;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.yisingle.amapview.lib.base.view.circle.BaseCircleView;
import com.yisingle.amapview.lib.base.view.marker.BaseMarkerBuilder;
import com.yisingle.amapview.lib.base.view.marker.BaseMarkerView;
import com.yisingle.amapview.lib.param.LocationMarkerParam;
import com.yisingle.amapview.lib.helper.AmapLocationHelper;
import com.yisingle.amapview.lib.helper.SensorEventHelper;

/**
 * 定位Marker
 *
 * @author jikun
 * Created by jikun on 2018/4/20.
 */
public class LocationMarkerView<W> extends BaseMarkerView<LocationMarkerParam, W> {


    /**
     * 方向传感器帮助类
     */
    private SensorEventHelper sensorEventHelper;


    /**
     * 定位帮助类
     */
    private AmapLocationHelper locationHelper;


    private OnLocationMarkerViewListener listener;


    /**
     * 圆形范围View
     */
    private BaseCircleView circleView;


    private LocationMarkerView(@NonNull Context context, @NonNull AMap amap, @NonNull LocationMarkerParam param) {
        super(context, amap, param);
        circleView = new BaseCircleView.Builder(getContext(), getAmap()).create();
    }


    @Override
    public void addToMap() {
        if (isRemove()) {
            setVisible(true);
            super.addToMap();
            if (null != circleView) {
                circleView.getOptions().center(getParam().getOptions().getPosition());
                circleView.addToMap();
            }

        }
    }

    public void setListener(OnLocationMarkerViewListener listener) {
        this.listener = listener;
    }


    @Override
    public void setPosition(LatLng latLng) {
        getParam().getOptions().position(latLng);
        if (isRemove()) {
            addToMap();
        } else {
            super.setPosition(latLng);
            if (null != circleView) {
                circleView.setCenter(latLng);
            }
        }

    }

    /**
     * 开始定位
     */
    public void startLocation() {
        if (sensorEventHelper != null) {
            return;
        }
        initSensorEventHelper();
        initLocationHelper();
    }

    @Override
    public void setVisible(boolean isVisible) {
        super.setVisible(isVisible);
        circleView.setVisible(isVisible);
    }


    @Override
    public void removeFromMap() {
        super.removeFromMap();
        if (null != circleView) {
            circleView.removeFromMap();
        }
        unInitSensorEventHelper();
        unInitLocationHelper();
    }

    @Override
    public void destory() {
        removeFromMap();
        super.destory();
    }

    /**
     * 初始化定位LocationHelper类
     */
    private void initLocationHelper() {
        if (null == locationHelper) {
            locationHelper = new AmapLocationHelper(getContext(), getParam().getLocationDurtion());

        }

        locationHelper.setOnLocationGetListener(new AmapLocationHelper.OnLocationGetListeneAdapter() {
            @Override
            public void onLocationGetSuccess(AMapLocation loc) {
                setPosition(new LatLng(loc.getLatitude(), loc.getLongitude()));
                if (null != circleView) {
                    //设置定位精度
                    circleView.setRadius(loc.getAccuracy());
                }
                if (null != listener) {
                    listener.onLocationSuccess(loc);
                }


            }

            @Override
            public void onLocationGetFail(AMapLocation loc) {
                super.onLocationGetFail(loc);
                if (null != listener) {
                    listener.onLocationFailed(loc);
                }

            }
        });
        locationHelper.startLocation();
    }


    /**
     * 注销LocationHelper类
     */
    private void unInitLocationHelper() {
        if (null != locationHelper) {
            locationHelper.destroyLocation();
            locationHelper = null;
        }
    }

    /**
     * 初始化方向SensorEventHelper类
     */
    private void initSensorEventHelper() {
        if (null == sensorEventHelper) {
            sensorEventHelper = new SensorEventHelper(getContext());
        }
        sensorEventHelper.setOnRotationListener(new SensorEventHelper.OnRotationListener() {
            @Override
            public void onRotationChange(float angle) {
                if (null != getMarker()) {
                    setRotateAngle(angle);
                }
                if (null != listener) {
                    listener.onRotationSuccess(angle);
                }

            }

            @Override
            public void onRotationFailed(String erroInfo) {
                if (0 != getParam().getWithOutSensorDrawableId()) {
                    setIcon(BitmapDescriptorFactory.fromResource(getParam().getWithOutSensorDrawableId()));
                }
                if (null != listener) {
                    listener.onRotationFailed(erroInfo);
                }


            }
        });
    }


    /**
     * 注销方向SensorEventHelper类
     */
    private void unInitSensorEventHelper() {
        if (null != sensorEventHelper) {
            sensorEventHelper.destroySensorEventHelper();
            sensorEventHelper = null;
        }

    }


    public final static class Builder extends BaseMarkerBuilder<Builder, LocationMarkerParam> {


        public Builder(@NonNull Context context, @NonNull AMap map) {
            super(context, map);
        }

        @Override
        protected LocationMarkerParam returnDefaultParam() {
            return new LocationMarkerParam();
        }

        @Override
        protected Builder getChild() {
            return this;
        }

        @Override
        public <W> LocationMarkerView<W> create() {
            return new LocationMarkerView<>(getContext(), getMap(), getParam());
        }


        /**
         * 设置没有手机方向传感器的图片
         *
         * @param drawableId 图片资源文件
         * @return Builder
         */
        public Builder setWithOutSensorDrawableId(@DrawableRes int drawableId) {
            getParam().setWithOutSensorDrawableId(drawableId);
            return this;

        }

        /**
         * 设置圆的填充颜色。填充颜色是绘制边框以内部分的颜色，ARGB格式。默认透明。
         *
         * @param color - 填充颜色ARGB格式
         */
        public Builder setCircleFillColor(int color) {
            getParam().getCircleOptions().fillColor(color);
            return this;
        }

        public Builder setLocationDurtion(int durtion) {
            getParam().setLocationDurtion(durtion);
            return this;
        }


        /**
         * 设置圆的边框颜色，ARGB格式。如果设置透明，则边框不会被绘制。默认黑色。
         *
         * @param color - 设置边框颜色，ARGB格式。
         * @return Builder
         */
        public Builder setCircleStrokeColor(int color) {
            getParam().getCircleOptions().strokeColor(color);
            return this;
        }


        /**
         * 设置圆的边框宽度，单位像素。参数必须大于等于0，默认10。
         *
         * @param width 边框宽度，单位像素
         * @return Builder
         */
        public Builder setCircleStrokeWidth(float width) {
            getParam().getCircleOptions().strokeWidth(width);
            return this;
        }

        /**
         * 设置圆的可见属性
         *
         * @param visible true为可见，false为不可见
         * @return Builder
         */
        public Builder setCircleVisible(boolean visible) {
            getParam().getCircleOptions().visible(visible);
            return this;

        }

        /**
         * 设置圆的Z轴数值，默认为0。
         *
         * @param zIndex zIndex - z轴数值
         * @return Builder
         */
        public Builder setCircleZindex(float zIndex) {

            getParam().getCircleOptions().zIndex(zIndex);
            return this;

        }


    }

    public interface OnLocationMarkerViewListener {


        /**
         *
         * 定位成功返回
         * @param loc 定位成功返回
         */
        void onLocationSuccess(AMapLocation loc);

        /**
         *
         * 定位失败返回
         * @param loc 定位失败返回
         */
        void onLocationFailed(AMapLocation loc);


        /**
         * 手机角度旋转的时候返回
         * @param angle 角度旋转
         */
        void onRotationSuccess(float angle);


        /**
         * 没有角度相关传感器的时候返回
         * @param erroInfo 错误信息
         */
        void onRotationFailed(String erroInfo);

    }


    public static class OnLocationMarkerViewListenerAdapter implements OnLocationMarkerViewListener {

        @Override
        public void onLocationSuccess(AMapLocation loc) {

        }

        @Override
        public void onLocationFailed(AMapLocation loc) {

        }

        @Override
        public void onRotationSuccess(float angle) {

        }

        @Override
        public void onRotationFailed(String erroInfo) {

        }
    }


}
