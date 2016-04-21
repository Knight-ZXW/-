package com.think.sample_recorder.view;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.think.sample_recorder.R;

/**
 * Created by XiuWuZhuo on 2016/1/20.
 * Emial:nimdanoob@163.com
 */
public class DialogManager {

    private Dialog mDialog;

    private ImageView mIcon;
    private ImageView mVoice;
    private TextView mLabel;
    private Context mContext;

    //Context 必须是 Activity,所以 这个类不能用单例来编写
    public DialogManager(Context context) {
        this.mContext = context;
    }

    public void showRecordingDialog(){
        mDialog = new Dialog(mContext, R.style.ThemeAudioDialog);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.dialog_recorder,null);
        mDialog.setContentView(view);

        mIcon = (ImageView) mDialog.findViewById(R.id.id_recorder_dialog_icon);
        Log.w("logger","mIcon =");
        mVoice = (ImageView) mDialog.findViewById(R.id.id_recorder_dialog_voice);
        mLabel = (TextView) mDialog.findViewById(R.id.id_recorder_dialog_label);

        //为什么 不去用DialogFragment,这个Dialog比较简单。so ->
        mDialog.show();

    }

    public void recording(){
        if (mDialog != null && mDialog.isShowing()){
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.VISIBLE);
            mLabel  .setVisibility(View.VISIBLE);

            mIcon.setImageResource(R.mipmap.recorder);
            mLabel.setText("手指上滑 取消发送");

        }
    }
    public void wantToCancel(){
        mIcon.setVisibility(View.VISIBLE);
        mVoice.setVisibility(View.GONE);
        mLabel  .setVisibility(View.VISIBLE);

        mIcon.setImageResource(R.mipmap.cancel);
        mLabel.setText("松开手指 取消发送");
    }

    public void tooShort(){
        if (mDialog != null && mDialog.isShowing()){
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.GONE);
            mLabel  .setVisibility(View.VISIBLE);

            mIcon.setImageResource(R.mipmap.voice_to_short);
            mLabel.setText("录音时间过短");

        }
    }

    public void dismissDialog(){
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
            mDialog = null;
        }
    }

    /**
     * 通过level去更新voice 上的图片
     * @param level
     */
    public void updateVoiceLevel(int level){
        if (mDialog != null && mDialog.isShowing()){
//            mIcon.setVisibility(View.VISIBLE);
//            mVoice.setVisibility(View.VISIBLE);
//            mLabel  .setVisibility(View.VISIBLE);
            int resId = mContext.getResources().getIdentifier("v"+level,"mipmap",mContext.getPackageName());
            mVoice.setImageResource(resId);
        }
    }
}
