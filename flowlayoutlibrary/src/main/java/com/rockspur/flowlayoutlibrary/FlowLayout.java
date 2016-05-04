package com.rockspur.flowlayoutlibrary;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by nelon on 2016/5/4.
 */
public class FlowLayout extends ViewGroup {
    private static final String TAG = FlowLayout.class.getSimpleName();
    public static final float LEFT = 0;
    public static final float CENTER = 0.5f;
    public static final float RIGHT = 1;

    /**
     * 存储每行的剩余width
     */
    private ArrayList<Integer> mDifWidths;
    /**
     * 行间距
     */
    private int rowSpacing;
    /**
     * 列间距
     */
    private int columnSpacing;
    /**
     * 对齐方式 : LEFT,CENTER,RIGHT,左对齐/居中/右对齐
     */
    private float gravity;


    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        /**
         * 默认左对齐
         */
        gravity = LEFT;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int totalHeight = 0;

        int childCount = this.getChildCount();

        int totalWidth = 0;
        int maxHeightInLine = 0;
        int line = 0;
        int difWidth;

        mDifWidths = new ArrayList<>();

        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            measureChild(childAt, widthMeasureSpec, heightMeasureSpec);

            int childWidth = childAt.getMeasuredWidth();
            int childHeight = childAt.getMeasuredHeight();


            totalWidth += (childWidth + columnSpacing);
            if (totalWidth > width + columnSpacing - (getPaddingLeft() + getPaddingRight())) {
                /**
                 * 计算剩余的宽度并存储
                 */
                difWidth = width - (getPaddingLeft() + getPaddingRight()) - (totalWidth - childWidth) + columnSpacing * 2; //为什么*2,我也不知道
                mDifWidths.add(line++, Math.max(difWidth, 0));

                totalWidth = childWidth + columnSpacing;
                totalHeight += maxHeightInLine + columnSpacing;

                maxHeightInLine = 0;
            }

            if (childHeight > maxHeightInLine) {
                maxHeightInLine = childHeight;
            }

            /**
             * 最后一行的剩余width
             */
            if (i == childCount - 1) {
                difWidth = width - (getPaddingLeft() + getPaddingRight()) - totalWidth + columnSpacing;
                mDifWidths.add(line++, Math.max(difWidth, 0));
            }

        }

        int heightSpec = MeasureSpec.makeMeasureSpec(totalHeight + getPaddingTop() + getPaddingBottom() + maxHeightInLine, MeasureSpec.EXACTLY);
        setMeasuredDimension(widthMeasureSpec, heightSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            layout(l, t, r, b);
        }

        int childCount = this.getChildCount();

        int totalWidth = 0;
        int totalHeight = getPaddingTop();
        int maxHeightInLine = 0;
        int line = 0;
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);

            int childWidth = childAt.getMeasuredWidth();
            int childHeight = childAt.getMeasuredHeight();


            totalWidth += (childWidth + columnSpacing);
            if (totalWidth > getWidth() + columnSpacing - (getPaddingLeft() + getPaddingRight())) {
                totalHeight += maxHeightInLine + columnSpacing;
                totalWidth = childWidth + columnSpacing;

                maxHeightInLine = 0;
                line++;
            }

            if (childHeight > maxHeightInLine) {
                maxHeightInLine = childHeight;
            }

            childAt.layout(
                    getPaddingLeft() + totalWidth - childWidth - columnSpacing + ((int) (mDifWidths.get(line) * gravity + .5f)),
                    totalHeight,
                    getPaddingLeft() + totalWidth - columnSpacing + ((int) (mDifWidths.get(line) * gravity + .5f)),
                    totalHeight + childHeight
            );
        }
    }

    public void setColumnSpacing(int columnSpacing) {
        this.columnSpacing = columnSpacing;
    }

    public void setRowSpacing(int rowSpacing) {
        this.columnSpacing = rowSpacing;
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }
}
