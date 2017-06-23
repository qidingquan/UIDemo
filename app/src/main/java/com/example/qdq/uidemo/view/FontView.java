package com.example.qdq.uidemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created on 2017/5/17.
 * Author: qdq
 * Description:文字FontMetric
 */

public class FontView extends View {
    private TextPaint paint;
    private StaticLayout staticLayout;
    private String text="apSB东西ξτβбпшㄎㄊěǔぬも┰┠№＠↓apSB东西ξτβбпшㄎㄊěǔぬも┰┠№＠↓apSB东西ξτβбпшㄎㄊěǔぬも┰┠№＠↓apSB东西ξτβбпшㄎㄊěǔぬも┰┠№＠↓";
    private Paint.FontMetrics fontMetrics;
    public FontView(Context context) {
        this(context,null);
    }

    public FontView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private void init(){
        paint=new TextPaint();
        paint.setTextSize(50);
        paint.setColor(Color.BLACK);
        //自定义字体楷书
        paint.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"hwkt.ttf"));

        fontMetrics=paint.getFontMetrics();
        float top=fontMetrics.top;
        float ascent=fontMetrics.ascent;
        float descent=fontMetrics.descent;
        float bottom=fontMetrics.bottom;
        float leading=fontMetrics.leading;
        Log.e("top->",top+"");
        Log.e("ascent->",ascent+"");
        Log.e("descent->",descent+"");
        Log.e("bottom->",bottom+"");
        Log.e("leading->",leading+"");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        staticLayout=new StaticLayout(text,paint,canvas.getWidth(), Layout.Alignment.ALIGN_NORMAL,2,1,true);
        staticLayout.draw(canvas);
        canvas.restore();
//        canvas.drawText(text,0,Math.abs(fontMetrics.top),paint);
//        canvas.drawLine(0,fontMetrics.bottom-fontMetrics.top/2,paint.measureText(text),fontMetrics.bottom-fontMetrics.top/2,paint);

    }
}
