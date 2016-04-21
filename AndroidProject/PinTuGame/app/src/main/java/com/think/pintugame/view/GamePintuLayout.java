package com.think.pintugame.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.think.pintugame.R;
import com.think.pintugame.utils.ImagePiece;
import com.think.pintugame.utils.ImageSplitterUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by XiuWuZhuo on 2016/1/11.
 * Emial:nimdanoob@163.com
 */
public class GamePintuLayout extends RelativeLayout implements View.OnClickListener {

    //首先分析应该有哪些成员变量
    private int mColumn = 3;
    /**
     * 容器的内边距
     */
    private int mPadding;
@Deprecated
    /**
     * 每张小图之间的距离
     */
    private int mMargin = 3;

    private ImageView[] mGamePintuItems;

    /**
     * 游戏面板宽度
     */
    private int mWidth;


    private boolean isGameSuccess;
    private int mTime = 20;
    /**
     * 游戏的原始图片
     */
    private Bitmap mBitamp;

    private List<ImagePiece> mItemBitmaps;

    private RelativeLayout mAnimLayout;

    private boolean once;
    private int mItemWidth;
    private boolean isAnim;

    public interface GamePintuListener{
        void nextLevel(int nextLevel);
        void timeChanged(int currentTime);
        void gameOver();
    }

    public GamePintuListener mListener;

    /**
     * 设置接口回调
     * @param mListener
     */
    public void setOnGamePintuListener(GamePintuListener mListener) {
        this.mListener = mListener;
    }
    private int level =1;
    private static final int TIME_CHANGED = 0X110;
    private static final int NEXT_LEVLE = 0x111;
    /**
     * 是否设置开启时间
     */
    private boolean isTimeEnable;
    private boolean isGameOver;

    public void setIsTimeEnable(boolean isTimeEnable) {
        this.isTimeEnable = isTimeEnable;
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case TIME_CHANGED:
                    if (isGameSuccess || isGameOver || isPause){
                        return;
                    }

                    if (mListener != null){
                        mListener.timeChanged(mTime);
                        if (mTime == 0){
                            isGameOver = true;
                            mListener.gameOver();
                            return;
                        }
                    }
                    mTime--;
                    mHandler.sendEmptyMessageDelayed(TIME_CHANGED,1000);
                    break;
                case NEXT_LEVLE:
                    level++;
                    if (mListener!=null) {
                        mListener.nextLevel(level);
                    } else {
                        nextLevel();
                    }
                    break;

            }
            super.handleMessage(msg);
        }
    };


    public GamePintuLayout(Context context) {
        this(context,null);
    }

    public GamePintuLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GamePintuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mWidth = Math.min(getMeasuredWidth(),getMeasuredWidth());
        if (!once){
            once =true;
            //进行切图，以及排序
            initBitmap();
            //设置ImageView(Item)的宽高属性
            initItem();

            //判断是否开启时间
            checkTimeEnable();
        }
        setMeasuredDimension(mWidth, mWidth);
    }

    private void checkTimeEnable() {
        if (isTimeEnable){
            //根据当前等级设置时间
            countTimeBaseLevel();
            mHandler.sendEmptyMessage(TIME_CHANGED);
        }
    }

    private void countTimeBaseLevel() {
        mTime = (int) Math.pow(2,level)*60;//2的多少倍 -》也就是事件长度指数增加
        mHandler.sendEmptyMessage(TIME_CHANGED);
    }


    private void initBitmap() {
        if (mBitamp == null){
            mBitamp = BitmapFactory.decodeResource(getResources(), R.mipmap.image);
        }

        mItemBitmaps = ImageSplitterUtil.splitImage(mBitamp,mColumn);
        //todo  如何随机排序
        Collections.sort(mItemBitmaps, new Comparator<ImagePiece>() {
            @Override
            public int compare(ImagePiece a, ImagePiece b) {
                return Math.random() > 0.5 ? 1 : -1;
            }
        });

    }

    /**
     * 设置ImageView(Item) 的宽高属性
     */
    private void initItem() {
        mItemWidth = (mWidth - mPadding * 2 - mMargin * (mColumn - 1)) / mColumn;
        mGamePintuItems = new ImageView[mColumn*mColumn];
        int length = mGamePintuItems.length;
        for (int i = 0; i < length; i++) {
            ImageView item = new ImageView(getContext());
            item.setOnClickListener(this);
            //已经乱序了
            item.setImageBitmap(mItemBitmaps.get(i).getBitmap());
            mGamePintuItems[i] = item;
            // todo 这边id设为 1+d有点不妥吧
            item.setId(i + 1);
            //在Item 的tag 中存储了index,index代表了这张图片
            item.setTag(i+"_"+mItemBitmaps.get(i).getIndex());

            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(mItemWidth,mItemWidth);
            //观察图像，发现不同行 不同列的特点
            //设置Item间横向间隙

            //不是最后一列
            if ( (i+1) % mColumn != 0){
                lp.rightMargin = mMargin;
            }
            //不是第一列
            if (i % mColumn !=0){
                lp.addRule(RelativeLayout.RIGHT_OF,mGamePintuItems[i-1].getId());
            }

            //纵向的间隙  如果不是第一行 设置topMargin 和rule
            if ( (i+1)>mColumn){
                lp.topMargin = mMargin;
                lp.addRule(RelativeLayout.BELOW,mGamePintuItems[i - mColumn].getId());
            }
            addView(item,lp);
        }

    }



    private void init() {
        //3dp
        mMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,3,getResources().getDisplayMetrics());

        mPadding = minPadding(getPaddingLeft(), getPaddingRight(), getPaddingTop(), getPaddingBottom());
    }

    /**
     * 返回最小值
     * @param params
     * @return
     */
    private int minPadding(int... params) {
        int min = params[0];
        for (int param: params){
            if (param < min)
                min = param;
        }
        return min;
    }

    //点击的2个
    private ImageView mFirst;
    private ImageView mSecond;

    @Override
    public void onClick(View v) {

        if (isAnim)
            return;

        //当时2次点击一个 第一个 Item时
        if (mFirst == v){
            mFirst.setColorFilter(null);
            mFirst = null;
            return;
        }
        if (mFirst == null){
            mFirst = (ImageView) v;
            mFirst.setColorFilter(Color.parseColor("#55CC0000"));
        } else {
            mSecond = (ImageView) v;
            //交换Item
            exchangeView();
        }
    }

    private void exchangeView() {
        mFirst.setColorFilter(null);

        setUpAnimLayout();

        final ImageView first = new ImageView(getContext());
        final Bitmap firstBitmap = mItemBitmaps.get(getImageIdByTag((String) mFirst.getTag())).getBitmap();

        first.setImageBitmap(firstBitmap);
        RelativeLayout.LayoutParams lp = new LayoutParams(mItemWidth,mItemWidth);
        lp.leftMargin = mFirst.getLeft() - mPadding;//动画层是在padding里面的，所以要减去padding
        lp.topMargin = mFirst.getTop() - mPadding;
        first.setLayoutParams(lp);
        mAnimLayout.addView(first);

        ImageView second = new ImageView(getContext());
        final Bitmap secondBitmap = mItemBitmaps.get(getImageIdByTag((String) mSecond.getTag())).getBitmap();

        second.setImageBitmap(secondBitmap);
        RelativeLayout.LayoutParams lp2 = new LayoutParams(mItemWidth,mItemWidth);
        lp2.leftMargin = mSecond.getLeft() - mPadding;//动画层是在padding里面的，所以要减去padding
        lp2.topMargin = mSecond.getTop() - mPadding;
        second.setLayoutParams(lp2);
        mAnimLayout.addView(second);

        //设置动画
        TranslateAnimation anim = new TranslateAnimation(0,mSecond.getLeft()-mFirst.getLeft(),0,mSecond.getTop()-mFirst.getTop());
        anim.setDuration(300);
        anim.setFillAfter(true);
        first.startAnimation(anim);

        TranslateAnimation animSecond = new TranslateAnimation(0,-mSecond.getLeft()+mFirst.getLeft(),0,-mSecond.getTop()+mFirst.getTop());
        animSecond.setDuration(300);
        animSecond.setFillAfter(true);
        second.startAnimation(animSecond);

        //监听动画
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mFirst.setVisibility(View.INVISIBLE);
                mSecond.setVisibility(View.INVISIBLE);
                isAnim = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                String firstTag = (String) mFirst.getTag();
                String secondTag = (String) mSecond.getTag();
                mFirst.setImageBitmap(secondBitmap);
                mSecond.setImageBitmap(firstBitmap);

                mFirst.setTag(secondTag);
                mSecond.setTag(firstTag);

                mFirst.setVisibility(VISIBLE);
                mSecond.setVisibility(VISIBLE);

                mAnimLayout.removeAllViews();
                mFirst = mSecond = null;
                //判断用户游戏是否成功
                checkSuccess();
                isAnim = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * 判断用户游戏是否成功
     */
    private void checkSuccess() {
        boolean isSuccess  = true;
        for (int i = 0;i < mGamePintuItems.length;i++){
            ImageView imageView  = mGamePintuItems[i];
            if (getImageIndexByTag((String) imageView.getTag()) != i){
                isSuccess  = false;
            }
        }
        if (isSuccess){
            Log.w("logger","is Success");
            isGameSuccess = true;
            mHandler.removeMessages(TIME_CHANGED);

            Toast.makeText(getContext(),"Success ,Game Level Up",Toast.LENGTH_LONG).show();
            mHandler.sendEmptyMessage(NEXT_LEVLE);
        }
    }

    public void reStart(){
        isGameOver = false;
        mColumn --;
        nextLevel();
    }

    private boolean isPause;
    public void pause(){
        isPause = true;
        mHandler.removeMessages(TIME_CHANGED);
    }

    public void resume(){
        if (isPause){
            isPause = false;
            mHandler.sendEmptyMessage(TIME_CHANGED);
        }
    }
    public void nextLevel(){
        this.removeAllViews();
        mAnimLayout = null;
        mColumn++;
        isGameSuccess = false;
        checkTimeEnable();
        initBitmap();
        initItem();
    }
    /**
     * 根据 tag  获取Id
     * @param tag
     * @return 图片id
     */
    public int getImageIdByTag(String tag){
        String[] split = tag.split("_");
        return Integer.parseInt(split[0]);
    }

    public int getImageIndexByTag(String tag){
        String[] split = tag.split("_");
        return Integer.parseInt(split[1]);
    }

    /**
     * 构造动画层
     */
    private void setUpAnimLayout() {
        if (mAnimLayout == null){
            mAnimLayout = new RelativeLayout(getContext());
            addView(mAnimLayout);
        }
    }
}
