package com.think.surfacechoujiang;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by ${XiuWuZhuo} on 2016/1/10.
 * Emial:nimdanoob@163.com
 */
public class LucklyPan extends SurfaceView implements SurfaceHolder.Callback,Runnable{

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

    private int[] mColors = new int[]{0xFFFC300,0xFFF17F01,0xFFFC300,0xFFF17F01,0xFFFC300,0xFFF17F01};

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

    //
    private float mTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,20,getResources().getDisplayMetrics());



    /**
     * 转盘的转动其实就是改变起始的angel,因为这个值存在多线程操作，使用volatile 保证线程间变量的可见性
     */
    private volatile float mStartAngle = 0;

    /**
     * 判断是否单机了停止按钮
     */
    private boolean isShouldEnd;

    public LucklyPan(Context context) {
        this(context, null);
    }

    public LucklyPan(Context context, AttributeSet attrs) {
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
        //坐标上的center,也是圆上的center
        mCenter = width/2;
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
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
        mTextPaint.setColor(0xffffffff);
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

                float tmpAngle = mStartAngle;
                float sweepAngle = 360/mItemCount;
                for (int i = 0; i < mItemCount; i++) {
                    //绘制盘快
                    mArcPaint.setColor(mColors[i]);
                    mCanvas.drawArc(mRange, tmpAngle, sweepAngle, true, mArcPaint);
                    //绘制文本 todo 如何绘制圆形的文本
                    drawText(tmpAngle, sweepAngle, mStrs[i]);

                    //绘制图标
                    drawIcon(tmpAngle,mImgsBitmaps[i]);

                    tmpAngle += sweepAngle;
                    mStartAngle +=mSpeed;
                    Log.w("mStartAngle", mStartAngle+"");


                    //todo 如果点击了停止按钮，缓慢的停止
                    //已经是个while任务了，-=1就行了
                    if (isShouldEnd){
                        mSpeed -=0.05;
                    }
                    if (mSpeed <=0){
                        mSpeed =0;
                        isShouldEnd = false;
                    }
                }
            }
        } catch (Exception e)
        {

        } finally {
            if (mCanvas != null){
                mHolder.unlockCanvasAndPost(mCanvas);
            }

        }

    }

    //绘制背景
    private void drawBg() {
        mCanvas.drawColor(0xFFFFFFFF);
        mCanvas.drawBitmap(mBgBitmap, null, new RectF(mPadding / 2, mPadding / 2, getMeasuredWidth() - mPadding / 2, getMeasuredHeight() - mPadding / 2), null);
    }

    /**
     * 绘制每个盘快的文本
     * @param startAngle
     * @param sweepAngle
     * @param str
     */
    private void drawText(float startAngle, float sweepAngle, String str) {
        Path path = new Path();
        path.addArc(mRange,startAngle,sweepAngle);
        //水平偏移量 和垂直偏移量
        //水平偏移量需要水平居中
        float textWidth = mTextPaint.measureText(str);
        int hOffset = (int) (mRadius * Math.PI /mItemCount/2 - textWidth/2);
        int vOffset = mRadius/2/6;//暂时设置为垂直的1/6
        mCanvas.drawTextOnPath(str, path, hOffset, vOffset, mTextPaint);
    }


    /**
     * 绘制每个奖品的icon
     * @param tmpAngle
     * @param bitmap
     */
    private void drawIcon(float tmpAngle, Bitmap bitmap) {
        //设置约束图片显示的小打小闹的宽度为直径的1/2
        int imgWidth = mRadius / 8;
        //度数转成弧度
        float angle = (float) ((tmpAngle+360/mItemCount/2)*Math.PI/180);

        int x = (int) (mCenter + mRadius/2/2*Math.cos(angle));
        int y = (int) (mCenter + mRadius/2/2* Math.sin(angle));

        //确定图片的位置
        int halfWidth = imgWidth/2;
        Rect rect = new Rect(x - halfWidth,y-halfWidth,x+halfWidth,y+halfWidth);

        mCanvas.drawBitmap(bitmap, null, rect, null);
    }

    //点击启动旋转
    public void lucklyStart(int index){
        //为mSpeed 赋值
        float angle = 360/mItemCount;
        //计算index项中将范围

        float from =  270 - (index + 1)* angle;
        float end = from + angle;

        //设置停下来需要旋转的距离
        float targetFrom = 4 * 360 +from;
        float targetEnd = 4 * 360 +end;

        //求出速度
        //-b+根号下4av-b
        float v1 = (float) (Math.sqrt(-1 +Math.sqrt(1+8*targetFrom))/2);
        float v2 = (float) (Math.sqrt(-1 +Math.sqrt(1+8*targetEnd))/2);

        //取随机中间
        //还需要注意 点击停止的时候，起始的角度并不一定是在分界点上，所以在end上做了处理
        mSpeed = v1 +Math.random()*(v2-v1);

        isShouldEnd = false;
    }

    //点击停止
    public void luckyEnd(){
        mStartAngle = 0;
        isShouldEnd =true;
    }

    /**
     * 转盘是否在旋转
     * @return
     */
    public boolean isStart(){
        return mSpeed != 0;
    }

    public boolean isShouldEnd(){
        return isShouldEnd;
    }
}
