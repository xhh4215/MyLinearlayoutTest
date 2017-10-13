package xiaoheihei.example.com.mylinearlayouttest;

import android.content.Context;
import android.icu.util.Measure;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xhh on 2017/10/13.
 */

public class WaterfallLayout extends ViewGroup {
    //定义显示图片的列数
    private int columns = 3;
    /*指定水平和垂直的间隔距离*/
    private int hSpace;
    private int vSpace;
    private int childWidth = 0;//表示每个图片的宽度
    private int top[];//用来保存当前的每列的高度的数组

    public WaterfallLayout(Context context) {
        super(context);

    }

    public WaterfallLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaterfallLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //初始化保存高度的数组
        top = new int[columns];
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取父控件测试的宽度模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        //获取符控件测试的宽度大小
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        //测试所有的父控件的宽度和高度
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        //计算显示的每个图片的宽度
        childWidth = (widthSize - (columns - 1) * hSpace) / columns;
        int wrapWidth;
        //获取自控件的个数
        int childcount = getChildCount();
        //在添加的图片没到3个的时候
        if (childcount < columns) {
            //获取控件的总共的宽度
            wrapWidth = childcount * childWidth + (childcount - 1) * hSpace;
        } else {
            //如果图片填满了就设置宽度为符控件测试的宽度
            wrapWidth = widthSize;
        }
        clearTop();
        for (int i = 0; i < childcount; i++) {
            View child = this.getChildAt(i);
            //获取自控件的高度
            int childHeight = child.getMeasuredHeight() * childWidth / child.getMeasuredWidth();
            //返回最短的列在数组中的位置
            int minColum = getMinHeightColum();
            //计算图片的高度
            top[minColum] += vSpace + childHeight;
        }
        int wrapHeight;
        //获取高度最高的列
        wrapHeight = getMaxHeight();
        setMeasuredDimension(widthMode == MeasureSpec.AT_MOST ? wrapWidth : widthSize, wrapHeight);
    }

    private void clearTop() {
        for (int i = 0; i < columns; i++) {
            top[i] = 0;
        }
    }

    @Override
    protected void onLayout(boolean change, int l, int t, int r, int b) {
        int childCount = getChildCount();
        clearTop();
        for (int i = 0; i < childCount; i++) {
            View child = this.getChildAt(i);
            int childHeight = child.getMeasuredHeight() * childWidth / child.getMeasuredWidth();
            int minColum = getMinHeightColum();
            int tleft = minColum * (childWidth + hSpace);
            int ttop = top[minColum];
            int tright = tleft + childWidth;
            int tbottom = ttop + childHeight;
            top[minColum] += vSpace + childHeight;
            child.layout(tleft, ttop, tright, tbottom);
        }
    }
    //返回图片高度最低的图片在数组中的位置
    public int getMinHeightColum() {
        int minColum = 0;
        for (int i = 0; i < columns; i++) {
            if (top[i] < top[minColum]) {
                minColum = i;
            }
        }
        return minColum;
    }
   //获取存储图片高度的数组中的最高的高度
    public int getMaxHeight() {
        int maxHeight = 0;
        for (int i = 0; i < columns; i++) {
            if (top[i] > maxHeight) {
                maxHeight = top[i];
            }
        }
        return maxHeight;
    }
    public interface OnItemClickListener{
        void onItemClick(View v, int index);
    }
    public void setOnItemClickItemListener(final OnItemClickListener listener){
        for(int i = 0;i<getChildCount();i++){
            final int index = i;
            View view  = getChildAt(i);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(view,index);
                }
            });
        }
    }
}
