package com.think.surfacechoujiang;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by ${XiuWuZhuo} on 2016/1/10.
 * Emial:nimdanoob@163.com
 */
public class SurfaceViewTempalte extends SurfaceView implements SurfaceHolder.Callback,Runnable{

    private SurfaceHolder mHolder;
    private Canvas mCanvas;

    /**
     * 用于绘制的线程
     */
    private Thread t;

    /**
     * 线程的控制开关
     */
    private boolean isRunning;

    private int mItemCount = 6;


    //图片对应的bitmap
    private Bitmap[] mImgsBitmaps;

    private String[] mStrs = new String[]{"单反相机","IPAD","恭喜发财","IPHONE","太平鸟服装","恭喜发财"};

    private int[] mImgs = new int[]{R.mipmap.danfan,R.mipmap.ipad,R.mipmap.meizi,R.mipmap.iphone,R.mipmap.f015,R.mipmap.f040};

    private int[]mColor = new int[]{0xFFFC300,0xFFF17F01,0xFFFC300,0xFFF17F01,0xFFFC300,0xFFF17F01};

    /**
     * 真个盘快的范围
     */
    private RectF mRange = new RectF();

    /**
     * 整个盘快的直径
     */
    private int mRadius;

    /**
     * 绘制盘快的画笔
     */
    private Paint mArcPaint;

    /**
     *绘制文本的画笔
     */
    private Paint mTextPaint;

    /**
     * 转盘的中心位置
     */
    private int mCenter;

    /**
     * padding 直接以paddingleft 为准
     */
    private int mPadding;

    /**
     * 盘快滚动的速度
     */
    private double mSpeed;

    //盘快的背景
    private Bitmap mBgBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bg2);

    //SHIFT 其实就是PX.
    private float mTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SHIFT,20,getResources().getDisplayMetrics());



    /**
     * 转盘的转动其实就是改变起始的angel,因为这个值存在多线程操作，使用volatile 保证线程间变量的可见性
     */
    private volatile int mStartAngle = 0;

    /**
     * 判断是否单机了停止按钮
     */
    private boolean isShouldEnd;

    public SurfaceViewTempalte(Context context) {
        this(context, null);
    }

    public SurfaceViewTempalte(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHolder = getHolder();

        mHolder.addCallback(this);

        //todo tip1 可获得焦点
        setFocusable(true);
        setFocusableInTouchMode(true);
        //设置常量  ? 屏幕一致处于开启状态
        setKeepScreenOn(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = Math.min(getMeasuredWidth(),getMeasuredHeight());
        mPadding = getPaddingLeft();
        mRadius = width-mPadding*2;
        mCenter = mRadius/2;
        //将其设置成正方形
        setMeasuredDimension(width, width);
    }

    //此时才进行一些绘制api的初始化
    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        //初始化绘制盘快的画笔
        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(true);
        mArcPaint.setDither(true);

        //初始话文本的画笔
        mTextPaint = new Paint();
        mTextPaint.setTextSize(mTextSize);

        //初始化盘快绘制的范围
        mRange = new RectF(mPadding,mPadding,mPadding+mRadius,mPadding+mRadius);

        //初始化图片
        mImgsBitmaps = new Bitmap[mItemCount];

        for (int i = 0; i < mItemCount; i++) {
            mImgsBitmaps[i] = BitmapFactory.decodeResource(getResources(),mImgs[i]);
        }

        isRunning = true;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRunning = false;
    }

    @Override
    public void run() {
        //不断进行绘制
        while (isRunning){
            long start = System.currentTimeMillis();
            draw();
            long end = System.currentTimeMillis();
            //todo 2 如果绘制在50ms之前就完成了，那么久睡眠一伙儿
            if (end - start <50){
                try {
                    Thread.sleep(50-(end - start));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void draw(){

        //todo tip2 ,为什么需要判断空，在用户按home键，或其他情况下 mcanvans可能被销毁。所以需要判断空
        //或者是canvas销毁了 都是线程还没销毁

        //绘制背景 盘快 图片 转盘 动画
        try {
            mCanvas = mHolder.lockCanvas();

            if (mCanvas != null) {
                // draw thing
                drawBg();
            }
        } catch (Exception e)
        {

        } finally {
            if (mCanvas != null){
                mHolder.unlockCanvasAndPost(mCanvas);
            }

        }

    }

    private void drawBg() {

    }
}
