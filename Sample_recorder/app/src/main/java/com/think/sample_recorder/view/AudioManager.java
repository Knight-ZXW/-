package com.think.sample_recorder.view;

import android.media.MediaRecorder;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by XiuWuZhuo on 2016/1/20.
 * Emial:nimdanoob@163.com
 */
public class AudioManager {
    private MediaRecorder mMediaRecorder;
    private String mDir;
    private String mCurrentFilePath;

    private boolean isPrepared;

    private static AudioManager mInstance;

    private AudioManager(String dir){
        mDir = dir;

    }

    public String getCurrentFilePath() {
        return mCurrentFilePath;
    }

    /**
     * 准备完毕的回调接口
     */
    public interface AudioStateListener{
        void wellPrepared();
    }

    public AudioStateListener mListener;

    public void setOnAudioStateListener(AudioStateListener listener){
        this.mListener = listener;
    }

    public static AudioManager getInstance(String dir){
        if (mInstance == null){
            synchronized (AudioManager.class){
                if (mInstance == null){
                    mInstance = new AudioManager(dir);
                }
            }
        }
        return mInstance;
    }

    public void prepareAudio(){

        try {
            //原因状态
            isPrepared = false;

            File dir = new File(mDir);
            if (!dir.exists()){
                dir.mkdirs();
            }
            String fileName = generateFileName();
            File file = new File(dir,fileName);

            mCurrentFilePath = file.getAbsolutePath();

            mMediaRecorder = new MediaRecorder();
            //设置输出文件  注意方法的调用顺序
            mMediaRecorder.setOutputFile(file.getAbsolutePath());
            // 设置MediaRecorder 的音频源为麦克风
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            //设置音频的格式
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
            //设置音频的编码为AMR
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            mMediaRecorder.prepare();
            mMediaRecorder.start();
            //准备过程 结束
            isPrepared = true;
            if (mListener != null){
                mListener.wellPrepared();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 随机生成录音文件的名称
     * @return
     */
    private String generateFileName() {
        return UUID.randomUUID().toString()+".amr";
    }

    public int getVoiceLevel(int maxLevel){
        if (isPrepared && mMediaRecorder != null){

            try {
                //mMediaRecorder.getMaxAmplitude() 范围在 1-32767
                return maxLevel * mMediaRecorder.getMaxAmplitude() / 32768 + 1;
            } catch (IllegalStateException e){

            }
        }
        return 1;
    }

    public void release(){
        mMediaRecorder.stop();
        mMediaRecorder.release();
        mMediaRecorder = null;
    }

    public void cancel(){
        //cancel 的时候需要删除创建的文件
        mMediaRecorder.release();
        if (mCurrentFilePath != null){
            File file = new File(mCurrentFilePath);
            file.delete();
            mCurrentFilePath = null;
        }

    }

}
