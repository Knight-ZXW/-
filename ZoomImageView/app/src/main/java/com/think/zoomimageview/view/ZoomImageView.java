package com.think.zoomimageview.view;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 * Created by ${XiuWuZhuo} on 2016/1/9.
 * Emial:nimdanoob@163.com
 */
public class ZoomImageView extends ImageView implements ViewTreeObserver.OnGlobalLayoutListener
        , ScaleGestureDetector.OnScaleGestureListener,View.OnTouchListener {
    //1.如何捕获到Image加载完成的事件 -> OnGlobalLayoutListener()

    /**
     * 是否是第一次进入onGlobaLayout的标志
     */
    private boolean mOnce;
    /**
     * 初始化时缩放的值
     */
    private float mInitScale;

    /**
     * 双击放大时到达的值
     */
    private float mMidScale;

    /**
     * 最大的放大值
     */
    private float mMaxScale;

    private Matrix mScaleMatrix;

    //todo 多点触控用的帮助类（将事件交给它去处理判断，）
    private ScaleGestureDetector mScaleGestureDetector;



    //-------------自由移动------------------------------------------
    /**
     * 记录上一次多点触控的数量(数量发生变化时，中心点会变化)
     */
    private int mLastPointerCount;

    //上次的中心点
    private float mLastX;
    private float mLastY;
    //Touch判断的阈值
    private float mTouchSlop;
    //由mTouchSlop判断得值
    private boolean isCanDrag;

    //是否有必要进行边界判断
    private boolean isCheckLeftAndrRight;
    private boolean isCheckTopAndBottom;

    //--------------双击放大缩小--------------------------------------------------
    private GestureDetector mGestureDetector;
    private boolean isAutoScale;


    public ZoomImageView(Context context) {
        this(context, null);
    }

    public ZoomImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZoomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScaleMatrix = new Matrix();
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        //todo tip4 该方法也可在xml配置
        setScaleType(ScaleType.MATRIX);

        mScaleGestureDetector = new ScaleGestureDetector(context,this);
        setOnTouchListener(this);
        mGestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (isAutoScale) return true;
                float x = e.getX();
                float y = e.getY();

                //如果当前scale 小于mMidScale 则直接 放大到mMidScale  否则 放大到 mInitScale
                if (getScale() < mMidScale){
//                    mScaleMatrix.postScale(mMidScale/getScale(),mMidScale/getScale(),x,y);
//                    setImageMatrix(mScaleMatrix);
                    //todo 缓慢地的 放大缩小
                    postDelayed(new AutoScaleRunnable(mMidScale,x,y),16);
                    isAutoScale =true;
                } else {
//                    mScaleMatrix.postScale(mInitScale/getScale(),mInitScale/getScale(),x,y);
//                    setImageMatrix(mScaleMatrix);
                    postDelayed(new AutoScaleRunnable(mInitScale, x, y), 16);
                    isAutoScale =true;
                }

                return true;
            }
        });
    }

    //todo tip1: 获得ViewTreeObserver，注册接口，得到图片布局设置完成的时机
    //在此时注册globalLayout接口
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    //此时将接口移除
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }

    }

    /**
     * 获取ImageView加载完成的图片
     */
    @Override
    public void onGlobalLayout() {
        if (!mOnce){
            //得到控件的宽和高
            int width = getWidth();
            int height = getHeight();
            //得到图片，以及宽和高
            //todo tip2:获得ImageView 设置的图片
            Drawable d = getDrawable();
            if ( d == null)
                return;
            //
            int dw = d.getIntrinsicWidth();
            int dh = d.getIntrinsicHeight();

            float scale = 1.0f;

            /**
             * 如果图片的宽度大于控件的宽度，高度小于控件的高度，将其缩小
             */
            if (dw >width && dh <height){
                scale = width*1.0f / dw;
            }

            if (dh >height && dw <width){
                scale = height * 1.0f / dh;
            }

            if ( (dh >height && dw >width) || (dw < width && dh < height)){
                scale = Math.min(width * 1.0f/dw,height*1.0f/dh);
            }
            /**
             * 得到了初始化时缩放的比例
             */
            mInitScale = scale;
            mMaxScale = mInitScale * 4;
            mMidScale = mInitScale * 0.5f;

            //将图片移动到控件的中心
            int dx = getWidth()/2 - dw/2;
            int dy = getHeight()/2 - dh/2;

            //todo tip3 scale是什么？ 一个3x3的矩阵
            // xScale xSkew xTrans
            // ySkew  yScle yTrans
            //   0     0      0
            mScaleMatrix.postTranslate(dx,dy);
            mScaleMatrix.postScale(mInitScale,mInitScale,width/2,height/2);
            setImageMatrix(mScaleMatrix);
            mOnce = true;


        }
    }
    /////////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        //获得手势判断取得的缩放值
        float scaleFactor = detector.getScaleFactor();
        //当前的缩放倍数
        float scale = getScale();

        if (getDrawable() == null)
            return true;

        //缩放范围的控制
        if ((scale < mMaxScale && scaleFactor> 1.0f)
                || (scale > mMidScale && scaleFactor <1.0f)){
            if (scale * scaleFactor < mMidScale){
                scaleFactor = mMidScale / scale;
            }

            if (scale * scaleFactor > mMaxScale){
                scaleFactor = mMaxScale / scale;
            }
            //缩放
            mScaleMatrix.postScale(scaleFactor,scaleFactor,detector.getFocusX(),detector.getFocusY());
            //todo tip5 手指触摸的位置放大 再缩小导致的图片位置问题

            checkBorderAndCenterWhenScale();

            setImageMatrix(mScaleMatrix);
        }

        return true;
    }

    /**
     * //todo tip6 获得图片放大缩小以后的宽和搞 ，以及 l t r b
     * @return
     */
    private RectF getMatrixRectF(){
        Matrix matrix = mScaleMatrix;
        RectF rectF = new RectF();

        Drawable d = getDrawable();
        if (d != null){
            rectF.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            matrix.mapRect(rectF);
        }

        return rectF;
    }
    /**
     * todo tip7 缩放的时候进行边界控制 以及位置的控制
     */
    private void checkBorderAndCenterWhenScale() {
        RectF rect = getMatrixRectF();

        float deltaX = 0;
        float deltaY = 0;

        int width = getWidth();
        int height = getHeight();

        //如果改变后的宽度大于 屏幕宽度，避免出现白边，即rectF left<=0 right>=width 应该处于正确的值
        if (rect.width() > width){
            if (rect.left > 0){
                deltaX = -rect.left;
            }

            if (rect.right < width){
                deltaX = width -rect.right;
            }
        }

        if (rect.height() > height){
            if (rect.top > 0){
                deltaY = -rect.top;
            }

            if (rect.bottom < width){
                deltaY = height -rect.bottom;
            }
        }

        //如果宽度 或者高度 小于控件的宽高，让其居中
        if (rect.width() < width){
            deltaX = width/2f - rect.right +rect.width()/2f;
        }

        if (rect.height() < height){
            deltaY = height/2f - rect.bottom + rect.height()/2f;
        }

        mScaleMatrix.postTranslate(deltaX,deltaY);
    }

    private void checkBorderWhenTranslate() {
        RectF rectF = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;
        int width = getWidth();
        int height = getHeight();
        if (rectF.top >0 && isCheckTopAndBottom){
            deltaY = -rectF.top;
        }

        if (rectF.bottom <height && isCheckTopAndBottom){
            deltaY = height - rectF.bottom;
        }

        if (rectF.left >0 && isCheckLeftAndrRight){
            deltaX = - rectF.left;
        }
        if (rectF.right < width && isCheckLeftAndrRight){
            deltaX = width - rectF.right;
        }

        mScaleMatrix.postTranslate(deltaX,deltaY);
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {

        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    ///////////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //双击的优先级别最高，如果是双击，即直接消费
        if (mGestureDetector.onTouchEvent(event))
            return true;
        mScaleGestureDetector.onTouchEvent(event);

        float x = 0;
        float y = 0;
        //得到多点触控的数量
        int pointerCount = event.getPointerCount();
        //todo tip8 在多手指点击时，判断中心点的位置（根据情况）
        for (int i = 0; i < pointerCount; i++) {
            x += event.getX(i);
            y += event.getY(i);
        }
        x/=pointerCount;
        y/=pointerCount;

        //用户在手指触摸过程中，数量发生变化
        if (mLastPointerCount != pointerCount) {
            //todo tip 手指触摸数量发生变化，重置标志位
            isCanDrag = false;
            mLastX = x;
            mLastY = y;
        }
        mLastPointerCount = pointerCount;

        RectF rectF = getMatrixRectF();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //todo 希望在图片大于屏幕时不被拦截事件
                if (rectF.width() > getWidth() + 0.01 || rectF.height() > getHeight() + 0.01){
                    if (getParent() instanceof ViewGroup) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = x - mLastX;
                float dy = y - mLastY;
                if (rectF.width() > getWidth() +0.01 || rectF.height() > getHeight() + 0.01){
                    if (getParent() instanceof ViewGroup) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }

                }
                if (!isCanDrag){
                    //手指数量发生变化时，判断标志位
                    isCanDrag = isMoveAction(dx,dy);
                }

                if (isCanDrag){

                    if (getDrawable() != null){
                        isCheckLeftAndrRight = isCheckTopAndBottom = true;
                        //宽度小于控件宽度，不做横向移动
                        if (rectF.width() < getWidth()){
                            isCheckLeftAndrRight = false;
                            dx = 0;
                        }
                        //高度小于控件高度，不做竖向移动
                        if (rectF.height() < getHeight()){
                            isCheckTopAndBottom = false;
                            dy =0;
                        }

                        mScaleMatrix.postTranslate(dx,dy);
                        checkBorderWhenTranslate();
                        setImageMatrix(mScaleMatrix);
                    }
                }


                break;

            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                mLastPointerCount = 0;
                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }

    /**
     * 判断是否move
     * @param dx
     * @param dy
     * @return
     */
    private boolean isMoveAction(float dx, float dy) {
        return Math.sqrt(dx*dx + dy*dy) > mTouchSlop;
    }

    /**
     * 获取当前图片的缩放值 (x,y一样)
     * @return
     */
    public float getScale(){
        float[] values = new float[9];
        mScaleMatrix.getValues(values);
        return values[Matrix.MSCALE_X];
    }

    /**
     * 自动放大与缩小
     */
    private class AutoScaleRunnable implements Runnable{
        /**
         * 缩放的目标值
         */
        private float mTargetScale;
        private float x;
        private float y;

        private final  float BIGGER = 1.07f;
        private final  float SMALL = 0.93f;

        private float tmpScale;
        public AutoScaleRunnable(float mTargetScale, float x, float y) {
            this.mTargetScale = mTargetScale;
            this.x = x;
            this.y = y;

            if (getScale() < mTargetScale){
                //放大
                tmpScale = BIGGER;
            } else if (getScale()>mTargetScale){
                tmpScale = SMALL;
            } else {
                tmpScale = 1f;
            }

        }

        @Override
        public void run() {
            mScaleMatrix.postScale(tmpScale,tmpScale,x,y);
            //进行缩放

            checkBorderAndCenterWhenScale();
            setImageMatrix(mScaleMatrix);

            float currentScale = getScale();
            if ( (tmpScale > 1.0f && currentScale < mTargetScale)
                    || tmpScale <1.0f && currentScale > mTargetScale){
                //todo tip9 如果没达到这个预期再次执行run
                postDelayed(this,16);
            } else {//得到预期的值了
                float scale = mTargetScale / currentScale;
                mScaleMatrix.postScale(scale, scale, x, y);
                checkBorderAndCenterWhenScale();
                setImageMatrix(mScaleMatrix);
                //结束了
                isAutoScale = false;
            }
        }
    }
}
