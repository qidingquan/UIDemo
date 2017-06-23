package com.example.qdq.uidemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.qdq.uidemo.R;

/**
 * Created on 2017/5/27.
 * Author: qdq
 * Description:自定义圆形进度条
 * 1.绘制背景圆形
 * 2.绘制中间文字进度
 *      需要计算文字绘制的起点，保证文字在控件的中心，这里用到了measureText()方法对文字宽度进行测量，
 *      文字绘制是从基线开始绘制，所以要通过FontMetrics来计算文字文字y坐标上的位置才能放在中间显示（具体计算参考代码）
 * 3.绘制进度圆弧
 *      圆弧起点角度固定，通过显示进度值动态改变圆弧的结束角度来绘制圆弧
 */

public class CircleProgressBar extends View {

    private Paint paintOne;//绘制背景
    private Paint paintTwo;//绘制文字
    private Paint paintThree;//绘制进度

    private int progressWidth=20;//进度宽度
    private int backgroundColor=Color.LTGRAY;//背景色
    private int progressColor=Color.DKGRAY;//进度色
    private int progressTextColor=Color.BLACK;//进度文字颜色
    private int progressTextSize=50;//进度文字大小
    private int progress;//进度值
    private int startDegree;//进度开始显示角度

    private int radius=50;//半径
    private int centerX;//圆心x坐标
    private int centerY;//圆心y坐标
    private int endDegree;//结束的角度值
    public CircleProgressBar(Context context) {
        this(context,null);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取xml文件中中自定义属性值
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.CircleProgressBar);
        progressWidth=typedArray.getDimensionPixelSize(R.styleable.CircleProgressBar_progressWidth,20);
        backgroundColor=typedArray.getColor(R.styleable.CircleProgressBar_backgroundColor,Color.LTGRAY);
        progressColor=typedArray.getColor(R.styleable.CircleProgressBar_progressColor,Color.DKGRAY);
        progressTextColor=typedArray.getColor(R.styleable.CircleProgressBar_progressTextColor,Color.BLACK);
        progressTextSize=typedArray.getDimensionPixelSize(R.styleable.CircleProgressBar_progressTextSize,50);
        progress=typedArray.getInteger(R.styleable.CircleProgressBar_progress,0);
        startDegree=typedArray.getInteger(R.styleable.CircleProgressBar_startDegree,0);
        typedArray.recycle();
        //初始化画笔
        initPaint();
    }
    private void initPaint(){
        //绘制背景
        paintOne=new Paint();
        paintOne.setAntiAlias(true);//抗锯齿使图形更圆滑
        paintOne.setStrokeWidth(progressWidth);//画笔宽度
        paintOne.setStyle(Paint.Style.STROKE);//设置画笔描边
        paintOne.setColor(backgroundColor);//设置颜色
        //绘制文字进度
        paintTwo=new Paint();
        paintTwo.setTextSize(progressTextSize);//设置画笔大小
        paintTwo.setStyle(Paint.Style.FILL);//设置画笔填充
        paintTwo.setColor(progressTextColor);
        //绘制进度
        paintThree=new Paint();
        paintThree.setAntiAlias(true);
        paintThree.setStrokeWidth(progressWidth);
        paintThree.setStyle(Paint.Style.STROKE);
        paintThree.setColor(progressColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获取进度条圆心坐标
        centerX=getWidth()/2;
        centerY=getHeight()/2;
        //获取进度条半径，控件宽度/2减去进度条宽度
        radius=centerX-progressWidth;
        //绘制背景圆
        canvas.drawCircle(centerX,centerY,radius,paintOne);
        //绘制中间进度文字
        String text=progress+"%";
        Paint.FontMetrics metrics=paintTwo.getFontMetrics();
        //获取绘制文字的起点x,y坐标，保证文字在控件的中心显示
        float x=centerX-paintTwo.measureText(text)/2;
        float y=centerY+(metrics.bottom-metrics.top)/2-metrics.bottom;
        canvas.drawText(text,x,y,paintTwo);
        //绘制进度圆弧
        RectF rectF=new RectF(centerX-radius,centerY-radius,centerX+radius,centerY+radius);
        canvas.drawArc(rectF,startDegree,endDegree,false,paintThree);
    }

    /**
     * 更新进度条进度值，这里总值为100
     * @param progress 需要转换成以100为分母的进度
     */
    public void setProgress(int progress){
        this.progress=progress;
        if(progress<=100){
            //转换角度用于绘制圆弧
            endDegree=progress*360/100;
            //可在子线程重绘
            postInvalidate();
        }
    }
}
