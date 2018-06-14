package com.yisingle.amapview.lib.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.yisingle.amapview.lib.base.view.marker.BaseMarkerBuilder;
import com.yisingle.amapview.lib.base.view.marker.BaseMarkerView;
import com.yisingle.amapview.lib.param.TextMarkerParam;
import com.yisingle.amapview.lib.utils.YiSingleDeBug;
import com.yisingle.amapview.lib.utils.TextPaintUtils;

import java.math.BigDecimal;

/**
 * @author jikun
 * Created by jikun on 2018/5/7.
 */
public class TextMarkerView<W> extends BaseMarkerView<TextMarkerParam, W> {

    private Marker textMarker;


    private TextMarkerView(@NonNull Context context, @NonNull AMap amap, @NonNull TextMarkerParam param) {
        super(context, amap, param);
    }


    @Override
    public void addToMap() {
        if (isRemove()) {
            super.addToMap();
            getParam().getTextMarkerOptions().position(getParam().getOptions().getPosition());
            StaticLayout staticLayout = createStaticLayout(getParam().getTextPaint());
            //设置锚点参数
            float[] anchor = getanchorByStaticLayout(staticLayout);
            getParam().getTextMarkerOptions().anchor(anchor[0], anchor[1]);

            getParam().getTextMarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(getTextBitMap(staticLayout)));
            getParam().getTextMarkerOptions().zIndex(getParam().getOptions().getZIndex());
            textMarker = getAmap().addMarker(getParam().getTextMarkerOptions());
            getMarker().setVisible(!getParam().isOnlyTextShow());
        }

    }


    @Override
    public void removeFromMap() {
        super.removeFromMap();
        if (null != textMarker) {
            textMarker.remove();
            textMarker = null;
        }

    }


    public void changeUI(LatLng latLng, String text) {
        setPosition(latLng);
        setText(text);

    }

    public void setOnlyTextShow(boolean onlyTextShow) {
        getParam().setOnlyTextShow(onlyTextShow);
        if (null != getMarker()) {
            getMarker().setVisible(!onlyTextShow);
        }
    }

    @Override
    public void setVisible(boolean isVisible) {
        if (null != textMarker) {
            if (getParam().isOnlyTextShow()) {
                super.setVisible(false);
            } else {
                super.setVisible(isVisible);
            }

            textMarker.setVisible(isVisible);
        }
    }

    @Override
    public void setPosition(LatLng latLng) {
        super.setPosition(latLng);
        if (null != textMarker) {
            textMarker.setPosition(latLng);
        }
        setText(getParam().getText());
    }

    public void setText(String text) {
        getParam().setText(text);
        if (null != textMarker) {

            StaticLayout staticLayout = createStaticLayout(getParam().getTextPaint());

            textMarker.setIcon((BitmapDescriptorFactory.fromBitmap(getTextBitMap(staticLayout))));

            //设置锚点参数
            float[] anchor = getanchorByStaticLayout(staticLayout);
            textMarker.setAnchor(anchor[0], anchor[1]);
        }
    }

    public void setTextSize(float textsize) {
        getParam().getTextPaint().setTextSize(textsize);
        setText(getParam().getText());

    }


    public void setTextColor(int color) {
        getParam().getTextPaint().setColor(color);
        setText(getParam().getText());
    }

    @Override
    public void setZIndex(float z) {
        super.setZIndex(z);
        if (null != textMarker) {
            textMarker.setZIndex(z);
        }

    }


    @Override
    public boolean isVisible() {
        return textMarker.isVisible();
    }

    public String getText() {
        return getParam().getText();

    }


    @Override
    public void destory() {
        removeFromMap();
        super.destory();
    }

    @Override
    public boolean isRemove() {
        return textMarker == null;
    }


    private Bitmap getTextBitMap(StaticLayout staticLayout) {
        //获取最大MaxWidth


        Bitmap bitmap = Bitmap.createBitmap(staticLayout.getWidth() + getParam().getPaddingLeftOrRight(),
                staticLayout.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.save();
        translateCanvas(getParam().getAlign(), canvas);
        //测试背景的时候使用一般不使用
        if (YiSingleDeBug.isdebug) {
            testDrawBack(canvas, staticLayout);
        }

        drawTextStroke(canvas);
        staticLayout.draw(canvas);

        canvas.restore();
        return bitmap;
    }

    /**
     * 画描边
     *
     * @param canvas
     */
    private void drawTextStroke(Canvas canvas) {
        TextPaint textPaint = getParam().getStrokeTextPaint();
        textPaint.setTextSize(getParam().getTextPaint().getTextSize());
        StaticLayout staticLayout = createStaticLayout(textPaint);
        staticLayout.draw(canvas);
    }


    private void testDrawBack(Canvas canvas, StaticLayout staticLayout) {
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#FF3030"));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, staticLayout.getWidth(), staticLayout.getHeight(), paint);
    }

    /**
     * 根据staticLayout设置锚点参数
     *
     * @param staticLayout
     * @return float[]
     */
    private float[] getanchorByStaticLayout(StaticLayout staticLayout) {


        float[] anchor = new float[2];


        BigDecimal y = new BigDecimal(0);
        if (staticLayout.getLineCount() > 0) {


            Paint.FontMetrics fontMetrics = getParam().getTextPaint().getFontMetrics();

            BigDecimal textHeight = new BigDecimal(fontMetrics.bottom - fontMetrics.top);


//            int top = staticLayout.getLineTop(0);
//            int bottom = staticLayout.getLineBottom(0);
//            int add = getParam().getTextRowSpaceAdd();
//            float multy = getParam().getTextRowSpaceMult();
//            int lineHeight = bottom - top;
//            BigDecimal textHeight = new BigDecimal(lineHeight);


            y = textHeight.divide(new BigDecimal(staticLayout.getHeight()).multiply(new BigDecimal(2)), 2, BigDecimal.ROUND_HALF_UP);

        }
//        Log.e("测试代码", "测试代码-y=" + y.floatValue());

//        int top = staticLayout.getLineTop(0);
//        int bottom = staticLayout.getLineBottom(0);
//        int height = staticLayout.getHeight();
//        Log.e("测试代码", "测试代码-getSpacingAdd=" + staticLayout.getSpacingAdd());
//        Log.e("测试代码", "测试代码-top=" + top);
//        Log.e("测试代码", "测试代码-bottom=" + bottom);
//        Log.e("测试代码", "测试代码-height=" + height);
        switch (getParam().getAlign()) {
            case TextMarkerParam.TextAlign.LEFT:
                anchor[0] = 0;
                anchor[1] = y.floatValue();

                break;
            case TextMarkerParam.TextAlign.CENTER:
                anchor[0] = 0.5f;
                anchor[1] = 0.5f;
                break;
            case TextMarkerParam.TextAlign.RIGHT:
                anchor[0] = 1f;
                anchor[1] = y.floatValue();
                break;

            default:
                anchor[0] = 0.5f;
                anchor[1] = 0.5f;
                break;
        }
        return anchor;

    }


    private void translateCanvas(@TextMarkerParam.TextAlign int align, Canvas canvas) {
        switch (align) {
            case TextMarkerParam.TextAlign.LEFT:
                canvas.translate(getParam().getPaddingLeftOrRight(), 0);

                break;
            case TextMarkerParam.TextAlign.CENTER:
                canvas.translate(0, 0);

                break;
            case TextMarkerParam.TextAlign.RIGHT:
                //这里为什么和left不同
                //因为StaticLayout.draw的时候是靠左画文字的，如果这个时候向左移动后 会发生移动文字显示不全的BUG
                //又因为靠着右边
                // 所以我们可以通过Bitmap.createBitmap(width+padding,height, Bitmap.Config.ARGB_8888);
                //width+padding添加画布的宽度来达到显示效果  所以是一样的
                canvas.translate(0, 0);
                break;
            default:
                break;
        }
    }


    private StaticLayout createStaticLayout(TextPaint textPaint) {


        //如果设置的文本长度小于最大文字长度。那么以设置的文本长度为length传给StaticLayout
        int length = getParam().getText().length() < getParam().getMaxTextLength() ? getParam().getText().length() : getParam().getMaxTextLength();

        float maxWidth = TextPaintUtils.getMaxTextWidth(getParam().getTextPaint(), length);
        StaticLayout staticLayout = new StaticLayout(getParam().getText(),
                textPaint,
                (int) maxWidth, Layout.Alignment.ALIGN_NORMAL,
                getParam().getTextRowSpaceMult(),
                getParam().getTextRowSpaceAdd(),
                false);
        return staticLayout;
    }


    public final static class Builder extends BaseMarkerBuilder<Builder, TextMarkerParam> {


        public Builder(@NonNull Context context, @NonNull AMap map) {
            super(context, map);
        }

        @Override
        protected TextMarkerParam returnDefaultParam() {
            TextMarkerParam textMarkerParam = new TextMarkerParam();
            return textMarkerParam;
        }

        @Override
        public <W> TextMarkerView<W> create() {
            TextMarkerView<W> textMarkerView = new TextMarkerView<>(getContext(), getMap(), getParam());
            textMarkerView.addToMap();
            return textMarkerView;
        }


        @Override
        protected Builder getChild() {
            return this;
        }


        public Builder setTextPaint(@NonNull TextPaint textPaint) {
            getParam().setTextPaint(textPaint);
            return this;
        }

        public Builder setText(String text) {
            getParam().setText(text);
            return this;
        }

        public Builder setTextPaddingLeftOrRight(int padding) {
            getParam().setPaddingLeftOrRight(padding);
            return this;
        }

        public Builder setTextRowSpaceMult(@FloatRange(from = 1f) float textSpaceMult) {
            getParam().setTextRowSpaceMult(textSpaceMult);
            return this;
        }


        public Builder setTextRowSpaceAdd(@IntRange(from = 0) int textSpaceAdd) {
            getParam().setTextRowSpaceAdd(textSpaceAdd);
            return this;
        }


        public Builder setTextMaxTextLength(int maxTextLength) {
            getParam().setMaxTextLength(maxTextLength);
            return this;
        }

        public Builder setTextOnlyTextShow(boolean onlyTextShow) {
            getParam().setOnlyTextShow(onlyTextShow);
            return this;
        }


        /**
         * 设置描边的范围
         *
         * @param width 范围
         * @return
         */
        public Builder setTextStrokeWidth(float width) {
            getParam().getStrokeTextPaint().setStrokeWidth(width);
            return this;
        }

        /**
         * 设置描边的颜色值
         *
         * @param color color
         * @return
         */
        public Builder setTextStrokeColor(int color) {
            getParam().getStrokeTextPaint().setColor(color);
            return this;
        }


        public Builder setTextAlign(@TextMarkerParam.TextAlign int algin) {
            getParam().setAlign(algin);

            return this;
        }


        public Builder setTextSize(float textSize) {
            getParam().getTextPaint().setTextSize(textSize);
            return this;
        }


        public Builder setTextColor(int color) {
            getParam().getTextPaint().setColor(color);
            return this;
        }


        public Builder setTextPointIcon(BitmapDescriptor bitmapDescriptor) {
            setIcon(bitmapDescriptor);
            return this;
        }


    }


}
