package com.example.androidviewdemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Editable;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.androidviewdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现类似微博的带收起/展开的TextView
 * 支持自定义展开/收起的文案、字体颜色
 * 支持话题内容高亮，可开启或关闭
 * 展开/收起点击事件
 */
public class MyExpandableTextView extends AppCompatTextView {
    private static final String TAG = MyExpandableTextView.class.getSimpleName();
    // 默认,话题文本高亮颜色
    private static final int FOREGROUND_COLOR = Color.parseColor("#4488ff");
    private static final int MAX_LINES = 2;
    private static final String EXPAND_TEXT = "..点击展开";
    private static final String PACK_UP_TEXT = "收起";

    // 启动话题模式：高亮话题文案，话题可点击
    private boolean openTopicMode = true;
    // 话题文本高亮颜色
    private int mForegroundColor = FOREGROUND_COLOR;

    private int maxLines;

    private OnTopicClickListener mClickListener;

    private List<String> mTObjectsList = new ArrayList<>();// object集合

    private int contentWidth = 0;

    /**
     * 展开文案
     */
    private String expandText;
    /**
     * 收起文案
     */
    private String packUpText;

    /**
     * 展开文案的color
     */
    private @ColorInt int expandTextColor;
    /**
     * 收起文案的color
     */
    private @ColorInt int packUpTextColor;
    private String message;

    private OnExpandAndPackUpListener mExpandAndPackUpListener;

    public MyExpandableTextView(@NonNull Context context) {
        super(context);
    }

    public MyExpandableTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MyExpandableTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.MyExpandableTextView);

        maxLines = ta.getInt(R.styleable.MyExpandableTextView_metv_maxLines, MAX_LINES);

        if (ta.hasValue(R.styleable.MyExpandableTextView_expand_text)) {
            expandText = ta.getString(R.styleable.MyExpandableTextView_expand_text);
        } else {
            expandText = EXPAND_TEXT;
        }
        if (ta.hasValue(R.styleable.MyExpandableTextView_expand_text_color)) {
            expandTextColor = ta.getColor(R.styleable.MyExpandableTextView_expand_text_color, Color.WHITE);
        }

        if (ta.hasValue(R.styleable.MyExpandableTextView_pack_up_text)) {
            packUpText = ta.getString(R.styleable.MyExpandableTextView_pack_up_text);
        } else {
            packUpText = PACK_UP_TEXT;
        }
        packUpTextColor = ta.getColor(R.styleable.MyExpandableTextView_pack_up_text_color, Color.WHITE);

        openTopicMode = ta.getBoolean(R.styleable.MyExpandableTextView_open_topic_mode, true);
        ta.recycle();

        setMovementMethod(LinkMovementMethod.getInstance());

        /*
         * 监听软键盘删除按钮(本打算使用setOnKeyListener,但发现在华为手机上Del按钮的keyCode与其他手机不一样,故用此)
         * 1.光标在话题后面,将整个话题内容删除
         * 2.光标在普通文字后面,删除一个字符
         */
        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public synchronized void afterTextChanged(Editable editablede) {
                if (editablede == null){
                    return;
                }
                if (!openTopicMode){
                    return;
                }
                refreshEditTextUI(editablede.toString());
            }
        });
    }

    public void setMessage(String message) {
        this.message = message;
        initAction();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (contentWidth != w - getPaddingLeft() - getPaddingRight()) {
            contentWidth = w - getPaddingLeft() - getPaddingRight();
            initAction();
        }
    }

    private void initAction() {
        initAction(false);
    }

    /**
     * 初始化
     * @param expand        显示什么样式的UI，true：展开样式；false：收起样式
     */
    public void initAction(boolean expand) {
        if (contentWidth == 0 || TextUtils.isEmpty(message)) {
            return;
        }
        if (expand) {
            expand();
        } else {
            packUp();
        }
    }

    /**
     * 展开样式
     */
    private void expand() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        if (getStaticLayout(message).getLineCount() > maxLines) {
            ssb.append(message);
            ssb.append(getEndString(false));
            initClick(ssb, false);
            setText(ssb);
        } else {
            setText(message);
        }
    }

    /**
     * 收起样式
     */
    private void packUp() {
        if (getStaticLayout(message).getLineCount() > maxLines) {
            SpannableStringBuilder ssb = getSpannableString();

            initClick(ssb, true);
            setText(ssb);
        } else {
            setText(message);
        }
    }

    /**
     *
     * @param ssb
     * @param open  点击后是什么状态，true为展开，false为收起
     */
    private void initClick(SpannableStringBuilder ssb, boolean open) {
        int startIndex = ssb.length() - getEndString(open).length();
        ssb.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                if (mExpandAndPackUpListener == null){
                    return;
                }
                if (open){
                    mExpandAndPackUpListener.clickExpand();
                }else{
                    mExpandAndPackUpListener.clickPackUp();
                }
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(open ? packUpTextColor : expandTextColor);
                ds.setUnderlineText(false);
            }
        }, startIndex, ssb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private StaticLayout getStaticLayout(CharSequence text) {
        return new StaticLayout(text, getPaint(), contentWidth, Layout.Alignment.ALIGN_NORMAL, getLineSpacingMultiplier(),
                getLineSpacingExtra(), getIncludeFontPadding());
    }

    private SpannableStringBuilder getSpannableString() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        String endString = getEndString(true);
        int offset = (int) (contentWidth - getPaint().measureText(endString));
        int endIndex = getStaticLayout(message).getOffsetForHorizontal(maxLines - 1, offset);

        ssb.append(message.subSequence(0, endIndex));
        ssb.append(endString, new ForegroundColorSpan(expandTextColor), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        while (getStaticLayout(ssb).getLineCount() > maxLines) {
            ssb = ssb.delete(endIndex - 1, endIndex);
            endIndex -= 1;
        }
        return ssb;
    }

    private String getEndString(boolean expand) {
        return expand ? expandText : packUpText;
    }

    /**
     * <b>Description:</b>
     * <p>
     * TEditText内容修改之后刷新UI
     * </p>
     * Author: Xiasem, Date: 17-7-17, Email: xiasem@163.com <br/>
     * Version: 1.0 <br/>
     *
     * @param content 输入框内容
     */
    private void refreshEditTextUI(String content) {
        /*
         * 重新设置span
         */
        Editable editable = getEditableText();
        if (editable == null) {
            return;
        }
        int textLength = editable.length();
        int formIndex = 0;
        List<Integer> indexs = new ArrayList<>();
        while (formIndex <= textLength) {
            formIndex = content.indexOf("#", formIndex);
            if (formIndex == -1) {
                break;
            }
            indexs.add(formIndex);
            formIndex++;
        }
        for (int i = 0; i < indexs.size(); ) {
            if (i + 1 < indexs.size()) {
                String substring = content.substring(indexs.get(i), indexs.get(i + 1) + 1);
                if (TextUtils.isEmpty(substring)) continue;

                if (substring.length() <= 22 && substring.length() > 2) {
                    i = i + 2;
                    if (!mTObjectsList.contains(substring)) {
                        mTObjectsList.add(substring);
                    }
                } else {
                    i = i + 1;
                }
            } else {
                i = indexs.size();
            }
        }
        /*
         * 内容变化时操作:
         * 1.查找匹配所有话题内容
         * 2.设置话题内容特殊颜色
         */
        if (mTObjectsList.size() == 0)
            return;

        if (TextUtils.isEmpty(content)) {
            mTObjectsList.clear();
            return;
        }


        int findPosition = 0;
        for (int i = 0; i < mTObjectsList.size(); i++) {
            //   final String object = mTObjectsList.get(i);
            // 文本
            String objectText = mTObjectsList.get(i);
            while (findPosition <= textLength) {
                // 获取文本开始下标
                findPosition = content.indexOf(objectText, findPosition);
                if (findPosition != -1) {
                    // 设置话题内容前景色高亮
                    ForegroundColorSpan colorSpan = new ForegroundColorSpan(mForegroundColor);
                    editable.setSpan(colorSpan, findPosition, findPosition + objectText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    int finalFindPosition = findPosition;
                    editable.setSpan(new ClickableSpan() {
                        @Override
                        public void onClick(@NonNull View widget) {
                            if (mClickListener != null) {
                                mClickListener.onClick(content.substring(finalFindPosition, finalFindPosition + objectText.length()));
                            }
                        }

                        @Override
                        public void updateDrawState(@NonNull TextPaint ds) {
                        }
                    }, findPosition, findPosition + objectText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    findPosition += objectText.length();
                } else {
                    break;
                }
            }
        }

    }

    public void setTopicClickListener(OnTopicClickListener clickListener) {
        mClickListener = clickListener;
    }

    public void setExpandAndPackUpListener(OnExpandAndPackUpListener expandAndPackUpListener) {
        mExpandAndPackUpListener = expandAndPackUpListener;
    }

    public interface OnTopicClickListener {
        void onClick(String topic);
    }

    public interface OnExpandAndPackUpListener{
        /**
         * 点击展开
         */
        void clickExpand();

        /**
         * 点击收起
         */
        void clickPackUp();
    }
}
