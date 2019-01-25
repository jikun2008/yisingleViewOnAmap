package com.yisingle.amapview.lib.utils.ditance;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;

import java.util.List;

/**
 * @author jikun
 * Created by jikun on 2018/7/9.
 */
public class DistanceUtils {


    public static float calculateTwoPointDistance(LatLng latLng1, LatLng latLng2) {
        return AMapUtils.calculateLineDistance(latLng1, latLng2);
    }

    public static float calculateTwoPointDistance(LatLng latLng1, LatLonPoint latLonPoint2) {
        return AMapUtils.calculateLineDistance(latLng1, new LatLng(latLonPoint2.getLatitude(), latLonPoint2.getLongitude()));
    }

    public static float calculateTwoPointDistance(LatLonPoint latLonPoint1, LatLng latLng2) {
        return AMapUtils.calculateLineDistance(new LatLng(latLonPoint1.getLatitude(), latLonPoint1.getLongitude()), latLng2);
    }

    public static float calculateTwoPointDistance(LatLonPoint point1, LatLonPoint point2) {
        return AMapUtils.calculateLineDistance(new LatLng(point1.getLatitude(), point1.getLongitude())
                , new LatLng(point2.getLatitude(), point2.getLongitude()));
    }


    public static float calcaulateListDistanceLatLng(List<LatLng> list) {
        LatLng previousLatLng = null;
        float distance = 0f;
        for (LatLng latLng : list) {
            if (null != previousLatLng) {
                distance = distance + calculateTwoPointDistance(previousLatLng, latLng);
            }
            previousLatLng = latLng;
        }

        return distance;

    }

    public static float calcaulateListDistanceLatLonPoint(List<LatLonPoint> list) {
        LatLonPoint previousLatLng = null;
        float distance = 0f;
        for (LatLonPoint latLonPoint : list) {
            if (null != previousLatLng) {
                distance = distance + calculateTwoPointDistance(previousLatLng, latLonPoint);
            }
            previousLatLng = latLonPoint;
        }

        return distance;

    }
}
