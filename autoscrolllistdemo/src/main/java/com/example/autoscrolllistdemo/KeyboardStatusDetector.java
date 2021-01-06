package com.example.autoscrolllistdemo;


import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.fragment.app.Fragment;

public class KeyboardStatusDetector {
    private static final int SOFT_KEY_BOARD_MIN_HEIGHT = 100;
    private KeyboardVisibilityListener mVisibilityListener;

    boolean keyboardVisible = false;

    private ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener;

    private ViewTreeObserver mViewTreeObserver;

    public void registerFragment(Fragment f) {
        registerView(f.getView());
    }

    public void registerActivity(Activity a) {
        registerView(a.getWindow().getDecorView().findViewById(android.R.id.content));
    }

    public KeyboardStatusDetector registerView(final View v) {
        if (mGlobalLayoutListener != null){
            v.getViewTreeObserver().removeOnGlobalLayoutListener(mGlobalLayoutListener);
        }
        mGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                v.getWindowVisibleDisplayFrame(r);

                int heightDiff = v.getRootView().getHeight() - (r.bottom - r.top);
                if (heightDiff > SOFT_KEY_BOARD_MIN_HEIGHT) { // if more than 100 pixels, its probably a keyboard...
                    if (!keyboardVisible) {
                        keyboardVisible = true;
                        if (mVisibilityListener != null) {
                            mVisibilityListener.onVisibilityChanged(true);
                        }
                    }
                } else {
                    if (keyboardVisible) {
                        keyboardVisible = false;
                        if (mVisibilityListener != null) {
                            mVisibilityListener.onVisibilityChanged(false);
                        }
                    }
                }
            }
        };
        mViewTreeObserver = v.getViewTreeObserver();
        mViewTreeObserver.addOnGlobalLayoutListener(mGlobalLayoutListener);

        return this;
    }

    public KeyboardStatusDetector setVisibilityListener(KeyboardVisibilityListener listener) {
        mVisibilityListener = listener;
        return this;
    }

    public void unregisterListener(){
        mVisibilityListener = null;
        if (mViewTreeObserver != null){
            mViewTreeObserver.removeOnGlobalLayoutListener(mGlobalLayoutListener);
            mGlobalLayoutListener = null;
            mViewTreeObserver = null;
        }
    }


    public interface KeyboardVisibilityListener {
        void onVisibilityChanged(boolean keyboardVisible);
    }

}
