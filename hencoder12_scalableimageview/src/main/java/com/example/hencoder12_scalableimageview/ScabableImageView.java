package com.example.hencoder12_scalableimageview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.hencoder12_scalableimageview.util.BitmapUtil;

/**
 * 可缩放（与宽度贴边，与高度贴边），可滑动的ImageView
 */
public class ScabableImageView extends View implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
    private Paint paint;
    private Bitmap mBitmap;
    // 绘制时的偏移，保证居中
    private int mOffsetX;
    private int mOffsetY;
    // 宽度贴边
    private float mSmallScale;
    // 高度贴边
    private float mBigScale;
    private boolean mBig;
    private GestureDetector mGestureDetector;
    // x轴滑动的距离，这里单独给出这个值是因为初始图片已经居中了，此时移动距离为0，而左右最大距离的绝对值是一样的
    private float mDistanceX;
    // y轴滑动的距离
    private float mDistanceY;
    // 当前的缩放比例
    private float mCurrentScale;

    private ObjectAnimator mScaleAnimator;
    public ScabableImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitmap = BitmapUtil.getBitmap(getContext());
        mGestureDetector = new GestureDetector(getContext(), this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mSmallScale = getWidth() * 1f / mBitmap.getWidth();
        mBigScale = getHeight() * 1f/ mBitmap.getHeight();

        mScaleAnimator = ObjectAnimator.ofFloat(this, "currentScale", mSmallScale, mBigScale);

        if (mBig){
            mCurrentScale = mBigScale;
        }else{
            mCurrentScale = mSmallScale;
        }
        updateOffset(w, h);
    }

    private void updateOffset(int w, int h) {
        mOffsetX = (int) ((w - mBitmap.getWidth() * mCurrentScale) / 2f);
        mOffsetY = (int) ((h - mBitmap.getHeight() * mCurrentScale) / 2f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mOffsetX + mDistanceX, mOffsetY + mDistanceY);
        canvas.scale(mCurrentScale, mCurrentScale);
        canvas.drawBitmap(mBitmap, 0, 0, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    // 要接收事件此处必须返回true
    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        // 起点-终点，所以应该减去distance
        mDistanceX -= distanceX;
        mDistanceY -= distanceY;
        if (mBig){
            int absoluteX = (int) ((mBitmap.getWidth() * mBigScale - getWidth()) / 2f);
            int result = (int) Math.max(mDistanceX, -absoluteX);
            mDistanceX = Math.min(result, absoluteX);
//            mDistanceY = Math.min(0f, mDistanceY);
        }
        invalidate();
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        mBig = !mBig;
        mDistanceX = mDistanceY = 0;
        if (mBig){
            mScaleAnimator.start();
        }else{
            mScaleAnimator.reverse();
        }
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    private float getCurrentScale() {
        return mCurrentScale;
    }

    private void setCurrentScale(float currentScale) {
        mCurrentScale = currentScale;
        updateOffset(getWidth(), getHeight());
        invalidate();
    }
}
