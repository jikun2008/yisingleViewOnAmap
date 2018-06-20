package com.yisingle.amapview.lib.base.view.marker;


import android.support.annotation.NonNull;


/**
 * @author jikun
 * Created by jikun on 2018/4/18.
 */
public interface IMarkerView<W> {


    void bindInfoWindowView(@NonNull BaseMarkerView.InfoWindowView<W> infoWindowView);


    void showInfoWindow(W data);

    void hideInfoWindow();


}
