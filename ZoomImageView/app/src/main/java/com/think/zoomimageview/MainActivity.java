package com.think.zoomimageview;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.think.zoomimageview.view.ZoomImageView;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    private int[] mImgs = new int[]{R.mipmap.girl,R.mipmap.girl,R.mipmap.girl};
    private ImageView[] mImgViews = new ImageView[3];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mImgViews.length;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ZoomImageView imageView = new ZoomImageView(MainActivity.this);
                imageView.setImageResource(mImgs[position]);
                container.addView(imageView);
                mImgViews[position] = imageView;
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mImgViews[position]);
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        });

    }
}
