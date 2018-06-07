package com.yisingle.amapview.lib.view;

import android.content.Context;
import android.support.annotation.NonNull;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.autonavi.amap.mapcore.IPoint;
import com.yisingle.amapview.lib.base.view.marker.BaseMarkerBuilder;
import com.yisingle.amapview.lib.base.view.marker.BaseMarkerView;
import com.yisingle.amapview.lib.param.MoveMarkerParam;
import com.yisingle.amapview.lib.utils.MoveUtils;

import java.util.List;


/**
 * @author jikun
 * Created by jikun on 2018/6/1.
 */
public class MoveMarkerView<W> extends BaseMarkerView<MoveMarkerParam, W> implements MoveUtils.OnCallBack {


    private MoveUtils moveUtils;


    private MoveMarkerView(@NonNull Context context, @NonNull AMap amap, @NonNull MoveMarkerParam param) {
        super(context, amap, param);
        moveUtils = new MoveUtils();
        moveUtils.setCallBack(this);
    }


    public void setLatLngList(List<LatLng> latLngList) {
        getParam().setLatLngList(latLngList);
        moveUtils.setLatLngList(getParam().getLatLngList());
    }


    public void stopMove() {
        moveUtils.stopMove();

    }


    public void startMove() {
        startMove(getParam().getLatLngList(), false);
    }


    public void startMove(List<LatLng> list) {
        startMove(list, false);
    }


    public void startMove(List<LatLng> list, boolean isResume) {
        moveUtils.startMove(list, isResume);

    }


    @Override
    public void destory() {
        stopMove();
        moveUtils.setCallBack(null);
        super.destory();

    }

    @Override
    public void onSetRotateAngle(float rotate) {
        setRotateAngle(360.0F - rotate + getAmap().getCameraPosition().bearing);

    }

    @Override
    public void onSetGeoPoint(IPoint point) {
        setGeoPoint(point);
    }


    public static final class Builder extends BaseMarkerBuilder<MoveMarkerView.Builder, MoveMarkerParam> {


        public Builder(@NonNull Context context, @NonNull AMap map) {
            super(context, map);
        }

        @Override
        protected MoveMarkerParam returnDefaultParam() {
            return new MoveMarkerParam();
        }


        @Override
        protected MoveMarkerView.Builder getChild() {
            return this;
        }

        @Override
        public <W> MoveMarkerView<W> create() {
            MoveMarkerView<W> moveMarkerView = new MoveMarkerView<>(getContext(), getMap(), getParam());
            moveMarkerView.addToMap();
            return moveMarkerView;
        }


        public MoveMarkerView.Builder setLatLngList(List<LatLng> latLngList) {
            getParam().setLatLngList(latLngList);
            return this;
        }


        public MoveMarkerView.Builder setTotalDuration(int totalDuration) {
            getParam().setTotalDuration(totalDuration);
            return this;
        }


    }


}
