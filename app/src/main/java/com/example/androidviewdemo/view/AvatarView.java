package com.example.androidviewdemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.androidviewdemo.R;
import com.example.androidviewdemo.util.ScreenUtil;

/**
 * 使用clipPath裁剪绘制范围，然后绘制图片，绘制完成回退离屏缓存；
 */
public class AvatarView extends View {
    private static final int WIDTH = (int) ScreenUtil.dp2Px(100);
    private Paint paint;
    private Path path;
    private Bitmap bitmap;
    public AvatarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        path = new Path();
        bitmap = getBitmap();
        Log.i("WWS", "after width = " + bitmap.getWidth() + " height = " + bitmap.getHeight());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        path.addCircle(w / 2, h / 2, WIDTH / 2, Path.Direction.CW);
    }

    /**
     * 获取指定大小的bitmap
     * @return
     */
    public Bitmap getBitmap(){
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 只获取bitmap的尺寸
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.p2157545428, options);
        // 根据尺寸计算缩放的倍数
        options.inSampleSize = calculateInSampleSize(options, WIDTH, WIDTH);
        options.inJustDecodeBounds = false;
        Log.i("WWS", "before width = " + options.outWidth + " height = " + options.outHeight + " inSampleSize = " + options.inSampleSize);
        // 根据缩放倍数获取bitmap
        return BitmapFactory.decodeResource(getResources(), R.drawable.p2157545428, options);
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();

        canvas.clipPath(path);
        canvas.drawBitmap(bitmap, getWidth() / 2 - WIDTH, getHeight() / 2 - WIDTH, paint);
        canvas.restore();
    }
}
