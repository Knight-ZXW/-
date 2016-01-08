package com.think.imageloader;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.think.imageloader.bean.FolderBean;
import com.think.imageloader.util.ImageLoader;

import java.util.List;

/**
 * Created by ${XiuWuZhuo} on 2016/1/8.
 * Emial:nimdanoob@163.com
 */
public class ListImageDirPopupWindow extends PopupWindow{
    private int mWidth;
    private int mHeight;
    private View mConvertView;
    private ListView mListView;

    private List<FolderBean> mDatas;
    public interface  onDirSelectedListener{
        void onSelected(FolderBean folderBean);
    }

    public onDirSelectedListener mListener;

    public void setOnDirSelectedListener(onDirSelectedListener listener){
        this.mListener = listener;
    }

    public ListImageDirPopupWindow(Context context,List<FolderBean> datas){
        calWidthAndHeight(context);

        mConvertView = LayoutInflater.from(context).inflate(R.layout.popup_main,null);
        mDatas = datas;
        setContentView(mConvertView);
        setWidth(mWidth);
        setHeight(mHeight);

        setFocusable(true);
        setTouchable(true);

        //点击外部使其消失
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());

        setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //在屏幕外，dismiss
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    dismiss();
                    return true;
                }
                return false;
            }
        });

        initViews(context);
        initEvent();
    }



    private void initViews(Context context) {
        mListView = (ListView) mConvertView.findViewById(R.id.id_list_dir);
        mListView.setAdapter(new ListDirAdapter(context,mDatas));
    }

    private void initEvent() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mListener != null){
                    mListener.onSelected(mDatas.get(position));
                }
            }
        });
    }

    private void calWidthAndHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        mWidth = (int) (outMetrics.widthPixels);
        mHeight = (int) (outMetrics.heightPixels * 0.7);
    }

    private class ListDirAdapter extends ArrayAdapter<FolderBean>{

        private LayoutInflater mInflater;
        private List<FolderBean> mDatas;
        public ListDirAdapter(Context context, List<FolderBean> objects) {
            super(context, 0, objects);
            mInflater = LayoutInflater.from(context);
            mDatas = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null){
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.item_popup_main,parent,false);
                holder.mImg = (ImageView) convertView.findViewById(R.id.id_dir_item_image);
                holder.mDirName = (TextView) convertView.findViewById(R.id.id_dir_item_name);
                holder.mDirCount = (TextView)convertView.findViewById(R.id.id_dir_item_count);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            FolderBean bean = getItem(position);
            holder.mImg.setImageResource(R.mipmap.pictures_no);
            ImageLoader.getInstance().loadImage(bean.getFirstImgPath(),holder.mImg);
            holder.mDirName.setText(bean.getName());
            holder.mDirCount.setText(bean.getCount()+"");
            return convertView;
        }

        private class ViewHolder{
            ImageView mImg;
            TextView mDirName;
            TextView mDirCount;
        }
    }
}
