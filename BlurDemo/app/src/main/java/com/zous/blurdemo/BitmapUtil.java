package com.zous.blurdemo;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

/**
 * Created by XiuWuZhuo on 2016/2/25.
 * Emial:nimdanoob@163.com
 */
public class BitmapUtil {
    private BitmapUtil() {
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Bitmap renderBlurBitmap(Bitmap bitmap, Context context){
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        RenderScript rs = RenderScript.create(context);

        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

        Allocation allIn = Allocation.createFromBitmap(rs,bitmap);
        Allocation allOut = Allocation.createFromBitmap(rs,outBitmap);

        blurScript.setRadius(25.f);

        blurScript.setInput(allIn);
        blurScript.forEach(allOut);

        allOut.copyTo(outBitmap);
        rs.destroy();
        return outBitmap;
    }
}
