package com.think.imageloader.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.LruCache;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by think on 2016/1/7.
 * emial :nimdanoob@163.com
 */
public class ImageLoader {
    private static ImageLoader mInstance;
    /**
     * 图片缓存
     */
    private LruCache<String,Bitmap> mLruCache;
    /**
     * 线程池
     */
    private ExecutorService mThreadPool;
    private static final int DEFAULT_THREAD_COUNT =1;
    /**
     * 队列的调度方式
     */
    private Type mType = Type.LIFO;
    /**
     * 任务队列
     */
    private LinkedList<Runnable> mTaskQueue;

    /**
     * 后台轮询线程
     */
    private Thread mPoolThread;
    private Handler mPoolThreadHandler;
    /**
     * UI线程中的Handler
     */
    private Handler mUIHandler;

    /**
     * 解决异步线程访问变量 先后顺序 可能造成的空指针问题
     */
    private Semaphore mSemaphorePoolThreadHandler = new Semaphore(0);

    /**
     * 实现线程池空闲时，才将任务发送到线程池。
     */
    private Semaphore mSemaphoreThreadPool;


    public enum Type{
        FIFO,LIFO;
    }


    private ImageLoader(int threadCount,Type type){
        init(threadCount,type);
    }

    /**
     * 初始化操作
     * @param threadCount
     * @param type
     */
    private void init(int threadCount, Type type) {
        //后台轮询线程
        mPoolThread = new Thread(){
            @Override
            public void run() {
                Looper.prepare();
                //其他线程调用将任务发送到mTaskQueue,该线程去取消息（任务执行，当任务信号量不够时，相当于）
                //该线程会阻塞，只到有了新的信号量，
                mPoolThreadHandler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        //线程池执行执行任务。
                        Runnable task = getTask();
                        mThreadPool.execute(task);
                        try {
                            mSemaphoreThreadPool.acquire();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                //释放一个信号量
                mSemaphorePoolThreadHandler.release();
                Looper.loop();
            }
        };

        mPoolThread.start();

        //获取应用的最大可用内存
        int maxMemory = (int)Runtime.getRuntime().maxMemory();
        int cacheMemory = maxMemory/8;

        mLruCache = new LruCache<String,Bitmap>(cacheMemory){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes()*value.getHeight();
            }
        };
        //创建线程池
        mThreadPool = Executors.newFixedThreadPool(threadCount);
        mTaskQueue = new LinkedList<Runnable>();
        mType = type;

        //初始化信号量
        mSemaphoreThreadPool = new Semaphore(threadCount);

    }

    public static ImageLoader getInstance(){
        if (mInstance == null){
            synchronized (ImageLoader.class){
                if (mInstance == null){
                    mInstance = new ImageLoader(4,Type.LIFO);
                }
            }
        }
        return mInstance;
    }
    public static ImageLoader getInstance(int threadCount,Type type){
        if (mInstance == null){
            synchronized (ImageLoader.class){
                if (mInstance == null){
                    mInstance = new ImageLoader(threadCount,type);
                }
            }
        }
        return mInstance;
    }

    /**f
     * 根据path为imageView设置图片
     * @param path
     * @param imageView
     */
    public void loadImage(final String path, final ImageView imageView)
    {
        imageView.setTag(path);
        if (mUIHandler == null){
            mUIHandler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    //获取得到的图片，为imageView回调设置图片
                    ImageBeanHolder holder = (ImageBeanHolder) msg.obj;
                    Bitmap bm = holder.bitmap;
                    ImageView imageView= holder.imageView;
                    String path = holder.path;
                    //将path 与getTag存储路径进行比较
                    if (imageView.getTag().toString().equals(path)){
                        imageView.setImageBitmap(bm);
                    }
                }
            };
        }

        Bitmap bm = getBitmapFromLruCache(path);
        if (bm != null){
            refreashBitmap(bm,path,imageView);
        } else {//加入到任务队列
            addTask(new Runnable() {
                @Override
                public void run() {
                    //加载图片
                    //图片的压缩
                    //1.获得图片需要显示的大小
                    ImageSize imageViewSize = getImageViewSize(imageView);
                    //2压缩图片到内存
                    Bitmap bitmap = decodeSampledBitmapFromPath(path,imageViewSize.width,imageViewSize.height);
                    //3图片加入的缓存中
                    addBitmapToLruCache(path,bitmap);

                    refreashBitmap(bitmap, path, imageView);

                    //run方法执行完成，即线程池的一个任务执行完了，
                    //任务执行完了，释放一个信号量
                    mSemaphoreThreadPool.release();
                }
            });
        }
    }

    /**
     * 反射获取 属性值
     * @return
     */
    private static int getImageFiledValue(Object object,String fieldName){
        int value = 0;
        Field field = null;
        try {
            field = ImageView.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            int fieldVale = field.getInt(object);
            if (fieldVale >0 && fieldVale <Integer.MAX_VALUE){
                value = fieldVale;
            }
        } catch (Exception e) {
            //如果没有设置这个的话，或出错，捕获它，返回0
            Log.e("logger","file:"+fieldName+" get caught exception");
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 给发送消息给UIHandler,刷新ImageView
     * @param bitmap
     * @param path
     * @param imageView
     */
    private void refreashBitmap(Bitmap bitmap, String path, ImageView imageView) {
        Message message = Message.obtain();
        ImageBeanHolder holder= new ImageBeanHolder();
        holder.bitmap = bitmap;
        holder.path = path;
        holder.imageView = imageView;

        message.obj = holder;
        mUIHandler.sendMessage(message);
    }

    /**
     * 根据图片需要显示的宽和高 对图片进行压缩
     * @param path
     * @param width
     * @param height
     * @return
     */
    private Bitmap decodeSampledBitmapFromPath(String path, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        //只是赋值options
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = caculateInsampleSize(options,width,height);

        //使用获取到的InsampleSize 再次解析图片
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        return bitmap;
    }

    /**
     * 将图片加入到缓存
     * @param path
     * @param bitmap
     */
    private void addBitmapToLruCache(String path, Bitmap bitmap) {
        if (getBitmapFromLruCache(path) == null){
            if (bitmap != null)
                mLruCache.put(path,bitmap);
        }
    }

    /**
     * 根据需求的宽和搞 和图片实际的宽和高计算SampleSize
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private int caculateInsampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int width = options.outWidth;
        int height = options.outHeight;

        int inSampleSize = 1;
        //缩放代码有问题
        if (width >reqWidth || height >reqHeight){

            int widthRadio = Math.round(width*1.0f/reqWidth);
            int heightRadio = Math.round(height*1.0f/reqHeight);
//            //根据需求来。
            inSampleSize = Math.max(widthRadio,heightRadio);
        }

        return inSampleSize;
    }

    /**
     * 根据imageView获取适当的 缩放的宽高
     * @param imageView
     * @return
     */

    private ImageSize getImageViewSize(ImageView imageView) {
        ImageSize imageSize = new ImageSize();
        DisplayMetrics displayMetrics = imageView.getContext().getResources().getDisplayMetrics();

        ViewGroup.LayoutParams lp = imageView.getLayoutParams();
        int width = imageView.getWidth(); //可能还没成像，此时取不到宽高
        if (width <=0){//wrapContent(-1) or matchParent(-2)
            width = lp.width;//
        }
        if (width <= 0){
            width = getImageFiledValue(imageSize,"mMaxWidth");
        }
        if (width <= 0){// 取成屏幕的值
            width = displayMetrics.widthPixels;
        }

        int height = imageView.getHeight();
        if (height <=0){//wrapContent(-1) or matchParent(-2)
            height = lp.height;//
        }
        if (height <= 0){
            height = getImageFiledValue(imageSize,"mMaxHeight"); //通过反射解决 只有api 16才能调用的方法getMaxHeight
        }
        if (height <= 0){// 取成屏幕的值
            height = displayMetrics.heightPixels;
        }
        imageSize.height =height;
        imageSize.width = width;
        return imageSize;
    }

    private synchronized void addTask(Runnable runnable){
        mTaskQueue.add(runnable);
        //发送通知
        //为了解决异步可能造成的空指针问题
        try{
            if (mPoolThreadHandler == null) {
                //得到才有值，否则阻塞
                mSemaphorePoolThreadHandler.acquire();
            }
        } catch (InterruptedException e){
            //log
        }
        mPoolThreadHandler.sendEmptyMessage(0x0);
    }

    /**
     * 从任务队列中取出任务
     * @return
     */
    private Runnable getTask(){

        if (mType == Type.FIFO && mTaskQueue.size()!=0){
            return mTaskQueue.removeFirst();
        } else if (mType == Type.LIFO && mTaskQueue.size()!=0){
            return mTaskQueue.removeLast();
        }
        return null;
    }

    /**
     * 根据path在缓存中获取bitmap
     * @param path
     * @return
     */
    private Bitmap getBitmapFromLruCache(String path) {

        return mLruCache.get(path);
    }

    private class ImageBeanHolder{
        Bitmap bitmap;
        ImageView imageView;
        String path;
    }

    private class ImageSize{
        int width;
        int height;
    }

}
