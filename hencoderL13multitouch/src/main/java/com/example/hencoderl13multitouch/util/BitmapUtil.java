package com.example.hencoderl13multitouch.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.hencoderl13multitouch.R;

public class BitmapUtil {
    private static final int AVATAR_WIDTH = (int) ScreenUtil.dp2Px(250);
    public static  Bitmap getBitmap(Context context){
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 只获取bitmap的尺寸
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), R.drawable.p2157545428, options);
        // 根据尺寸计算缩放的倍数
        options.inSampleSize = calculateInSampleSize(options, AVATAR_WIDTH, AVATAR_WIDTH);
        options.inJustDecodeBounds = false;
        Log.i("WWS", "before width = " + options.outWidth + " height = " + options.outHeight + " inSampleSize = " + options.inSampleSize);
        // 根据缩放倍数获取bitmap
        return BitmapFactory.decodeResource(context.getResources(), R.drawable.p2157545428, options);
    }

    private static  int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
