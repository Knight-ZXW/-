package com.zous.swipeback;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by XiuWuZhuo on 2016/2/23.
 * Emial:nimdanoob@163.com
 */
public class SwipeBackLayout extends FrameLayout{

    private static final String TAG = "SwipeBackLayout";

    public enum DragEdge {
        LEFT,

        TOP,

        RIGHT,

        BOTTOM
    }

    private DragEdge dragEdge = DragEdge.TOP;

    public void setDragEdge(DragEdge dragEdge) {
        this.dragEdge = dragEdge;
    }


    private static final double AUTO_FINISHED_SPEED_LIMIT = 2000.0;

    private final ViewDragHelper viewDragHelper;

    private View target;

    private View scrollChild;

    private int verticalDragRange = 0;

    private int horizontalDragRange = 0;

    private int draggingState = 0;

    private int draggingOffset;

    /**
     * Whether allow to pull this layout.
     */
    private boolean enablePullToBack = true;

    private static final float BACK_FACTOR = 0.5f;

    /**
     * the anchor of calling finish.
     */
    private float finishAnchor = 0;

    /**
     * Set the anchor of calling finish.
     *
     * @param offset
     */
    public void setFinishAnchor(float offset) {
        finishAnchor = offset;
    }

    private boolean enableFlingBack = true;

    /**
     * Whether allow to finish activity by fling the layout.
     *
     * @param b
     */
    public void setEnableFlingBack(boolean b) {
        enableFlingBack = b;
    }

    private SwipeBackListener swipeBackListener;

    @Deprecated
    public void setOnPullToBackListener(SwipeBackListener listener) {
        swipeBackListener = listener;
    }

    public void setOnSwipeBackListener(SwipeBackListener listener) {
        swipeBackListener = listener;
    }


    public SwipeBackLayout(Context context) {
        this(context,null);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        viewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelperCallBack());
    }


    public interface SwipeBackListener {

        /**
         * Return scrolled fraction of the layout.
         *
         * @param fractionAnchor relative to the anchor.
         * @param fractionScreen relative to the screen.
         */
        public void onViewPositionChanged(float fractionAnchor, float fractionScreen);

    }

    private class ViewDragHelperCallBack extends ViewDragHelper.Callback {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return false;
        }
    }
}
