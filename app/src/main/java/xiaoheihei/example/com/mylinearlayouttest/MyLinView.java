package xiaoheihei.example.com.mylinearlayouttest;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by xhh on 2017/10/13.
 */

public class MyLinView extends ViewGroup {
    public MyLinView(Context context) {
        super(context);
    }

    public MyLinView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    //onMeasure()的作用就是根据container内部的子控件计算自己的宽和高，最后通过setMeasuredDimension（int width,int height设置进去）；
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = 0;
        int width = 0;
        int count = getChildCount();//获取自控件的个数；
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);//获取viewgroup内部的子View
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

            int childHeight = child.getMeasuredHeight()+lp.topMargin+lp.bottomMargin;
            int childWidth = child.getMeasuredWidth()+lp.leftMargin+lp.rightMargin;
            height += childHeight;
            width = Math.max(childWidth, width);
        }
        setMeasuredDimension((measureWidthMode == MeasureSpec.EXACTLY) ? measureWidth : width, (measureHeightMode == MeasureSpec.EXACTLY) ? measureHeight : height);
    }

    //实现的是自定义view在viewgroup中的摆放的方式
    /*onlayout内部的参数表示的是相对于父窗口做  左 上 右 下 的距离*/
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int top = 0;
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
            int childHeight = child.getMeasuredHeight()+layoutParams.bottomMargin+layoutParams.topMargin;
            int childWidth = child.getMeasuredWidth()+layoutParams.rightMargin+layoutParams.leftMargin;
            child.layout(0,top,childWidth,top+childHeight);
            top += childHeight;
        }
    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        return new MarginLayoutParams(lp);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }

//    @Override
//    protected LayoutParams generateDefaultLayoutParams() {
//        return new MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//    }


}
