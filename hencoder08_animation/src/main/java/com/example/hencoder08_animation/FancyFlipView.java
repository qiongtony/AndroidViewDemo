package com.example.hencoder08_animation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.hencoder08_animation.util.BitmapUtil;
import com.example.hencoder08_animation.util.ScreenUtil;

public class FancyFlipView extends View {
    public FancyFlipView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private Paint paint;

    private Bitmap avatarBitmap;
    private Camera camera;
    /**
     * 上半部分，Camera的旋转角度
     */
    private float mCameraTopFlip = 0;
    /**
     * 下半部分，Camera的旋转角度
     */
    private float mCameraBottomFlip = 45;
    /**
     * 图片的旋转角度
     */
    private float mRotateFlip = 20;

    {
        avatarBitmap = BitmapUtil.getBitmap(getContext());

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        camera = new Camera();
        // 设置投影Z轴的位置，首先为Z轴的负值，负值越小投影位置越远，投影后的图形越小
        // 单位为英寸，1英寸=72像素，需要使用转换为dp的值
        camera.setLocation(0, 0, - ScreenUtil.dp2Px(2));
        // 旋转45度
//        camera.rotateX(65);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();

        // 上半部分
        // apply后以当前canvas的画布位置进行投影
        canvas.translate(100 + avatarBitmap.getWidth() / 2, 100 + avatarBitmap.getHeight() / 2);
        canvas.rotate(-mRotateFlip);
        camera.save();
        camera.rotateX(mCameraTopFlip);
        camera.applyToCanvas(canvas);
        // 这里是由于旋转后的范围最多是宽高的根号2，所以裁剪宽高两倍区域保证内容存在
        canvas.clipRect(- avatarBitmap.getWidth() , - avatarBitmap.getHeight(), avatarBitmap.getWidth(), 0);
        canvas.rotate(mRotateFlip);
        canvas.translate(-(100 + avatarBitmap.getWidth() / 2), -(100 + avatarBitmap.getHeight() / 2));
        canvas.drawBitmap(avatarBitmap, 100, 100, paint);
        canvas.restore();
        camera.restore();

        canvas.save();
        canvas.translate(100 + avatarBitmap.getWidth() / 2, 100 + avatarBitmap.getHeight() / 2);
        canvas.rotate(-mRotateFlip);
        camera.save();
        camera.rotateX(mCameraBottomFlip);
        camera.applyToCanvas(canvas);
        // 这里是由于旋转的宽高最多不大于根号2
        canvas.clipRect(- avatarBitmap.getWidth() , 0, avatarBitmap.getWidth(), avatarBitmap.getHeight());
        canvas.rotate(mRotateFlip);
        canvas.translate(-(100 + avatarBitmap.getWidth() / 2), -(100 + avatarBitmap.getHeight() / 2));
        // 在旋转后的Canvas里绘制图片
        canvas.drawBitmap(avatarBitmap, 100, 100, paint);
        canvas.restore();
        camera.restore();
    }

    public float getCameraTopFlip() {
        return mCameraTopFlip;
    }

    public void setCameraTopFlip(float mCameraTopFlip) {
        this.mCameraTopFlip = mCameraTopFlip;
        invalidate();
    }

    public float getCameraBottomFlip() {
        return mCameraBottomFlip;
    }

    public void setCameraBottomFlip(float mCameraBottomFlip) {
        this.mCameraBottomFlip = mCameraBottomFlip;
        invalidate();
    }

    public float getRotateFlip() {
        return mRotateFlip;
    }

    public void setRotateFlip(float mRotateFlip) {
        this.mRotateFlip = mRotateFlip;
        invalidate();
    }
}
