package xiaoheihei.example.com.mylinearlayouttest;

import android.content.Context;
import android.icu.util.Measure;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xhh on 2017/10/13.
 */

public class MyFlowView extends ViewGroup {
    private int lineWidth = 0;//记录每一行的宽度；
    private int lineHeight = 0;//记录每一行的高度；
    int height = 0;//记录flowlayout的高度；
    int width = 0; //记录flowlayout的宽度；


    public MyFlowView(Context context) {
        super(context);
    }

    public MyFlowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeigh = MeasureSpec.getSize(heightMeasureSpec);
        int measurWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            int childHeight = child.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
            if (lineWidth + childWidth > measureWidth) {
                width = Math.max(lineWidth, childWidth);
                height += lineHeight;
                lineWidth = childWidth;
                lineHeight = childHeight;
            } else {
                lineHeight = Math.max(lineHeight, childHeight);
                lineWidth += childWidth;
            }
            if (i == count - 1) {
                height += lineHeight;
                width = Math.max(width, lineWidth);
            }
        }
        setMeasuredDimension(measurWidthMode == MeasureSpec.EXACTLY ? measureWidth : width, (measureHeightMode == MeasureSpec.EXACTLY) ?
                measureHeigh
                : height);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        int count = getChildCount();
        int lineWidth = 0;
        int lineHeight = 0;
        int top = 0;
        int left = 0;
        for (int j=0;j<count;j++){
            View child = getChildAt(j);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth= child.getMeasuredWidth()+lp.leftMargin+lp.rightMargin;
            int childHeight = child.getMeasuredHeight()+lp.topMargin+lp.bottomMargin;
            if(childWidth+lineWidth>getMeasuredWidth()){
               top+=lineHeight;
                left = 0;
                lineHeight = childHeight;
                lineWidth = childWidth;
            }else{
                lineHeight = Math.max(lineHeight,childHeight);
                lineWidth +=childWidth;
            }
            //计算childView的left,top,right,bottom
            int lc = left + lp.leftMargin;
            int tc = top + lp.topMargin;
            int rc =lc + child.getMeasuredWidth();
            int bc = tc + child.getMeasuredHeight();
            child.layout(lc, tc, rc, bc);
            //将left置为下一子控件的起始点
            left+=childWidth;
        }
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
