package com.example.qdq.uidemo.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created on 2017/5/31.
 * Author: qdq
 * Description:环形渐变
 */

public class RadialGradientView extends View {
    private Paint paint;
    private RadialGradient radialGradient;
    private int centerX;
    private int centerY;
    private int currentRadius = 100;
    private int radius = 100;
    private ValueAnimator valueAnimator;

    public RadialGradientView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(200);
        valueAnimator.setIntValues(0, 1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        centerX = getWidth() / 2;
        centerY = getWidth() / 2;
        Log.e("currentRadius", currentRadius + "");
        radialGradient = new RadialGradient(centerX, centerY, currentRadius, Color.WHITE, Color.RED, Shader.TileMode.CLAMP);
        paint.setShader(radialGradient);
        canvas.drawCircle(centerX, centerY, currentRadius, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                start();
                break;

        }
        return super.onTouchEvent(event);
    }

    private void start() {
        if (!valueAnimator.isRunning()) {
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float faction = animation.getAnimatedFraction();
                    Log.e("faction", faction + "");
                    currentRadius = (int) (radius * faction);
                    if (currentRadius > 0) {
                        Log.e("postInvalidate", "postInvalidate");
                        postInvalidate();
                    }
                }
            });
            valueAnimator.start();
        }
    }
}
