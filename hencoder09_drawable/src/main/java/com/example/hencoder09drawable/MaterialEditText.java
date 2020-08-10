package com.example.hencoder09drawable;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

public class MaterialEditText extends AppCompatEditText {
    private static final int HINT_MARGIN_TOP = (int) ScreenUtil.dp2Px(22);
    private static final float HINT_MARGIN_LEFT = ScreenUtil.dp2Px(4);
    private static final int TEXT_MARGIN_TOP = (int) ScreenUtil.dp2Px(6);
    private Paint mPaint;
    /**
     * 全局是否使用悬浮提示，
     */
    private boolean mUseFloatingLabel = false;
    private ObjectAnimator mFloatingLabelShowAnimator;
    private ObjectAnimator mTextAlphaAnimator;
    /**
     * 提示文本的marginTop值
     */
    private int mTextMarginTop = 0;
    private int mTextAlpha = 255;
    /**
     * padding的固定值---Background的Rect范围
     */
    private Rect mPaddingRect;

    public MaterialEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        mPaddingRect = new Rect();
        getBackground().getPadding(mPaddingRect);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MaterialEditText);
        mUseFloatingLabel = ta.getBoolean(R.styleable.MaterialEditText_met_use_label, true);
        ta.recycle();


        if (mUseFloatingLabel) {
            useFloatingLabel();
        }else{
            unuseFloatingLabel();
        }
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(ScreenUtil.dp2Px(14));
        mPaint.setColor(Color.GRAY);


        mFloatingLabelShowAnimator = ObjectAnimator.ofInt(this, "textMarginTop", 0, TEXT_MARGIN_TOP);
        mTextAlphaAnimator = ObjectAnimator.ofInt(this, "textAlpha", 255, 0);


        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!mUseFloatingLabel){
                    return;
                }
                if (TextUtils.isEmpty(s)){
                    unuseFloatingLabel();
                    mFloatingLabelShowAnimator.reverse();
                }else{
                    useFloatingLabel();
                    mFloatingLabelShowAnimator.start();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void useFloatingLabel() {
        setPadding(mPaddingRect.left, mPaddingRect.top + HINT_MARGIN_TOP, mPaddingRect.right, mPaddingRect.bottom);
    }

    private void unuseFloatingLabel(){
        setPadding(mPaddingRect.left, mPaddingRect.top, mPaddingRect.right, mPaddingRect.bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!mUseFloatingLabel){
            return;
        }
        // 有文案时显示悬浮提示
        if (!TextUtils.isEmpty(getText().toString())){
            canvas.drawText(getHint().toString(), HINT_MARGIN_LEFT, mTextMarginTop + mPaint.getFontSpacing(), mPaint);
        }
    }

    public int getTextMarginTop() {
        return mTextMarginTop;
    }

    public void setTextMarginTop(int mHintMarginTop) {
        this.mTextMarginTop = mHintMarginTop;
        invalidate();
    }

    public int getTextAlpha() {
        return mTextAlpha;
    }

    public void setTextAlpha(int textAlpha) {
        mTextAlpha = textAlpha;
        mPaint.setAlpha(textAlpha);
        invalidate();
    }

    public void setUseFloatingLabel(boolean useFloatingLabel) {
        if (mUseFloatingLabel == useFloatingLabel){
            return;
        }
        mUseFloatingLabel = useFloatingLabel;
        if (mUseFloatingLabel){
            useFloatingLabel();
        }else{
            unuseFloatingLabel();
        }
    }
}
