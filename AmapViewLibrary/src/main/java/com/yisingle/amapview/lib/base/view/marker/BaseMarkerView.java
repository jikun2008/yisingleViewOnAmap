package com.yisingle.amapview.lib.base.view.marker;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.yisingle.amap.lib.R;
import com.yisingle.amapview.lib.base.param.BaseMarkerParam;
import com.yisingle.amapview.lib.utils.YiSingleDeBug;
import com.yisingle.amapview.lib.viewholder.MapInfoWindowViewHolder;


/**
 * @author jikun
 * @date 17/5/19
 */

public abstract class BaseMarkerView<P extends BaseMarkerParam, W> extends AbstractMarkerView<P> implements IMarkerView<W> {


    private W infoData;


    private InfoWindowView infoWindowView;


    protected BaseMarkerView(@NonNull Context context, @NonNull AMap amap, @NonNull P param) {
        super(context, amap);
        this.param = param;
    }


    @Override
    public void addToMap() {
        if (null != getAmap()) {
            if (isRemove()) {
                marker = getAmap().addMarker(param.getOptions());
            }
        }
    }

    @Override
    public void removeFromMap() {
        if (null != marker) {
            marker.remove();
            marker = null;
        }
        if (null != infoMarker) {
            infoMarker.remove();
            infoMarker = null;
        }
    }

    @Override
    public void destory() {
        if (null != marker) {
            marker.remove();
            marker = null;

        }
        stopMove();
        infoData = null;
        if (null != infoWindowView) {
            infoWindowView.destory();
        }
        super.destory();
    }

    @Override
    public boolean isRemove() {
        return marker == null;
    }


    @Override
    public void setInfoWindowView(@NonNull InfoWindowView<W> infoWindowView) {
        this.infoWindowView = infoWindowView;
        if (null == infoWindowView) {
            Log.e("BaseMarkerView", "BaseMarkerView please do not setInfoWindowView(null)");
        } else {
            infoWindowView.init(getContext());
            this.infoData = infoWindowView.getInfoData();
        }
    }

    @Override
    public void showInfoWindow(W data) {
        infoData = data;
        setShowInfoWindow(true);
        if (null != infoWindowView) {
            infoWindowView.setInfoData(data);
        }

        if (null == marker || null == infoWindowView) {
            return;
        }

        MarkerOptions infoWindowParam = getInfoWindowMarkerOptions(infoWindowView);
        if (null == infoMarker || infoMarker.isRemoved()) {

            infoMarker = getAmap().addMarker(infoWindowParam);

            infoMarker.setAnchor(infoWindowParam.getAnchorU(), 1f);


        } else {
            if (!infoMarker.isVisible()) {
                infoMarker.setVisible(true);
            }
            infoMarker.setPosition(infoWindowParam.getPosition());
            infoMarker.setAnchor(infoWindowParam.getAnchorU(), 1f);
            infoMarker.setIcon(infoWindowParam.getIcon());

        }


    }


    @Override
    public void hideInfoWindow() {
        setShowInfoWindow(false);
        if (null != infoMarker) {
            infoMarker.setVisible(false);
        }
    }


    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public W getInfoData() {
        return infoData;
    }

    public void setInfoData(W infoData) {
        this.infoData = infoData;
    }


    @Override
    public void setPosition(LatLng latLng) {
        super.setPosition(latLng);
        if (null != marker) {
            if (null != infoWindowView && isShowInfoWindow()) {
                showInfoWindow(infoData);
            }
        }


    }


    /**
     * @author jikun
     * Created by jikun on 2018/5/30.
     */
    public static abstract class InfoWindowView<W> {

        private W infoData;

        private View infoWindowView;

        private Context context;

        private @LayoutRes
        int layoutId;

        private MapInfoWindowViewHolder viewHolder;

        public InfoWindowView(@LayoutRes int layoutId, W infoData) {
            this.layoutId = layoutId;
            this.infoData = infoData;

        }

        protected void init(Context context) {
            this.context = context;

            infoWindowView = LayoutInflater.from(context).inflate(R.layout.lib_info_window, null);
            LinearLayout ll_real_layout = infoWindowView.findViewById(R.id.ll_real_layout);
            View view = LayoutInflater.from(context).inflate(layoutId, null);
            ll_real_layout.addView(view);
            viewHolder = new MapInfoWindowViewHolder(0, view);
        }


        public abstract void bindData(MapInfoWindowViewHolder viewHolder, W data);


        protected View getView(int width, int height) {

            TextView seatView = infoWindowView.findViewById(R.id.seatView);
            seatView.setWidth(width);
            seatView.setHeight(height);

            if (YiSingleDeBug.isdebug) {
                seatView.setVisibility(View.VISIBLE);
                seatView.setBackgroundResource(android.R.color.holo_green_dark);
                infoWindowView.setBackgroundResource(android.R.color.holo_blue_light);
            }

            bindData(viewHolder, infoData);

            return infoWindowView;

        }


        public void destory() {

        }

        public void setInfoData(W infoData) {
            this.infoData = infoData;
        }

        public W getInfoData() {
            return infoData;
        }
    }

}
