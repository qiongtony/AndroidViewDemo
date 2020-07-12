package com.example.androidviewdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;


public class CameraView extends View {
    private Paint paint;

    private Bitmap avatarBitmap;
    private Camera camera;

    public CameraView(Context context, @NonNull AttributeSet attrs){
        super(context, attrs);
    }

    {
        avatarBitmap = BitmapUtil.getBitmap(getContext());

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        camera = new Camera();
        // 旋转45度
        camera.setLocation(0, 0, - ScreenUtil.dp2Px(2));
        camera.rotateX(45);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();

        canvas.translate(100 + avatarBitmap.getWidth() / 2, 100 + avatarBitmap.getHeight() / 2);
        // 上半部分
        // apply后以当前canvas的画布位置进行投影
        canvas.save();
        canvas.translate(-(100 + avatarBitmap.getWidth() / 2), -(100 + avatarBitmap.getHeight() / 2));
        canvas.clipRect(100 , 100, 100 + avatarBitmap.getWidth(), 100 + avatarBitmap.getHeight() / 2);
        canvas.drawBitmap(avatarBitmap, 100, 100, paint);

        canvas.restore();
        camera.applyToCanvas(canvas);
        canvas.translate(-(100 + avatarBitmap.getWidth() / 2), -(100 + avatarBitmap.getHeight() / 2));
        canvas.clipRect(100 , 100 + avatarBitmap.getHeight() / 2, 100 + avatarBitmap.getWidth(), 100 + avatarBitmap.getHeight());
        canvas.drawBitmap(avatarBitmap, 100, 100, paint);
        canvas.restore();
    }
}
