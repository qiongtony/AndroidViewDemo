package com.example.androidviewdemo.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;


import com.example.androidviewdemo.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;


/**
 * Created by Vance on 2020/07/14.
 */

public class BulletView extends FrameLayout {

    private HashMap<Animator, View> mSendByImmediately;
	private ArrayList<HashMap<Animator, View>> mRows;
	private LinkedList<TextCache> mTextCache = new LinkedList<>();

	private int mPreRowHeight;
	private boolean[] mIsRowOpen;
	private int[] mRowHeight;
	private int mVSpace, mHSpace;

	private float mSpeed = 0.108f;
	private boolean overlapping = false;
	private float mTextSize = 14f;
	private int mTextColor = Color.BLACK;
	private Drawable mTextBackground;

	private boolean rowHeightNeedUpdate = false;
	private boolean pause = false, close = false;

	private OnSendTextListener listener;

	private int mRowCount = 0;
	
	private boolean isLoopMode;
	
	public BulletView(Context context) {
		super(context);
		init(null);
	}

	public BulletView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}

	public BulletView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(attrs);
	}

	private void init(AttributeSet attrs) {
		if (attrs != null) {
			TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.BulletView);
			mRowCount = a.getInt(R.styleable.BulletView_rows, 0);
			mPreRowHeight = a.getDimensionPixelOffset(R.styleable.BulletView_preRowHeight, 0);

			mVSpace = a.getDimensionPixelOffset(R.styleable.BulletView_vSpace, mVSpace);
			mHSpace = a.getDimensionPixelOffset(R.styleable.BulletView_hSpace, mHSpace);

			mSpeed = a.getFloat(R.styleable.BulletView_scrollSpeed, mSpeed);
			overlapping = a.getBoolean(R.styleable.BulletView_overlapping, false);

			mTextBackground = a.getDrawable(R.styleable.BulletView_innerBackground);
			mTextColor = a.getColor(R.styleable.BulletView_innerTextColor, Color.BLACK);
			mTextSize = a.getDimension(R.styleable.BulletView_innerTextSize, 14f);
			a.recycle();
		}

		initRows();
	}

	private void initRows() {
		if (mRowCount == 0) {
			return;
		}
		mRows = new ArrayList<>(mRowCount);
		for (int i = 0; i < mRowCount; ++i) {
			mRows.add(new HashMap<>());
		}

		mIsRowOpen = new boolean[mRowCount];
		Arrays.fill(mIsRowOpen, true);

		mRowHeight = new int[mRowCount];
		if (mPreRowHeight > 0) {
			Arrays.fill(mRowHeight, mPreRowHeight);
		}
	}

	public void setOnSendTextListener(OnSendTextListener listener) {
		this.listener = listener;
	}

	public void setRowHeight(int... rowHeights) {
		if (rowHeights == null || rowHeights.length < mRowHeight.length) {
			return;
		}

		System.arraycopy(rowHeights, 0, mRowHeight, 0, mRowHeight.length);
		rowHeightNeedUpdate = false;
	}

	public void setRowHeightNeedUpdate(boolean isNeed) {
		rowHeightNeedUpdate = isNeed;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		if (getMeasuredHeight() == 0 && mPreRowHeight > 0 && mRowCount > 0) {
//			int height = mPreRowHeight * mRowCount + mVSpace * (mRowHeight.length - 1) + getPaddingTop()
//					+ getPaddingBottom();
//			heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
//		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if (mPreRowHeight > 0 && mRowCount <= 0 && getMeasuredHeight() > 0) {
			mRowCount = (getMeasuredHeight()-getPaddingTop()-getPaddingBottom()) / (mPreRowHeight+mVSpace);
            initRows();
		}
	}
	
	public TextView addText(CharSequence text) {
		return addText(-1, text);
	}

	public TextView addText(int index, CharSequence text) {
		if (index >= mIsRowOpen.length || close) {
			return null;
		}

		TextView tv = createText(text);
		if (pause) {
			mTextCache.offer(new TextCache(index, tv));
			return tv;
		}

		if (index < 0) {
			for (int i = 0; i < mIsRowOpen.length; ++i) {
				if (mIsRowOpen[i]) {
					sendText(tv, i);
					return tv;
				}
			}
		} else if (mIsRowOpen[index] || overlapping) {
			sendText(tv, index);
			return tv;
		}

		mTextCache.offer(new TextCache(index, tv));
		return tv;
	}

	public boolean addText(View tv) {
		return addText(-1, tv);
	}

	public boolean addText(int rowIndex, View tv) {
        if (rowIndex >= mIsRowOpen.length || close) {
			return false;
		}

		if (pause) {
			mTextCache.offer(new TextCache(rowIndex, tv));
			return false;
		}

		if (rowIndex < 0) {
			for (int i = 0; i < mIsRowOpen.length; ++i) {
				if (mIsRowOpen[i]) {
					sendText(tv, i);
					return true;
				}
			}
		} else if (mIsRowOpen[rowIndex] || overlapping) {
			sendText(tv, rowIndex);
			return true;
		}
		mTextCache.offer(new TextCache(rowIndex, tv));
		return false;
	}
	
	public boolean sendViewImmediately(View view, int gravity, int topMargin, int duration){
        if (listener != null) {
            view = listener.beforeTextSend(view, -99);
        }
        if (view == null || view.getParent() != null) {
            return false;
        }
        
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        final int viewWidth = view.getMeasuredWidth();
        lp.width = viewWidth;
        lp.gravity = gravity;
        lp.topMargin = topMargin;
        view.setLayoutParams(lp);
        addView(view);

        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "translationX", getWidth(), -viewWidth * 2);
        if(mSendByImmediately == null){
            mSendByImmediately = new HashMap<>();
        }
        mSendByImmediately.put(anim, view);

		if (duration > 0) {
			anim.setDuration(duration);
		} else {
			int realDuration = (int) ((getWindowAttachCount() + viewWidth) / mSpeed);
			anim.setDuration(realDuration);
		}
        anim.setInterpolator(new LinearInterpolator());
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animator) {
                if(mSendByImmediately == null || mSendByImmediately.isEmpty()){
                    return;
                }
                View v = mSendByImmediately.remove(animator);
                removeView(v);
                if (listener != null) {
                    listener.onTextRemove(v, -99);
                }
            }
        });
        anim.start();
        return true;
    }

	public TextView createText(CharSequence text) {
		TextView textView = new TextView(getContext());
		textView.setMaxLines(1);
		textView.setTextColor(mTextColor);
		textView.setBackground(mTextBackground);
		textView.setTextSize(mTextSize);
		textView.setText(text);
		return textView;
	}

	private void sendTextThrow(View textView, final int rowIndex) {
		if (listener != null) {
			textView = listener.beforeTextSend(textView, rowIndex);
		}
		if (textView == null || (textView.getParent() != null && textView.getParent() != this)) {
			return;
		}

        int textWidth;
		if(textView.getParent() == this){
            textWidth = textView.getMeasuredWidth();
        }else{
            LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            textView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            textWidth = textView.getMeasuredWidth();
            lp.width = textWidth;
            if (mPreRowHeight > 0) {
                lp.height = mPreRowHeight;
            } else if (rowHeightNeedUpdate || mRowHeight[rowIndex] <= 0) {
                lp.height = textView.getMeasuredHeight();
                mRowHeight[rowIndex] = lp.height;
            }
            textView.setLayoutParams(lp);
            addView(textView);
        }
        
		textView.setTranslationY(getRowY(rowIndex, textView));
		
		ObjectAnimator anim = ObjectAnimator.ofFloat(textView, "translationX", getWidth(), -textWidth);
		mRows.get(rowIndex).put(anim, textView);

		mIsRowOpen[rowIndex] = false;
		if (rowIndex == mIsRowOpen.length - 1 && overlapping) {
			Arrays.fill(mIsRowOpen, true);
		}

		int realDuration = (int) ((getWidth() + textWidth) / mSpeed);
		anim.setDuration(realDuration);
		anim.setInterpolator(new LinearInterpolator());
		anim.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animator) {
                HashMap<Animator, View> map = mRows.get(rowIndex);
                View v = map.remove(animator);
                if(isLoopMode){
                    addText(v);
                }else{
                    removeView(v);
                }
                if (overlapping && map.isEmpty()) {
                    mIsRowOpen[rowIndex] = true;
                }
                if (listener != null) {
                    listener.onTextRemove(v, rowIndex);
                }
			}
		});
		if (!overlapping) {
			anim.addUpdateListener(animation -> {
                if (!mIsRowOpen[rowIndex]) {
                    if ((Float) animation.getAnimatedValue() < (getWidth() - textWidth - mHSpace)) {
                        mIsRowOpen[rowIndex] = true;
                        animation.removeAllUpdateListeners();
                        sendCacheIfNeed();
                    }
                }
            });
		}
		anim.start();
	}

	private void sendText(View textView, int rowIndex) {
		try {
			sendTextThrow(textView, rowIndex);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}

	private void sendCacheIfNeed() {
		if (pause) {
			return;
		}
		if(close){
            mTextCache.clear();
            return;
        }
		if (!mTextCache.isEmpty()) {
			TextCache cache = mTextCache.peek();
			if (cache == null) {
				mTextCache.poll();
				return;
			}
			if (cache.index < 0) {
				for (int i = 0; i < mIsRowOpen.length; ++i) {
					if (mIsRowOpen[i]) {
						sendText(cache.text, i);
						mTextCache.poll();
					}
				}
			} else if (mIsRowOpen[cache.index]) {
				sendText(cache.text, cache.index);
				mTextCache.poll();
			}
		}
	}

	public LinkedList<TextCache> getTextCaches() {
		return mTextCache;
	}

	private int getRowY(int index, View view) {
		int y = 0;
		for (int i = 0; i < index; ++i) {
			y += mRowHeight[i] + mVSpace;
		}
		int offset = mPreRowHeight > 0 ? mPreRowHeight : view.getMeasuredHeight();
		y += Math.max(mRowHeight[index] - offset, 0);
		return y;
	}

	public BulletView setTextSize(float mTextSize) {
		this.mTextSize = mTextSize;
		return this;
	}

	public BulletView setTextColor(int mTextColor) {
		this.mTextColor = mTextColor;
		return this;
	}

	public BulletView setTextBackground(Drawable mTextBackground) {
		this.mTextBackground = mTextBackground;
		return this;
	}

	public BulletView setOverlapping(boolean overlapping) {
		this.overlapping = overlapping;
		return this;
	}
	
	public void setLoopMode(boolean loopMode){
        isLoopMode = loopMode;
    }
    
	public void pause() {
		pause = true;
		if (mRows != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			for (HashMap<Animator, View> q : mRows) {
				for (Animator animator : q.keySet()) {
					animator.pause();
				}
			}
		}
	}

	public void resume() {
		pause = false;
		if (mRows != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			for (HashMap<Animator, View> q : mRows) {
				for (Animator animator : q.keySet()) {
					animator.resume();
				}
			}
		}
		sendCacheIfNeed();
	}

	@Override
	protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
		super.onVisibilityChanged(changedView, visibility);
		if (visibility == VISIBLE) {
			resume();
		} else {
			pause();
		}
	}

    public void close() {
		close = true;
	}

	public void open() {
		close = false;
	}
    
    public boolean isPause() {
        return pause;
    }
    
    public boolean isClose() {
        return close;
    }
    
	public void gc() {
        close();
        clear();
        mRows.clear();
	}
	
	public void clear(){
        if (mTextCache != null) {
            mTextCache.clear();
        }
        if (mRows != null) {
            for (HashMap<Animator, View> q : mRows) {
                for (Animator animator : q.keySet()) {
                    animator.removeAllListeners();
                    animator.cancel();
                }
                q.clear();
            }
        }
        if(mSendByImmediately != null){
            for (Animator animator : mSendByImmediately.keySet()) {
                animator.removeAllListeners();
                animator.cancel();
            }
            mSendByImmediately.clear();
        }
        
        Arrays.fill(mIsRowOpen, true);
        removeAllViews();
    }

	public interface OnSendTextListener {
		default View beforeTextSend(View v, int rowIndex){ return v;}

		void onTextRemove(View v, int rowIndex);
	}

	public static class TextCache {
		public int index;
		public View text;

		public TextCache(int idx, View text) {
			this.index = idx;
			this.text = text;
		}
	}
}
