package com.example.qdq.uidemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.example.qdq.uidemo.R;
import com.example.qdq.uidemo.model.TaiWanPath;
import com.example.qdq.uidemo.util.PathParser;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2017/6/1.
 * Author: qdq
 * Description:台湾地图
 */

public class MapView extends View {
    private List<TaiWanPath> pathList;
    private Paint paint;
    private Region region;
    private GestureDetectorCompat gestureDetectorCompat;

    public MapView(Context context) {
        this(context, null);
    }

    public MapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
        parseXml();
    }

    private void init(Context context) {
        //初始化画笔
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //设置手势
        gestureDetectorCompat = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                //判断点击的位置是否在地图的某个区域
                for (TaiWanPath taiWanPath : pathList) {
                    if (isContain(taiWanPath.path, (int) e.getX(), (int) e.getY())) {
                        taiWanPath.isSelect = true;
                    } else {
                        taiWanPath.isSelect = false;
                    }
                }
                //重绘
                postInvalidate();
                return true;
            }
        });
    }

    /**
     * 判断该路径中是否包含点击位置
     *
     * @param path 路径
     * @param x    点击位置的x坐标
     * @param y    点击位置的y坐标
     * @return 是否包含
     */
    private boolean isContain(Path path, int x, int y) {
        region = new Region();
        RectF rectF = new RectF();
        path.computeBounds(rectF, true);
        region.setPath(path, new Region((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom));
        return region.contains(x, y);
    }

    /**
     * 使用pull解析vector数据
     */
    private void parseXml() {
        try {
            XmlPullParser xmlPullParser = Xml.newPullParser();
            InputStream inputStream = getContext().getResources().openRawResource(R.raw.taiwan);
            xmlPullParser.setInput(inputStream, "utf-8");
            int event = xmlPullParser.getEventType();
            //如果没有到文档结束继续解析
            while (event != XmlPullParser.END_DOCUMENT) {
                switch (event) {
                    case XmlPullParser.START_DOCUMENT://文档开始
                        pathList = new ArrayList<>();
                        break;
                    case XmlPullParser.START_TAG://开始标签
                        if (xmlPullParser.getName().equals("path")) {
                            TaiWanPath taiWanPath = new TaiWanPath();
                            if (xmlPullParser.getAttributeName(0).equals("fillColor")) {
                                taiWanPath.fillColor = xmlPullParser.getAttributeValue(0);
                            }
                            if (xmlPullParser.getAttributeName(1).equals("strokeColor")) {
                                taiWanPath.strokeColor = xmlPullParser.getAttributeValue(1);
                            }
                            if (xmlPullParser.getAttributeName(2).equals("strokeWidth")) {
                                taiWanPath.strokeWidth = xmlPullParser.getAttributeValue(2);
                            }
                            if (xmlPullParser.getAttributeName(3).equals("pathData")) {
                                taiWanPath.pathData = xmlPullParser.getAttributeValue(3);
                                taiWanPath.path = PathParser.createPathFromPathData(xmlPullParser.getAttributeValue(3));
                            }
                            pathList.add(taiWanPath);
                        }
                        break;
                    case XmlPullParser.END_TAG://结束标签
                        break;
                }
                event = xmlPullParser.next();//下一个事件
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制地图
        for (int i = 0; i < pathList.size(); i++) {
            TaiWanPath taiWanPath = pathList.get(i);
            if (taiWanPath.isSelect) {//选中的区域红色表示
                paint.setColor(Color.RED);
            } else {//未选中的区域用原色
                paint.setColor(Color.parseColor(taiWanPath.fillColor));
            }
            paint.setStrokeWidth(Float.valueOf(taiWanPath.strokeWidth));
            canvas.drawPath(pathList.get(i).path, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetectorCompat.onTouchEvent(event);
    }
}
