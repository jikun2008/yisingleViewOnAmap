package com.yisingle.amapview.lib.base.view.marker.infowindow;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;

import com.amap.api.maps.model.Marker;
import com.yisingle.amapview.lib.viewholder.MapInfoWindowViewHolder;

/**
 * @author jikun
 * Created by jikun on 2018/4/19.
 */
@Deprecated
public abstract class BaseMultipleInfoWindowView<W> implements IInfoWindowView<W> {


    protected Context context;


    private int currentType;

    private W currentData;

    /**
     * layouts indexed with their types
     */
    private SparseIntArray layouts;


    protected View infoWindowView = null;


    public BaseMultipleInfoWindowView(Context context, W data) {
        this.context = context;
        this.currentData = data;

    }

    public void addItemType(int type, @LayoutRes int layoutResId) {
        if (layouts == null) {
            layouts = new SparseIntArray();
        }
        layouts.put(type, layoutResId);
    }


    @Override
    public void destory() {
        context = null;
        infoWindowView = null;

    }

    @Override
    public View getInfoWindow(Marker marker) {
        int layoutId = layouts.get(getCurrentType());
        infoWindowView = LayoutInflater.from(getContext()).inflate(layoutId, null);
        MapInfoWindowViewHolder viewHolder = new MapInfoWindowViewHolder(getCurrentType(), infoWindowView);
        bindData(viewHolder, getCurrentType(), getCurrentData());
        return infoWindowView;
    }


    public void setTypeAndData(int currentType, W currentData) {
        this.currentType = currentType;
        this.currentData = currentData;
    }


    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }


    public void setCurrentType(int currentType) {
        this.currentType = currentType;
    }

    public int getCurrentType() {
        return currentType;
    }

    public W getCurrentData() {
        return currentData;
    }

    public void setCurrentData(W currentData) {
        this.currentData = currentData;
    }

    public Context getContext() {
        return context;
    }
}
