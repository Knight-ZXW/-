package com.think.crazycat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Vector;

/**
 * Created by think on 2016/1/7.
 */

public class Playground extends SurfaceView implements View.OnTouchListener {
    private static final int ROW = 10;
    private static final int COL = 10;

    private static int WIDTH;
    //默认添加的路障数量
    private static final int BLOCKS = 10;
    private Dot matrix[][];
    private Dot cat;

    //游戏刷新
    private boolean justInit;


    public Playground(Context context) {
        super(context);
        getHolder().addCallback(callback);
        matrix = new Dot[ROW][COL];
        setOnTouchListener(this);
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                matrix[i][j] = new Dot(j, i);
            }
        }
        initGame();
    }

    private Dot getDot(int x, int y) {
        return matrix[y][x];
    }

    private boolean isAtEdge(Dot d) {
        if (d.getX() * d.getY() == 0 || d.getX() + 1 == COL || d.getY() + 1 == ROW) {
            return true;
        }
        return false;
    }

    /**
     * @param one
     * @param dir 返回不同方位的点参数为1-6
     * @return
     */
    private Dot getNeighbour(Dot one, int dir) {
        switch (dir) {
            case 1:
                return getDot(one.getX() - 1, one.getY());
            case 2:
                if (one.getY() % 2 == 0) {
                    return getDot(one.getX() - 1, one.getY() - 1);
                } else {
                    return getDot(one.getX(), one.getY() - 1);
                }
            case 3:
                if (one.getY() % 2 == 0) {
                    return getDot(one.getX(), one.getY() - 1);
                } else {
                    return getDot(one.getX() + 1, one.getY() - 1);
                }
            case 4:
                return getDot(one.getX() + 1, one.getY());
            case 5:
                if (one.getY() % 2 == 0) {
                    return getDot(one.getX(), one.getY() + 1);
                } else {
                    return getDot(one.getX() + 1, one.getY() + 1);
                }
            case 6:
                if (one.getY() % 2 == 0) {
                    return getDot(one.getX() - 1, one.getY() + 1);
                } else {
                    return getDot(one.getX(), one.getY() + 1);
                }

            default:
                break;
        }
        return null;
    }

    /**
     * 获得该点距离边界的 距离，负数表示有障碍的距离，正数表示该方面无障碍的距离
     *
     * @param one
     * @param dir
     * @return
     */
    private int getDistance(Dot one, int dir) {
        int distance = 0;
        boolean toedge = true;
        if (isAtEdge(one)){
            return 1;
        }
        Dot ori, next;
        ori = one;
        while (toedge) {
            next = getNeighbour(ori, dir);
            if (next.getStatus() == Dot.STATUS_ON) {
                return distance * -1;
            }
            if (isAtEdge(next)) {
                distance++;
                return distance;
            }
            distance++;
            ori = next;
        }
        return distance;
    }

    private void MoveTo(Dot one) {
        one.setStatus(Dot.STATUS_IN);
        getDot(cat.getX(), cat.getY()).setStatus(Dot.STATUS_OFF);
        cat.setXY(one.getX(), one.getY());
    }

    private void move() {
        if (isAtEdge(cat)) {//cat 在边界，则输掉游戏
            lose();
            return;
        }
        //所有可用的点
        Vector<Dot> avaliable = new Vector<>();
        //方向上没有阻碍的点
        Vector<Dot> positive = new Vector<>();
        HashMap<Dot,Integer> al = new HashMap<>();
        for (int i = 1; i < 7; i++) {
            Dot neighbour = getNeighbour(cat, i);
            if (neighbour.getStatus() == Dot.STATUS_OFF) {//如果点可用
                avaliable.add(neighbour);
                al.put(neighbour, i);
                if (getDistance(neighbour,i)>0){
                    positive.add(neighbour);
                }
            }
        }
        if (avaliable.size() == 0) {
            win();
        } else if (avaliable.size() == 1) {//只有一个点可以移动
            MoveTo(avaliable.get(0));
        }else {//即不是第一次点击，而且有多条路可走
            Dot best = null;
            if (positive.size() != 0){//存在可以直接到达屏幕边缘的走向
                int min = 999;
                for (int i = 0; i < positive.size(); i++) {
                    int distance = getDistance(positive.get(i), al.get(positive.get(i)));
                    if (distance < min)
                        min = distance;
                        best = positive.get(i);
                }
            }else {//所有的方向都有路障
                int max = 0;
                for (int i = 0; i < avaliable.size(); i++) {
                    int k = getDistance(avaliable.get(i),al.get(avaliable.get(i)));
                    if (k < max){
                        max = k;
                        best = avaliable.get(i);
                    }
                }
            }
            MoveTo(best);
        }

    }

    private void lose() {
        Toast.makeText(getContext(), "Lose !!!", Toast.LENGTH_LONG).show();
    }

    private void win() {
        Toast.makeText(getContext(), "You Win !!!", Toast.LENGTH_LONG).show();

    }

    public void redraw() {
        Canvas c = getHolder().lockCanvas();
        c.drawColor(Color.LTGRAY);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        for (int i = 0; i < ROW; i++) {
            int offset = 0;
            if (i % 2 != 0) {
                offset = WIDTH / 2;
            }
            for (int j = 0; j < COL; j++) {
                Dot one = getDot(j, i);
                switch (one.getStatus()) {
                    case Dot.STATUS_OFF:
                        paint.setColor(0xFFEEEEEE);
                        break;
                    case Dot.STATUS_ON:
                        paint.setColor(0xFFFFAA00);
                        break;
                    case Dot.STATUS_IN:
                        paint.setColor(0xFFFF0000);
                        break;
                }
                c.drawOval(new RectF(one.getX() * WIDTH + offset, one.getY() * WIDTH, (one.getX() + 1) * WIDTH + offset, (one.getY() + 1) * WIDTH), paint);
            }
        }

        getHolder().unlockCanvasAndPost(c);
    }

    SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            redraw();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            WIDTH = width / (COL + 1);
            Log.w("logger", "surfaceChanged" + width + ":" + height);
            redraw();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    };

    private void initGame() {
        justInit = true;
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                matrix[i][j].setStatus(Dot.STATUS_OFF);
            }
        }
        cat = new Dot(4, 5);
        getDot(4, 5).setStatus(Dot.STATUS_IN);

        for (int i = 0; i < BLOCKS; ) {
            int x = (int) ((Math.random() * 1000) % COL);
            int y = (int) ((Math.random() * 1000) % ROW);
            if (getDot(x, y).getStatus() == Dot.STATUS_OFF) {
                getDot(x, y).setStatus(Dot.STATUS_ON);
                i++;
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            int x, y;
            y = (int) event.getY() / WIDTH;
            if (y % 2 == 0) {
                x = (int) event.getX() / WIDTH;
            } else {
                x = (int) (event.getX() - WIDTH / 2) / WIDTH;
            }
            if (x + 1 > COL || y + 1 > ROW) {
                initGame();
            } else if (getDot(x, y).getStatus() == Dot.STATUS_OFF) {
                getDot(x, y).setStatus(Dot.STATUS_ON);
                move();
            }
            redraw();
        }
        return true;
    }
}
