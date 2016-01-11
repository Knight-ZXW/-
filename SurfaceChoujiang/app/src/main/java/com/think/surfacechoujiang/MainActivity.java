package com.think.surfacechoujiang;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private LucklyPan mLuckyPan;
    private ImageView mstartBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLuckyPan = (LucklyPan) findViewById(R.id.id_lucklyPan);
        mstartBtn = (ImageView)findViewById(R.id.id_start_btn);

        mstartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mLuckyPan.isStart()){//此时没有启动，按后启动，图片转为stop
                    mLuckyPan.lucklyStart(1);
                    mstartBtn.setImageResource(R.mipmap.stop);
                } else {
                    //排除，已经按下stop，但是stop还没执行完的情况
                    if (!mLuckyPan.isShouldEnd()){
                        mLuckyPan.luckyEnd();
                        mstartBtn.setImageResource(R.mipmap.start);
                    }
                }
            }
        });
    }
}
