package com.example.qdq.uidemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.qdq.uidemo.R;

/**
 * Created on 2017/5/18.
 * Author: qdq
 * Description:shader相关知识
 */

public class ShaderView extends View{
    private Paint paint;
    private BitmapShader bitmapShader;
    private Bitmap bitmap;
    private int x=300;
    private int y=300;
    private Matrix matrix;
    public ShaderView(Context context) {
        this(context,null);
    }

    public ShaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private void init(){
        paint=new Paint();
        bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.one);
        bitmapShader=new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(bitmapShader);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(x,y,300,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x= (int) event.getX();
        y= (int) event.getY();
        if(x>300&&y>300){
            invalidate();
        }
        return true;
    }
}
