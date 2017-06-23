package com.example.qdq.uidemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.example.qdq.uidemo.R;

/**
 * Created on 2017/5/26.
 * Author: qdq
 * Description:自定义瀑布流布局
 * 实现效果：每个子项的宽度相同高度可能不同的布局
 * 实现思路：
 * 1.由于项宽度相同，则高度需要根据图片比例进行计算得出（本例中计算比例存在问题，当图片宽高大于屏幕宽高时，通过getMeasuredWidth（）获取的宽度为屏幕宽度并非图片真实宽度，高度一样的道理，导致比例计算不正确）
 * 2.用一个数组来存储每一列的总高度，将子控件添加到总高度最小的那一列的后面
 * 3.通过onMeasure()方法计算出控件的高度，控件的高度为所有列高中最大的值
 * 并自定义LayoutParams类，将子控件的left,top,right,bottom封装到layoutParams参数中，以便在onLayout方法中获取参数对子控件进行布局,最后通过setMeasuredDimension()方法来设置控件测量大小
 * 4.在onLayout()方法中对子控件进行布局
 */

public class WaterfallFlowLayout extends ViewGroup {
    private int column = 4;//显示的列数
    private int horizontalSpace=10;//列间距
    private int verticalSpace=10;//行间距
    private int childWidth;//子控件的宽度
    private int[] columnHeight=new int[column];//存储每一列的高度
    public WaterfallFlowLayout(Context context) {
        this(context,null);
    }

    public WaterfallFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WaterfallFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.WaterfallFlowLayout);
        column=typedArray.getInteger(R.styleable.WaterfallFlowLayout_column,3);
        horizontalSpace=typedArray.getDimensionPixelSize(R.styleable.WaterfallFlowLayout_horizontalSpace,0);
        verticalSpace=typedArray.getDimensionPixelSize(R.styleable.WaterfallFlowLayout_verticalSpace,0);
        typedArray.recycle();
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MyLayoutParams(getContext(),attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //测量之后的宽度高度
        int newWidth = 0;
        int newHeight = 0;

        if (widthMode == MeasureSpec.EXACTLY) {
            newWidth = widthSize;
            newHeight = heightSize;
        } else {
            //根据子控件的高度宽度测量出自身的宽高
            measureChildren(widthMeasureSpec,heightMeasureSpec);
            //计算子控件的宽度
            childWidth = (widthSize-(column-1)*horizontalSpace) / column;
            int childNum = getChildCount();
            if (childNum < column) {
                newWidth = childWidth * childNum;
            } else {
                newWidth = widthSize;
            }
            for (int i = 0; i < childNum; i++) {
                View childView = getChildAt(i);
                //根据子控件的宽度计算出子控件应该显示的高度（需要等比例缩放）
                int width=childView.getMeasuredWidth();
                int height=childView.getMeasuredHeight();
                float scale= (float) (height*1.0 / width);
                int childHeight = (int) (childWidth *scale);
                //获取所有列当中高度最小的位置，放置子控件
                int column=getMinHeight();
                MyLayoutParams params= (MyLayoutParams) childView.getLayoutParams();
                params.left=column*(childWidth+horizontalSpace);
                params.top=columnHeight[column]+verticalSpace;
                params.right=params.left+childWidth;
                params.bottom=params.top+childHeight;
                //对添加的列的高度进行累加
                columnHeight[column]+=childHeight+verticalSpace;
            }
            newHeight=getMaxHeight();//获取整个列中最大值
        }
        setMeasuredDimension(newWidth, newHeight);
    }
    //获取所有行中列高度最小的位置
    private int getMinHeight(){
        int minColumn=0;
        for(int i=1;i<columnHeight.length;i++){
            if(columnHeight[i]<columnHeight[minColumn]){
                minColumn=i;
            }
        }
        return minColumn;
    }
    //获取最大的高度
    private int getMaxHeight(){
        int maxHeight=columnHeight[0];
        for(int i=1;i<columnHeight.length;i++){
            if(columnHeight[i]>maxHeight){
                maxHeight=columnHeight[i];
            }
        }
        return maxHeight;
    }
    private void clearTop() {
        for (int i = 0; i < columnHeight.length; i++) {
            columnHeight[i] = 0;
        }
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //对子控件进行布局
        int childNum=getChildCount();
        clearTop();
        for(int i=0;i<childNum;i++){
            View childView=getChildAt(i);
            MyLayoutParams params= (MyLayoutParams) childView.getLayoutParams();
            childView.layout(params.left,params.top,params.right,params.bottom);
        }
    }
    public static class MyLayoutParams extends ViewGroup.LayoutParams{
        public int left;
        public int top;
        public int right ;
        public int bottom ;
        public MyLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }
    }
}
