package com.example.qdq.uidemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.example.qdq.uidemo.R;
import com.example.qdq.uidemo.util.ScreenUtil;

/**
 * Created on 2017/5/11.
 * Author: qdq
 * Description:倒影图片
 */

public class InvertInflectView extends View {
    private Bitmap bitmap;
    private Bitmap scaleBitmap;
    private Paint paint;
    private LinearGradient linearGradient;
    private PorterDuffXfermode mXfermode;
    private int left;
    private int top;
    public InvertInflectView(Context context) {
        super(context);
        init();
    }

    public InvertInflectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private void init(){
        //获取原图
        bitmap=((BitmapDrawable)getResources().getDrawable(R.mipmap.one)).getBitmap();
        //获取倒影图
        Matrix matrix=new Matrix();
        matrix.setScale(1f,-1f);
        scaleBitmap=Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);

        left= ScreenUtil.getScreenWidth(getContext())/2-bitmap.getWidth()/2;
        top=ScreenUtil.getScreenHeight(getContext())/2-bitmap.getHeight()/2;
        paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        linearGradient=new LinearGradient(left,top+bitmap.getHeight(),left+bitmap.getWidth(),
                top+bitmap.getHeight()+scaleBitmap.getHeight(),
                new int[]{ 0xAA000000, Color.TRANSPARENT},null, Shader.TileMode.CLAMP);
        paint.setShader(linearGradient);
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(bitmap,left,top, null);
        //倒影图片那一层
        int sc = canvas.saveLayer(left,top+ bitmap.getHeight(),left + scaleBitmap.getWidth(),top + bitmap.getHeight() * 2, null, Canvas.ALL_SAVE_FLAG);
        //绘制倒影图
        canvas.drawBitmap(scaleBitmap, left, top+ bitmap.getHeight(), null);

        paint.setXfermode(mXfermode);
        //绘制矩形
        canvas.drawRect(left,top + bitmap.getHeight(), left+ scaleBitmap.getWidth(), top+ bitmap.getHeight() * 2, paint);

        paint.setXfermode(null);

        canvas.restoreToCount(sc);
    }
}
