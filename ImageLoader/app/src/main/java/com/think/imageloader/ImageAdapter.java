package com.think.imageloader;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.think.imageloader.util.ImageLoader;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ${XiuWuZhuo} on 2016/1/8.
 * Emial:nimdanoob@163.com
 */
public class ImageAdapter extends BaseAdapter {

    private static Set<String> mSeletedImg = new HashSet<>();

    private String mDirPath;
    private List<String> mImgPath;
    private LayoutInflater mInflater;

    public ImageAdapter(Context context,List<String> mDatas,String dirPath) {
        this.mDirPath = dirPath;
        this.mImgPath = mDatas;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (mImgPath!=null)
            return  mImgPath.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return mImgPath.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.item_girdview,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.mImg = (ImageView) convertView.findViewById(R.id.id_item_image);
            viewHolder.mSelect = (ImageButton) convertView.findViewById(R.id.id_item_select);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Log.w("logger","getView");
//        //重置状态
        viewHolder.mImg.setImageResource(R.mipmap.pictures_no);
//        viewHolder.mSelect.setImageResource(R.mipmap.picture_unselected);
//        viewHolder.mImg.setColorFilter(null);
        final String  filepath = mDirPath + "/" + mImgPath.get(position);

        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //已经被选择
                if (mSeletedImg.contains(filepath)) {
                    mSeletedImg.remove(filepath);
                    finalViewHolder.mImg.setColorFilter(null);
                    finalViewHolder.mSelect.setImageResource(R.mipmap.picture_unselected);
                } else {
                    mSeletedImg.add(filepath);
                    finalViewHolder.mImg.setColorFilter(Color.parseColor("#77000000"));
                    finalViewHolder.mSelect.setImageResource(R.mipmap.pictures_selected);
                }
            }
        });


        ImageLoader.getInstance(3, ImageLoader.Type.FIFO).loadImage(mDirPath+"/"+mImgPath.get(position),viewHolder.mImg);
        return convertView;
    }

    private class ViewHolder{
        ImageView mImg;
        ImageButton mSelect;
    }

}
