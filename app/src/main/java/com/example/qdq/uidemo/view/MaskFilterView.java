package com.example.qdq.uidemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.qdq.uidemo.R;

/**
 * Created on 2017/5/17.
 * Author: qdq
 * Description:MaskFilter:BlurMaskFilter和EmbossMaskFilter
 */

public class MaskFilterView extends View {
    private Paint paint;
    private Bitmap bitmap;
    private Bitmap alphaBitmap;//获取位图的alpha通道图
    private BlurMaskFilter maskFilter;
    public MaskFilterView(Context context) {
        this(context,null);
    }

    public MaskFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setLayerType(LAYER_TYPE_SOFTWARE,null);
        paint=new Paint();
        paint.setColor(Color.BLUE);
        maskFilter=new BlurMaskFilter(20, BlurMaskFilter.Blur.SOLID);
        paint.setMaskFilter(maskFilter);
        bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.one);
        bitmap=Bitmap.createScaledBitmap(bitmap,400,400,true);
        alphaBitmap=bitmap.extractAlpha();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制阴影
        canvas.drawBitmap(alphaBitmap,0,0,paint);
        //绘制原图
        canvas.drawBitmap(bitmap,0,0,null);
    }
}
