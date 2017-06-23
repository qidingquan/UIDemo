package com.example.qdq.uidemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created on 2017/5/11.
 * Author: qdq
 * Description:霓虹灯效果
 */

public class NeonLightView extends TextView {
    private int DELTAX = 20;
    private TextPaint paint;
    private LinearGradient linearGradient;
    private int move;//移动的距离
    private float textWidth;//文字宽度
    public NeonLightView(Context context) {
        super(context);
    }

    public NeonLightView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        String text= getText().toString();
        paint=getPaint();
        textWidth=getPaint().measureText(text);
        linearGradient=new LinearGradient(0,0,200,0,new int[]{0x22ffffff,0xffffffff,0x22ffffff},null, Shader.TileMode.CLAMP);
        paint.setShader(linearGradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        move+=DELTAX;
        float textWidth = getPaint().measureText(getText().toString());
        if(move > textWidth + 1 || move < 1){
            DELTAX = - DELTAX;
        }
        Matrix matrix=new Matrix();
        matrix.setTranslate(move,0);
        linearGradient.setLocalMatrix(matrix);
        postInvalidateDelayed(100);
    }
}
