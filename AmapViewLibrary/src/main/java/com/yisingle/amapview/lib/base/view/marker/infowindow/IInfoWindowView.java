package com.yisingle.amapview.lib.base.view.marker.infowindow;

import com.amap.api.maps.AMap;
import com.yisingle.amapview.lib.viewholder.MapInfoWindowViewHolder;

/**
 * @author jikun
 * Created by jikun on 2018/4/13.
 */
@Deprecated
public  interface IInfoWindowView<W> extends AMap.InfoWindowAdapter {


    void bindData(MapInfoWindowViewHolder viewHolder,int type, W data);


    void destory();


}
