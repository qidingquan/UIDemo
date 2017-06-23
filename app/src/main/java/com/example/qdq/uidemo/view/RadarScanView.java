package com.example.qdq.uidemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created on 2017/5/12.
 * Author: qdq
 * Description:雷达扫描效果
 * 实现原理：原理很简单主要通过SweepGradient对圆进行扫描渐变效果，
 * 注意渐变颜色要从不透明到透明，通过不断旋转画布来实现扫描效果，注意这里画布原点在(0,0)点需要将其平移到控件的中心位置，不然会以(0,0)这个点进行旋转
 */

public class RadarScanView extends View {
    private static final int defaultWidth=200;//默认宽度
    private static final int defaultHeight=200;//默认高度
    private Paint fillPaint;
    private Paint stokePaint;
    private Paint shadePaint;

    private SweepGradient sweepGradient;//扫描渐变
    private int centerX;//圆心的x坐标
    private int centerY;//圆心的y坐标
    private int radius;//圆的半径
    private int num = 3;//圆环数
    //雷达扫描时候的起始和终止颜色
    private int mStartColor = 0x00000000;
    private int mEndColor = 0xff000000;
    private Matrix matrix;

    public RadarScanView(Context context) {
        super(context);
        init();
    }

    public RadarScanView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        //初始化填充画笔
        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setColor(Color.LTGRAY);
        //初始化描边画笔
        stokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        stokePaint.setStyle(Paint.Style.STROKE);
        stokePaint.setColor(Color.BLACK);
        //初始化扫描区域画笔
        shadePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        shadePaint.setStyle(Paint.Style.FILL);
        shadePaint.setColor(Color.BLACK);
        shadePaint.setStrokeWidth(2);
        //初始化矩阵
        matrix = new Matrix();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpec=MeasureSpec.getMode(widthMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightSpec=MeasureSpec.getMode(heightMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);

        int measureWidth=0;
        int measureHeight=0;

        if(widthSpec==MeasureSpec.EXACTLY){
            measureWidth=widthSize;
        }else {
            measureWidth=defaultWidth;
        }
        if(heightSpec==MeasureSpec.EXACTLY){
            measureHeight=heightSize;
        }else{
            measureHeight=defaultHeight;
        }
        setMeasuredDimension(measureWidth,measureHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        centerX = getWidth() / 2;
        centerY = getWidth() / 2;
        radius = getWidth() / 2;

        shadePaint.setShader(null);
        //将画布原点平移到控件的中心位置
        canvas.translate(centerX, centerY);
        //绘制底部背景圆
        canvas.drawCircle(0, 0, radius, fillPaint);
        //根据圆环数绘制圆环
        for (int i = 0; i < num; i++) {
            int h_radius = radius - i * radius / num;
            canvas.drawCircle(0, 0, h_radius, stokePaint);
        }
        canvas.drawLine(-radius, 0, radius, 0, stokePaint);
        canvas.drawLine(0, -radius, 0, radius, stokePaint);
        //设置扫面渐变
        sweepGradient = new SweepGradient(0, 0, mStartColor, mEndColor);
        shadePaint.setShader(sweepGradient);
        //绘制扫描渐变圆
        canvas.concat(matrix);
        canvas.drawCircle(0, 0, radius, shadePaint);
        //调用循环扫描
        scan();

    }
    //旋转角度
    private int rotate;

    private void scan() {
        //不断改变角度，并重绘控件
        rotate += 3;
        postInvalidateDelayed(20);
        //重置矩阵
        matrix.reset();
        //设置旋转角度
        matrix.setRotate(rotate);
    }
}
