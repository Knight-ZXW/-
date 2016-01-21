package com.think.pintugame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.think.pintugame.view.GamePintuLayout;

public class MainActivity extends AppCompatActivity {
    private GamePintuLayout mGamepintuLayout;
    private TextView mLevel;
    private TextView mTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGamepintuLayout = (GamePintuLayout) findViewById(R.id.id_gamePintu);
        mTime = (TextView) findViewById(R.id.id_time);
        mLevel = (TextView) findViewById(R.id.id_level);
        mGamepintuLayout.setIsTimeEnable(true);
        mGamepintuLayout.setOnGamePintuListener(new GamePintuLayout.GamePintuListener() {
            @Override
            public void nextLevel(final int nextLevel) {
                new AlertDialog.Builder(MainActivity.this).setTitle("Game info")
                        .setMessage("Level UP!!!").setPositiveButton("NEXT LEVEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mGamepintuLayout.nextLevel();
                        mLevel.setText(""+nextLevel);
                    }
                }).show();
            }

            @Override
            public void timeChanged(int currentTime) {
                mTime.setText(""+currentTime);
            }

            @Override
            public void gameOver() {
                new AlertDialog.Builder(MainActivity.this).setTitle("Game Over")
                        .setMessage("The Time is  UP!!!").setPositiveButton("RESTART", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mGamepintuLayout.reStart();
                    }
                }).setNegativeButton("QUIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGamepintuLayout.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGamepintuLayout.pause();
    }
}
