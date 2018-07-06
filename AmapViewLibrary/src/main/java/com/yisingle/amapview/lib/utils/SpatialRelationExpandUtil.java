package com.yisingle.amapview.lib.utils;

import android.util.Pair;

import com.amap.api.maps.utils.SpatialRelationUtil;
import com.amap.api.services.core.LatLonPoint;
import com.autonavi.amap.mapcore.DPoint;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author jikun
 * Created by jikun on 2018/7/6.
 */
public class SpatialRelationExpandUtil extends SpatialRelationUtil {

    public static Pair<Integer, LatLonPoint> calShortestDistancePoint(List<LatLonPoint> var0, LatLonPoint var1) {
        ArrayList var2;
        try {
            if (var0 == null || var1 == null || var0.size() == 0) {
                return null;
            }

            var2 = new ArrayList();
            int var3 = 0;

            for (Iterator var6 = var0.iterator(); var6.hasNext(); ++var3) {
                LatLonPoint var4 = (LatLonPoint) var6.next();
                var2.add(DPoint.obtain(var4.getLatitude(), var4.getLongitude()));
                if (var4.equals(var1)) {
                    return new Pair(var3, var1);
                }
            }

            DPoint var7 = DPoint.obtain(var1.getLatitude(), var1.getLongitude());
            Pair var8;
            if ((var8 = calShortestDistancePoint(var2, (DPoint) var7)) != null) {
                return new Pair(var8.first, new LatLonPoint(((DPoint) var8.second).x, ((DPoint) var8.second).y));
            }
        } catch (Throwable var5) {
            var2 = null;
            var5.printStackTrace();
        }

        return null;
    }
}
