package com.example.qdq.uidemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.example.qdq.uidemo.R;

/**
 * Created on 2017/5/11.
 * Author: qdq
 * Description:自定义圆形头像
 */

public class CircleImageView extends View {
    private Paint paint;
    private Bitmap bitmap;
    private boolean isCircle;//是不是圆形
    private float round;//圆角半径
    public CircleImageView(Context context) {
        super(context);
        init();
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.CircleImageView);
        isCircle=typedArray.getBoolean(R.styleable.CircleImageView_isCircle,true);
        round=typedArray.getDimension(R.styleable.CircleImageView_round,10);
        typedArray.recycle();
        init();
    }
    private void init(){
        paint=new Paint();
        paint.setAntiAlias(true);//抗锯齿，图片更平滑
        bitmap=((BitmapDrawable)getResources().getDrawable(R.mipmap.one)).getBitmap();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //缩放到控件大小
        bitmap=Bitmap.createScaledBitmap(bitmap,getMeasuredWidth(),getMeasuredHeight(),false);
        //创建渲染
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(bitmapShader);
        if(isCircle){
            //绘制圆形
            canvas.drawCircle(getWidth()/2,getHeight()/2,getWidth()/2,paint);
        /*    //通过ShapeDrawable实现
            ShapeDrawable shapeDrawable=new ShapeDrawable(new OvalShape());
            shapeDrawable.getPaint().setShader(bitmapShader);
            //设置drawable的大小
            shapeDrawable.setBounds(0,0,getWidth(),getHeight());
            shapeDrawable.draw(canvas);*/
        }else{
            RectF rectF=new RectF(0,0,getWidth(),getHeight());
            canvas.drawRoundRect(rectF,round,round,paint);
        }
    }
}
