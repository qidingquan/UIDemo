package com.example.qdq.uidemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.example.qdq.uidemo.R;

/**
 * Created on 2017/5/15.
 * Author: qdq
 * Description:Xfermode的使用
 */

public class XfermodeView extends View {
    private Paint paint;
    private PorterDuffXfermode xfermode;
    private Bitmap srcBitmap;
    private Bitmap dscBitmap;

    public XfermodeView(Context context) {
        this(context, null);
    }

    public XfermodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        if (Build.VERSION.SDK_INT >= 11) {//关闭硬件加速
            setLayerType(LAYER_TYPE_HARDWARE, null);
        }
        srcBitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.one)).getBitmap();
        dscBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.shade);

        paint = new Paint();
        xfermode = new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        srcBitmap=Bitmap.createScaledBitmap(srcBitmap,getWidth(),getHeight(),false);
        dscBitmap=Bitmap.createScaledBitmap(dscBitmap,getWidth(),getHeight(),false);
        //画源图像
        canvas.drawBitmap(srcBitmap, 0, 0, paint);
        //画目标图像
        paint.setXfermode(xfermode);
        canvas.drawBitmap(dscBitmap,0,0, paint);

    }
}
