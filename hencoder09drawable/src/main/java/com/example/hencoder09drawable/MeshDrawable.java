package com.example.hencoder09drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 网眼的drawable
 */
public class MeshDrawable extends Drawable {
    private Paint paint;
    private static final int INTERNAL = 30;

    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(12);
    }
    @Override
    public void draw(@NonNull Canvas canvas) {
        for (int i = getBounds().left; i < getBounds().right; i+= INTERNAL){
            canvas.drawLine(i, getBounds().top, i, getBounds().bottom, paint);
        }

        for (int i = getBounds().top; i < getBounds().bottom; i+= INTERNAL){
            canvas.drawLine(getBounds().left, i, getBounds().right, i, paint);
        }
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        // 判断透明度：不透明、半透明，还是全透明
        return getAlpha() == 0 ? PixelFormat.OPAQUE : (getAlpha() != 255 ? PixelFormat.TRANSLUCENT : PixelFormat.TRANSPARENT);
    }
}
