package com.yisingle.amapview.lib.view;

import android.content.Context;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.text.TextPaint;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.LatLng;
import com.yisingle.amapview.lib.base.view.marker.BaseMarkerBuilder;
import com.yisingle.amapview.lib.base.view.marker.BaseMarkerView;
import com.yisingle.amapview.lib.param.PointMarkerParam;
import com.yisingle.amapview.lib.param.TextMarkerParam;

/**
 * @author jikun
 * Created by jikun on 2018/5/16.
 */
public class PointMarkerView<W> extends BaseMarkerView<PointMarkerParam, W> {

    private TextMarkerView textMarkerView;

    private PointMarkerView(@NonNull Context context, @NonNull AMap amap, @NonNull PointMarkerParam param) {
        super(context, amap, param);
    }


    @Override
    public void addToMap() {
        if (isRemove()) {
            super.addToMap();
            if (null != textMarkerView) {
                textMarkerView.addToMap();
            }
        }
    }


    @Override
    public void removeFromMap() {
        super.removeFromMap();
        if (null != textMarkerView) {
            textMarkerView.removeFromMap();
        }
    }


    public void changeUI(LatLng position, String text) {
        setPosition(position);
        setText(text);
    }

    @Override
    public void setPosition(LatLng position) {
        super.setPosition(position);
        textMarkerView.setPosition(position);
    }


    public void setText(String text) {
        textMarkerView.setText(text);

    }


    public void setTextSize(float textsize) {
        textMarkerView.setTextSize(textsize);

    }


    public void setTextColor(int color) {
        textMarkerView.setTextColor(color);
    }

    public void setTextPointIcon(BitmapDescriptor icon) {
        textMarkerView.setIcon(icon);
    }

    @Override
    public void destory() {
        super.destory();
        if (null != textMarkerView) {
            textMarkerView.destory();
        }
    }


    public void setTextMarkerView(TextMarkerView textMarkerView) {
        this.textMarkerView = textMarkerView;
    }

    public TextMarkerView getTextMarkerView() {
        return textMarkerView;
    }

    public final static class Builder extends BaseMarkerBuilder<Builder, PointMarkerParam> {

        private TextMarkerView.Builder textBuilder;

        public Builder(@NonNull Context context, @NonNull AMap map) {
            super(context, map);


        }

        @Override
        protected PointMarkerParam returnDefaultParam() {
            textBuilder = new TextMarkerView.Builder(getContext(), getMap());
            return new PointMarkerParam();
        }

        @Override
        protected Builder getChild() {
            return this;
        }

        @Override
        public <W> PointMarkerView<W> create() {

            PointMarkerView<W> pointMarkerView = new PointMarkerView<W>(getContext(), getMap(), getParam());
            pointMarkerView.setTextMarkerView(textBuilder.create());
            pointMarkerView.addToMap();
            return pointMarkerView;
        }


        @Override
        public Builder setPosition(LatLng position) {
            super.setPosition(position);
            textBuilder.setPosition(position);
            return this;
        }


        public Builder setTextMarkerBuilder(@NonNull TextMarkerView.Builder textMarkerBuilder) {
            //代理模式 随意组装内部需要TextMarkerView
            textBuilder = textMarkerBuilder;
            return this;
        }


        public Builder setTextPaint(@NonNull TextPaint textPaint) {
            textBuilder.setTextPaint(textPaint);
            return this;
        }

        public Builder setText(String text) {
            textBuilder.setText(text);
            return this;
        }

        public Builder setTextPaddingLeftOrRight(int padding) {
            textBuilder.setTextPaddingLeftOrRight(padding);
            return this;
        }

        public Builder setTextRowSpaceMult(@FloatRange(from = 1f) float textSpaceMult) {
            textBuilder.setTextRowSpaceMult(textSpaceMult);
            return this;
        }


        public Builder setTextRowSpaceAdd(@IntRange(from = 0) int textSpaceAdd) {
            textBuilder.setTextRowSpaceAdd(textSpaceAdd);
            return this;
        }


        public Builder setTextMaxTextLength(int maxTextLength) {
            textBuilder.setTextMaxTextLength(maxTextLength);
            return this;
        }

        public Builder setTextOnlyTextShow(boolean onlyTextShow) {
            textBuilder.setTextOnlyTextShow(onlyTextShow);
            return this;
        }


        /**
         * 设置描边的范围
         *
         * @param width 范围
         * @return
         */
        public Builder setTextStrokeWidth(float width) {
            textBuilder.setTextStrokeWidth(width);
            return this;
        }

        /**
         * 设置描边的颜色值
         *
         * @param color color
         * @return
         */
        public Builder setTextStrokeColor(int color) {
            textBuilder.setTextStrokeColor(color);
            return this;
        }


        public Builder setTextAlign(@TextMarkerParam.TextAlign int algin) {
            textBuilder.setTextAlign(algin);

            return this;
        }


        public Builder setTextSize(float textSize) {
            textBuilder.setTextSize(textSize);
            return this;
        }


        public Builder setTextColor(int color) {
            textBuilder.setTextColor(color);
            return this;
        }


        public Builder setTextPointIcon(BitmapDescriptor bitmapDescriptor) {
            textBuilder.setTextPointIcon(bitmapDescriptor);
            return this;
        }


    }
}
