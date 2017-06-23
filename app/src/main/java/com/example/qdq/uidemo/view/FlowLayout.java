package com.example.qdq.uidemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2017/5/25.
 * Author: qdq
 * Description:自定义流式布局
 * 1.继承自ViewGroup
 * 2.重写onMeasure
 *      根据测量模式测量出控件的宽度和高度（其中包含了子控件的margin值），通过setMeasuredDimension(newWidthSize, newHeightSize);设置控件的宽高
 * 3.实现onLayout
 *      对它的每一个子View进行布局设置，规则为从左到右依次排列，该行放不下子控件则将子控件放到新的一行
 */

public class FlowLayout extends ViewGroup {
    //存储该控件内的所有的子控件
    private List<List<View>> viewList = new ArrayList<>();
    //存储每一行中最大的高度作为该行的高度
    private List<Integer> mLineHeights = new ArrayList<>();
    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取测量模式和大小
        int widthSpec = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpec = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int newWidthSize = 0;
        int newHeightSize = 0;
        int currentWidth=0;
        int currentHeight=0;
        //根据不同模式设置大小
        if (widthSpec == MeasureSpec.EXACTLY && heightSpec == MeasureSpec.EXACTLY) {
            newWidthSize = widthSize;
            newHeightSize = heightSize;
        } else {
            //通过计算出每个孩子的宽高相加后得到控件的宽高
            int childNum = getChildCount();
            List<View> lineViewList = new ArrayList<>();
            for (int i = 0; i < childNum; i++) {
                //获得子控件
                View childView = getChildAt(i);
                //对子控件进行测量
                measureChild(childView, widthMeasureSpec, heightMeasureSpec);
                MarginLayoutParams params = (MarginLayoutParams) childView.getLayoutParams();
                //子控件的宽度=自身宽度+左边距+右边距 高度=自身高度+上边距+下边距
                int childWidth = childView.getMeasuredWidth() + params.leftMargin + params.rightMargin;
                int childHeight = childView.getMeasuredHeight() + params.topMargin + params.bottomMargin;
                //判断子控件所处位置是否超出父控件宽度
                if (currentWidth + childWidth < widthSize) {
                    //当前行中最大的宽度
                    currentWidth += childWidth;
                    //获得当前行中最大的高度
                    currentHeight=Math.max(currentHeight, childHeight);
                    //添加同一行子控件
                    lineViewList.add(childView);
                } else {
                    //存储上一行的高度
                    mLineHeights.add(currentHeight);
                    //所有行中最大的宽度
                    newWidthSize=Math.max(currentWidth,newWidthSize);
                    //计算总共的高度
                    newHeightSize+=currentHeight+childHeight;
                    //计算新一行的宽度
                    currentWidth=childWidth;
                    currentHeight=childHeight;
                    //存储上一行的所有子控件
                    viewList.add(lineViewList);
                    //重新创建存储新行子控件的集合
                    lineViewList = new ArrayList<>();
                    lineViewList.add(childView);
                }
                if(i==childNum-1){//最后一行或者只有一行
                    //存储一行的高度
                    mLineHeights.add(currentHeight);
                    //所有行中最大的宽度
                    newWidthSize = Math.max(newWidthSize,currentWidth);
                    //计算总共的高度
                    newHeightSize+=currentHeight;
                    //存储上一行的所有子控件
                    viewList.add(lineViewList);
                }
            }
        }
        setMeasuredDimension(newWidthSize, newHeightSize);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left,top,right,bottom;
        int curTop = 0;//表示子控件的top值
        int curLeft = 0;//表示子控件的left值
        //遍历获取子控件并对其进行布局
        for(int i = 0 ; i < viewList.size() ; i++) {
            //获取第i行中的所有子控件
            List<View> viewList = this.viewList.get(i);
            for(int j = 0; j < viewList.size(); j++){
                //获取第i行中第j个子控件
                View childView = viewList.get(j);
                //获取控件的布局属性
                MarginLayoutParams layoutParams = (MarginLayoutParams) childView.getLayoutParams();
                //计算子控件的left,right,top,bottom值
                left = curLeft + layoutParams.leftMargin;
                top = curTop + layoutParams.topMargin;
                right = left + childView.getMeasuredWidth();
                bottom = top + childView.getMeasuredHeight();
                //对子控件进行布局操作
                childView.layout(left,top,right,bottom);
                //同一行中下一个子控件的left值为=当前控件left值+当前控件的宽度+左边距+右边距
                curLeft += childView.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
                //设置点击事件
                childView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(),"你好啊",Toast.LENGTH_SHORT).show();
                    }
                });
            }
            //下一行的left初始化为0
            curLeft = 0;
            //下一行的top值为上一行的高度值
            curTop += mLineHeights.get(i);
        }
        //将集合清空
        viewList.clear();
        mLineHeights.clear();
    }
}
