package com.yisingle.amapview.lib.utils;

import android.util.Log;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.autonavi.amap.mapcore.IPoint;
import com.autonavi.amap.mapcore.MapProjection;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jikun
 * Created by jikun on 2018/6/7.
 */
public class MoveUtils {

    private CustomAnimator customAnimator = new CustomAnimator();

    private int index;

    private IPoint startIPoint = new IPoint(0, 0);

    private IPoint nextPoint = new IPoint();


    private boolean isOver = false;

    private int speed = 20;


    private List<LatLng> latLngList = new ArrayList<>();


    private OnCallBack callBack;


    private List<LatLng> produceResumeList(LatLng currentLatlng, List<LatLng> list) {
        List<LatLng> nowList = new ArrayList<>();
        if (currentLatlng == null && list.size() == 1) {
            nowList.addAll(list);
            if (null != callBack) {
                IPoint iPoint = new IPoint();
                MapProjection.lonlat2Geo(list.get(0).longitude, list.get(0).latitude, iPoint);
                callBack.onSetGeoPoint(iPoint);
            }

        } else if ((currentLatlng != null && list.size() == 1)) {

            nowList.add(currentLatlng);
            nowList.addAll(list);
        } else {
            nowList.addAll(list);
        }
        return nowList;

    }

    private List<LatLng> produceRightNowList(List<LatLng> list) {
        if (list.size() == 1) {
            if (null != callBack) {
                IPoint iPoint = new IPoint();
                MapProjection.lonlat2Geo(list.get(0).longitude, list.get(0).latitude, iPoint);
                callBack.onSetGeoPoint(iPoint);
            }
        }
        return list;

    }


    public void startMove(LatLng latLng, List<LatLng> list, boolean isResume) {
        if (isResume) {
            if (null == customAnimator || !customAnimator.isRunning()) {
                beginMove(produceResumeList(latLng, list));
            } else {
                this.latLngList.addAll(list);
            }
        } else {
            beginMove(produceRightNowList(list));

        }
    }

    private void beginMove(List<LatLng> list) {
        setLatLngList(list);
        index = 0;
        stopMove();
        createAnimator();
    }


    public void stopMove() {
        if (null != customAnimator) {
            customAnimator.end();
        }

    }

    public void setCallBack(OnCallBack callBack) {
        this.callBack = callBack;
    }

    private void createAnimator() {

        customAnimator.end();
        customAnimator.setOnTimeListener(new CustomAnimator.OnTimeListener() {
            @Override
            public void onRepeatStart() {
                if (index < latLngList.size()) {
                    index = index + 1;
                }
                if (index < latLngList.size() - 1) {
                    calculate();
                } else {
                    isOver = true;
                    customAnimator.end();
                }

            }

            @Override
            public void onStart() {
                index = 0;
                isOver = false;
                if (latLngList.size() >= 2) {
                    calculate();
                } else {
                    Log.e("测试代码", "测试代码--未能开始移动 因为坐标集合数量小于2");
                    customAnimator.end();
                }

            }

            @Override
            public void onUpdate(float t) {


                if (!isOver) {
                    // Log.e("测试代码", "测试代码---onUpdate");
                    Float value = t;
                    int plugX = nextPoint.x - startIPoint.x;
                    int plugY = nextPoint.y - startIPoint.y;
                    IPoint point = new IPoint((int) ((double) startIPoint.x + (double) plugX * value), (int) ((double) startIPoint.y + (double) plugY * value));


                    if (null != callBack) {
                        callBack.onSetGeoPoint(point);
                    }

                }

            }
        });
        customAnimator.start();

    }

    private void calculate() {
        float distance = AMapUtils.calculateLineDistance(latLngList.get(index), latLngList.get(index + 1));
        if (index < latLngList.size() - 1) {
            MapProjection.lonlat2Geo(latLngList.get(index).longitude, latLngList.get(index).latitude, startIPoint);
            MapProjection.lonlat2Geo(latLngList.get(index + 1).longitude, latLngList.get(index + 1).latitude, nextPoint);
            float rotate = getRotate(startIPoint, nextPoint);


            if (null != callBack) {
                callBack.onSetRotateAngle(rotate);
            }


            int time = new BigDecimal(distance).divide(new BigDecimal(speed), 3, RoundingMode.HALF_DOWN).multiply(new BigDecimal(1000)).intValue();

            Log.e("测试代码", "测试代码time=" + time + "index=" + index);

            customAnimator.setDuration(time);
        }


    }


    private float getRotate(IPoint var1, IPoint var2) {
        if (var1 != null && var2 != null) {
            double var3 = (double) var2.y;
            double var5 = (double) var1.y;
            double var7 = (double) var1.x;
            return (float) (Math.atan2((double) var2.x - var7, var5 - var3) / 3.141592653589793D * 180.0D);
        } else {
            return 0.0F;
        }
    }

    public void setLatLngList(List<LatLng> latLngList) {
        this.latLngList = latLngList;
    }


    public interface OnCallBack {
        void onSetRotateAngle(float rotate);

        void onSetGeoPoint(IPoint point);
    }


}
