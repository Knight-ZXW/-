package com.think.pintugame.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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


    /**
     * 游戏的原始图片
     */
    private Bitmap mBitamp;

    private List<ImagePiece> mItemBitmaps;

    private boolean once;
    private int mItemWidth;

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
        }
        setMeasuredDimension(mWidth, mWidth);
    }


    private void initBitmap() {
        if (mBitamp == null){
            mBitamp = BitmapFactory.decodeResource(getResources(), R.mipmap.image);
        }

        mItemBitmaps = ImageSplitterUtil.splitImage(mBitamp,3);
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
        String firstTag = (String) mFirst.getTag();
        String secondTag = (String)mSecond.getTag();
        String[] firstParams = firstTag.split("_");
        String[] secondParams = secondTag.split("_");
        Bitmap firstBitmap = mItemBitmaps.get(Integer.parseInt(firstParams[0])).getBitmap();
        mSecond.setImageBitmap(firstBitmap);

        Bitmap secondBitmap = mItemBitmaps.get(Integer.parseInt(secondParams[0])).getBitmap();
        mFirst.setImageBitmap(secondBitmap);

//        Drawable firstDrawable = mFirst.getDrawable();
//        Drawable secondDrawable = mSecond.getDrawable();
//        mFirst.setImageDrawable(secondDrawable);
//        mSecond.setImageDrawable(firstDrawable);


//        //改变tag
//        String tempTag = (String) mFirst.getTag();
//        mFirst.setTag(mSecond.getTag());
//        Log.w("logger", mFirst.getTag() + "");
//        mSecond.setTag(tempTag);
//        Log.w("logger", mSecond.getTag() + "");

        mFirst = mSecond = null;
    }
}
