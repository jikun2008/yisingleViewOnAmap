package com.yisingle.study.map.one;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.yisingle.amapview.lib.utils.TextPaintUtils;

/**
 * @author jikun
 * Created by jikun on 2018/5/8.
 */
public class MyTextView extends View {

    private Paint paint = new Paint();

    public MyTextView(Context context) {
        super(context);
        paint.setColor(Color.parseColor("#7EC0EE"));
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.parseColor("#7EC0EE"));
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint.setColor(Color.parseColor("#7EC0EE"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawText(canvas);
    }

    private void drawText(Canvas canvas) {
        TextPaint textPaint = new TextPaint();
        textPaint.setAntiAlias(true);//设置抗锯齿

        //设置字体加粗
        textPaint.setFakeBoldText(true);
        //设置字体颜色
        textPaint.setColor(Color.parseColor("#5E5E5E"));
        textPaint.setTextSize(120);

        canvas.drawBitmap(getTextBitMap(textPaint), 0, 0, textPaint);

//        StaticLayout staticLayout = new StaticLayout("测试代码测试代码", textPaint, 300, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, true);
//        canvas.drawRect(0, 0, staticLayout.getWidth(), staticLayout.getHeight(), paint);
//        staticLayout.draw(canvas);
    }


    private Bitmap getTextBitMap(TextPaint textPaint) {
        //获取最大MaxWidth
        float maxWidth = TextPaintUtils.getMaxTextWidth(textPaint, 1);
        StaticLayout staticLayout = new StaticLayout("测试代码测试代码", textPaint, (int) maxWidth, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, true);
        Bitmap bitmap = Bitmap.createBitmap(staticLayout.getWidth(),
                staticLayout.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.save();
        staticLayout.draw(canvas);
        canvas.restore();
        return bitmap;
    }


    /**
     * 最大文字长度
     *
     * @param maxTextLength
     * @return
     */
    public float getMaxTextWidth(@NonNull Paint paint, int maxTextLength) {
        StringBuilder measureText = new StringBuilder();
        for (int i = 0; i < maxTextLength; i++) {
            measureText.append("徐");
        }
        float width = paint.measureText(measureText.toString());
        return width;
    }


}

