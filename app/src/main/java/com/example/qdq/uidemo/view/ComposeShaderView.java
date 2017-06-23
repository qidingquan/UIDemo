package com.example.qdq.uidemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.qdq.uidemo.R;

/**
 * Created on 2017/5/31.
 * Author: qdq
 * Description:组合动画
 */

public class ComposeShaderView extends View {
    private Paint paint;
    private Bitmap heartBitmap;
    private LinearGradient linearGradient;
    private BitmapShader bitmapShader;
    private ComposeShader composeShader;
    private int[] colors={
            Color.parseColor("#ff0000"),
            Color.parseColor("#990000"),
            Color.parseColor("#330000")
    };
    public ComposeShaderView(Context context) {
        super(context);
        initPaint();
    }

    public ComposeShaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }
    private void initPaint(){
        paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        heartBitmap= BitmapFactory.decodeResource(getResources(), R.drawable.heart);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        linearGradient=new LinearGradient(0,0,heartBitmap.getWidth(),heartBitmap.getHeight(),colors,null, Shader.TileMode.CLAMP);
        bitmapShader=new BitmapShader(heartBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        composeShader=new ComposeShader(linearGradient,bitmapShader, PorterDuff.Mode.MULTIPLY);
        paint.setShader(composeShader);
        canvas.drawRect(0,0,heartBitmap.getWidth(),heartBitmap.getHeight(),paint);
    }
}
