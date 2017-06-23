package com.example.qdq.uidemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created on 2017/5/12.
 * Author: qdq
 * Description:水波纹效果
 */

public class WaterRippleView extends View implements View.OnClickListener{
    private Paint paint;
    private RadialGradient radialGradient;
    private int centerX;//圆心的x坐标
    private int centerY;//圆心的y坐标
    private int radius;//圆的半径
    private int gradientSize;//渐变大小
    public WaterRippleView(Context context) {
        super(context);
        init();
    }

    public WaterRippleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private void init(){
        setOnClickListener(this);
        paint=new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        centerX=getWidth()/2;
        centerY=getWidth()/2;
        radius=getWidth()/2;
        gradientSize+=20;
        radialGradient=new RadialGradient(centerX,centerY,gradientSize,new int[]{Color.LTGRAY,Color.GRAY,Color.DKGRAY},null, Shader.TileMode.CLAMP);
        paint.setShader(radialGradient);
        canvas.drawCircle(centerX,centerY,radius,paint);
        if(gradientSize<radius){
            postInvalidateDelayed(20);
        }
    }

    @Override
    public void onClick(View v) {
        gradientSize=0;
        invalidate();
    }
}
