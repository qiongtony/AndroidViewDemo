package com.example.hencoder12_scalableimageview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.OverScroller;

import androidx.annotation.Nullable;

import com.example.hencoder12_scalableimageview.util.BitmapUtil;

/**
 * 双击可缩放（与宽度贴边，与高度贴边），可滑动（贴边）的ImageView
 *
 * 缩放居中：移动已经算出了图片要展示的起点，之后缩放，显示
 * 惯性滑动使用：OverScroller。理由：1、启动速度快，Scroller启动速度慢；2、可实现超出位置的效果；
 *
 * OverScroll方法解析：
 * 主要包含四对值：起点，速度，极值（用于防止滑过头）
 * 物理模型：
 * 相当于从某点开始立即刹车，此时会依据惯性滑动一段距离，但滑动不会超过设置的边界
 * 这里的值都是相对的，所以只要起点和极值的坐标系相同即可
 * fling(int startX, int startY, int velocityX, int velocityY,
 *             int minX, int maxX, int minY, int maxY)
 */
public class ScabableImageView extends View implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, Runnable {
    private static final float OVER_SCALL = 1.5F;
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

    private OverScroller mScroller;

    private ObjectAnimator mScaleAnimator;
    private int mAbsoluteMaxScrollerX;
    private int mAbsoluteMaxScrollerY;

    private ScaleGestureDetector mScaleGestureDetector;
    private MyScaleListener mScaleListener = new MyScaleListener();

    public ScabableImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitmap = BitmapUtil.getBitmap(getContext());
        mGestureDetector = new GestureDetector(getContext(), this);
        mScroller = new OverScroller(getContext());

        mScaleGestureDetector = new ScaleGestureDetector(getContext(), mScaleListener);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mSmallScale = getWidth() * 1f / mBitmap.getWidth();
        mBigScale = getHeight() * 1f/ mBitmap.getHeight() * OVER_SCALL;

        mScaleAnimator = ObjectAnimator.ofFloat(this, "currentScale", mSmallScale, mBigScale);
        mAbsoluteMaxScrollerX = (int) ((mBitmap.getWidth() * mBigScale - getWidth()) / 2f);
        mAbsoluteMaxScrollerY = (int) ((mBitmap.getHeight() * mBigScale - getHeight()) / 2f);
        if (mBig){
            mCurrentScale = mBigScale;
        }else{
            mCurrentScale = mSmallScale;
        }
        updateOffset(w, h);
    }

    private void updateOffset(int w, int h) {
        mOffsetX = (int) ((w - mBitmap.getWidth() * mCurrentScale) / 2f + mBitmap.getWidth() * mCurrentScale/ 2);
        mOffsetY = (int) ((h - mBitmap.getHeight() * mCurrentScale) / 2f + mBitmap.getHeight() * mCurrentScale / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(mOffsetX + mDistanceX, mOffsetY + mDistanceY);
//        canvas.translate(mBitmap.getWidth() / 2f, mBitmap.getHeight() / 2f);
        canvas.scale(mCurrentScale, mCurrentScale);
        canvas.drawBitmap(mBitmap,  - mBitmap.getWidth() / 2, - mBitmap.getHeight() / 2, paint);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return
//                mScaleGestureDetector.onTouchEvent(event)
                mGestureDetector.onTouchEvent(event)
                ;
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
            int result = (int) Math.max(mDistanceX, -mAbsoluteMaxScrollerX);
            mDistanceX = Math.min(result, mAbsoluteMaxScrollerX);

            result = (int) Math.max(mDistanceY, -mAbsoluteMaxScrollerY);
            mDistanceY = Math.min(result, mAbsoluteMaxScrollerY);
        }
        invalidate();
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        mScroller.fling((int)mDistanceX, (int)mDistanceY, (int)velocityX, (int)velocityY, -mAbsoluteMaxScrollerX, mAbsoluteMaxScrollerX, 0, 0);
        postOnAnimation(this);
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    /**
     * 双击
     * 第二次触摸到屏幕就会触发
     * @param e
     * @return
     */
    @Override
    public boolean onDoubleTap(MotionEvent e) {
        mBig = !mBig;
//        mDistanceX = mDistanceY = 0;
        if (mBig){
            int x = (int) ((e.getX() - getWidth() / 2) * (mBigScale - mCurrentScale) / mCurrentScale);
            int y = (int) ((e.getY() - getHeight() / 2) * (mBigScale - mCurrentScale) / mCurrentScale);
            mDistanceX -= x;
            mDistanceY -= y;
            mScaleAnimator.start();
        }else{
            mDistanceX = mDistanceY = 0;
            mScaleAnimator.reverse();
        }
        return false;
    }

    /**
     * 第二次触摸按下后续事件都会触发
     * @param e
     * @return
     */
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

    @Override
    public void run() {
        // 计算此时的位置，并返回惯性滑动有无结束
        if (mScroller.computeScrollOffset()){
            // 获取当前滑动到x、y值
            // 刷新
            // 下一帧继续查看
            mDistanceX = mScroller.getCurrX();
            mDistanceY = mScroller.getCurrY();
            invalidate();
            postOnAnimation(this);
        }
    }

    class MyScaleListener implements ScaleGestureDetector.OnScaleGestureListener{
        private float mInitScale;

        /**
         * 缩放过程
         * @param detector  可以获取当前的缩放倍数
         * @return
         */
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mCurrentScale = mInitScale * detector.getScaleFactor();
            return false;
        }


        /**
         * 缩放前的准备
         * @param detector
         * @return
         */
        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            mInitScale = mCurrentScale;
            return false;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {

        }
    }
}
