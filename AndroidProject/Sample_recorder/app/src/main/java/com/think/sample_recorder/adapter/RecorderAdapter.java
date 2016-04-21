package com.think.sample_recorder.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.think.sample_recorder.MainActivity.Recorder;
import com.think.sample_recorder.R;

import java.util.List;
/**
 * Created by XiuWuZhuo on 2016/1/21.
 * Emial:nimdanoob@163.com
 */
public class RecorderAdapter extends ArrayAdapter<Recorder>{
    private List<Recorder> mDatas;
    private Context mContext;

    private LayoutInflater mLayoutInflater;

    private int mMinItemWidth;
    private int mMaxItemWidth;

    public RecorderAdapter(Context context, List<Recorder> recorders) {
        super(context, -1,recorders);
        mContext = context;
        mDatas = recorders;

        mLayoutInflater = LayoutInflater.from(context);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);

        mMaxItemWidth = (int) (outMetrics.widthPixels * 0.7);
        mMinItemWidth = (int) (outMetrics.widthPixels * 0.1);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.item_recorder,parent,false);
            holder = new ViewHolder();
            holder.seconds = (TextView) convertView.findViewById(R.id.id_recorder_time);
            holder.length = (View) convertView.findViewById(R.id.id_recorder_length);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.seconds.setText(Math.round(getItem(position).getTime())+"\"");
        ViewGroup.LayoutParams lp = holder.length.getLayoutParams();
        lp.width = (int) (mMinItemWidth + (mMaxItemWidth/60f)* getItem(position).getTime());

        return convertView;

    }

    public static class ViewHolder{
        TextView seconds;
        View length;
    }
}
