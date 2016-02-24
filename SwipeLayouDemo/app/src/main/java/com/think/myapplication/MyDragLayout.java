package com.think.myapplication;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by XiuWuZhuo on 2016/2/23.
 * Emial:nimdanoob@163.com
 */
public class MyDragLayout extends FrameLayout {
    private ViewDragHelper mDragHelper;

    public MyDragLayout(Context context) {
        this(context, null);
    }

    public MyDragLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDragHelper = ViewDragHelper.create(this, 1.0f, new DragHelperCallback());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        // 在 continueSettling 中 调用offsetLeftAndRight offsetTopAndBottom 对view进行移动
        if (mDragHelper.continueSettling(true)){
            invalidate();
        }
    }

    class DragHelperCallback extends ViewDragHelper.Callback {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            //可能我们给自定义的layout 设置了padding属性
            final int leftBound = getPaddingLeft();
            //left 的 最大界限 是布局的宽 - padding - 拖拽view的宽
            final int rightBound = getWidth() - leftBound -child.getWidth();
            //系统帮我们判断的left 和 我们的margin（margin可能为负） 取最大值，在和 最大界限取最小值
            return Math.min(Math.max(leftBound,left),rightBound);
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            //可能我们给自定义的layout 设置了padding属性
            final int topBound = getPaddingTop();
            //left 的 最大界限 是布局的宽 - padding - 拖拽view的宽
            final int bottomBound = getHeight() - topBound -child.getHeight();
            //系统帮我们判断的left 和 我们的margin（margin可能为负） 取最大值，在和 最大界限取最小值
            return Math.min(Math.max(topBound,top),bottomBound);
        }

        // 如果我们应该在这个方法中
        // 调用settleCapturedViewAt(int, int) or flingCapturedView(int, int, int, int).
        // 如果调用了其中一个 ViewDragHelper会进入 STATE_SETTLING 状态
        // 如果没有 会进入STATE_IDLE 状态
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            mDragHelper.settleCapturedViewAt(200,200);
            //不要忘记调用invalidate(),
            invalidate();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mAutoBackX = w/2;
        mAutoBackY = h/2;
    }
    private int  mAutoBackX;
    private int  mAutoBackY;
}
