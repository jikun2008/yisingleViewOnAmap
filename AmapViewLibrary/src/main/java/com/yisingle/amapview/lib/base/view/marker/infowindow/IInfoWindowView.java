package com.yisingle.amapview.lib.base.view.marker.infowindow;

import com.amap.api.maps.AMap;
import com.yisingle.amapview.lib.viewholder.MapInfoWindowViewHolder;

/**
 * @author jikun
 * Created by jikun on 2018/4/13.
 */
@Deprecated
public  interface IInfoWindowView<W> extends AMap.InfoWindowAdapter {


    /**
     * 绑定数据
     * @param viewHolder MapInfoWindowViewHolder
     * @param type int
     * @param data W
     */
    void bindData(MapInfoWindowViewHolder viewHolder,int type, W data);


    /**
     * 销毁InfoWindow
     */
    void destory();


}
