package com.think.sample_recorder.view;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.think.sample_recorder.R;

/**
 * Created by XiuWuZhuo on 2016/1/20.
 * Emial:nimdanoob@163.com
 */
public class AudioRecorderButton extends Button implements AudioManager.AudioStateListener {

    public static final int STATE_NORMAL = 1;
    public static final int STATE_RECORDING = 2;
    public static final int STATE_WANT_TO_CANCEL = 3;

    private static final int DISTANCE_Y_CANCEL = 50;

    private float mTime;

    //是否触发了LongClick 用来判断是否触发了Audio  需要释放资源
    private boolean mReady;

    private final AudioManager mAudiManager;

    private int mCurState = STATE_NORMAL;

    private boolean isRecording = false;

    private DialogManager mDialogManager;



    public AudioRecorderButton(Context context) {
        this(context, null);
    }

    public AudioRecorderButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AudioRecorderButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mDialogManager = new DialogManager(getContext());

        String dir = Environment.getExternalStorageDirectory()+"/app_recorder_audios";
        mAudiManager = AudioManager.getInstance(dir);
        mAudiManager.setOnAudioStateListener(this);
        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mReady  = true;
                mAudiManager.prepareAudio();
                return false;
            }
        });
    }

    /**
     * 录音完成后的回调
     */
    public interface AudioFinishRecorderListener{
        void onFinish(float seconds,String filePath);
    }

    private AudioFinishRecorderListener mListener;
    public void setAudioFinishRecorderListener(AudioFinishRecorderListener listener){
        this.mListener = listener;
    }

    private static final int MSG_AUDIO_PREPARED = 0x001;
    private static final int MSG_VOICE_CHANGED = 0x002;
    private static final int MSG_DIALOG_DISMISS = 0x003;
    /**
     * 获取音量大小
     */
    private Runnable mGetVoiceLevelRunnable = new Runnable() {
        @Override
        public void run() {
            while (isRecording){
                try {
                    Thread.sleep(100);
                    mTime+=0.1f;
                    mHandler.sendEmptyMessage(MSG_VOICE_CHANGED);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_AUDIO_PREPARED:
                    //todo 真正显示应该在 audio end prepared 之后
                    mDialogManager.showRecordingDialog();
                    isRecording = true;
                    //获取音量，开线程
                    new Thread(mGetVoiceLevelRunnable).start();
                    break;
                case MSG_VOICE_CHANGED:
                    mDialogManager.updateVoiceLevel(mAudiManager.getVoiceLevel(7));
                    break;
                case MSG_DIALOG_DISMISS:
                    mDialogManager.dismissDialog();
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    //如果完全准备好的 回调的方法
    @Override
    public void wellPrepared() {
        mHandler.sendEmptyMessage(MSG_AUDIO_PREPARED);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                changeState(STATE_RECORDING);
                //todo to test
//                isRecording = true;

                break;
            case MotionEvent.ACTION_MOVE:
                if (isRecording){//已经在录制
                    if (wantToCancel(x, y)){
                        Log.w("logger","want to cancel");
                        changeState(STATE_WANT_TO_CANCEL);
                    } else {
                        Log.w("logger","STATE_RECORDING");
                        changeState(STATE_RECORDING);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:

                if (!mReady){
                    reset();
                    return super.onTouchEvent(event);
                }

                //如果已经调用prepared 了，但是有可能抬起时 如果已经prepared
                if (!isRecording || mTime <0.6){
                    mDialogManager.tooShort();
                    //消除文件夹
                    mAudiManager.cancel();
                    //显示了取消1.3秒后 才去关闭
                    mHandler.sendEmptyMessageDelayed(MSG_DIALOG_DISMISS,1300);
                }

                if (mCurState == STATE_RECORDING){//it si normal
                    mDialogManager.dismissDialog();
                    //release
                    mAudiManager.release();
                    if (mListener != null){
                        mListener.onFinish(mTime,mAudiManager.getCurrentFilePath());
                    }

                    //callback to Activity

                } else if (mCurState == STATE_WANT_TO_CANCEL){
                    //cancel
                    mDialogManager.dismissDialog();
                    mAudiManager.cancel();
                }
                //重置一些标志位
                reset();
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 恢复状态及标识为
     */
    private void reset() {
        isRecording = false;
        mReady =false;
        changeState(STATE_NORMAL);
        mTime = 0;
    }


    private void changeState(int state) {
        if (mCurState != state){
            mCurState = state;
            switch (state){
                case STATE_NORMAL:
                    setBackgroundResource(R.drawable.btn_recorder_normal);
                    setText(R.string.str_recorder_normal);
//                    mDialogManager.showRecordingDialog();
                    break;
                case STATE_RECORDING:
                    setBackgroundResource(R.drawable.btn_recording);
                    setText(R.string.str_recorder_recording);
                    if (isRecording){
                        mDialogManager.recording();
                    }
                    break;
                case STATE_WANT_TO_CANCEL:
                    setBackgroundResource(R.drawable.btn_recording);
                    setText(R.string.str_recorder_want_cancel);
                    mDialogManager.wantToCancel();
                    break;
                default:
                    break;
            }
        }
    }
    private boolean wantToCancel(int x, int y) {
        if (x<0 || x >getWidth()){
            return true;
        }
        //超出上下 distance
        if (y<-DISTANCE_Y_CANCEL || y> getHeight()+DISTANCE_Y_CANCEL){
            return true;
        }
        return false;
    }


}
