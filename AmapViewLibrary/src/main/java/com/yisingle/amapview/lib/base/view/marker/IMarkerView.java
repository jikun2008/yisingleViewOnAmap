package com.yisingle.amapview.lib.base.view.marker;


import android.support.annotation.NonNull;


/**
 * @author jikun
 * Created by jikun on 2018/4/18.
 */
 interface IMarkerView<W> {


    /**
     * 绑定数据
     *
     * @param infoWindowView BaseInfoWindowView
     */
    void bindInfoWindowView(@NonNull BaseMarkerView.BaseInfoWindowView<W> infoWindowView);


    /**
     * 显示InfoWindow
     *
     * @param data W
     */
    void showInfoWindow(W data);

    /**
     * 隐藏InfoWindow
     */
    void hideInfoWindow();


}
