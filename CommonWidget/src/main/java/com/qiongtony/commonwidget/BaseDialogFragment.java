package com.qiongtony.commonwidget;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.DialogFragment;

/**
 * 执行的顺序：
 * onCreate->onCreateDialog->setupDialog(设置style）->onCreateView
 * @param <T>
 */
public abstract class BaseDialogFragment<T extends ViewDataBinding> extends DialogFragment {
    protected T mBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置要在onCreateDialog调用前
        setStyle(STYLE_NO_TITLE , getTheme());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        // 调DialogFragment的cancel而不是Dialog的，因为在onActivityCreated还会根据DialogFragment的cancelable值再给Dialog射一次
        setCancelable(true);
        return mBinding.getRoot();
    }


    @Override
    public void onStart() {
        super.onStart();
        // 在onStart的时候初始化Dialog的尺寸，在onCreateView时虽然Dialog被创建了，但是设置Window的Attribute会不生效
        Window window = getDialog().getWindow();
        if (window != null) {
            //设置 window 的背景色为透明色.
            window.setBackgroundDrawableResource(R.color.transparent);
            WindowManager.LayoutParams attributes = window.getAttributes();
            //在这里我们可以设置 DialogFragment 弹窗的位置
            attributes.gravity = getGravity();

            //为什么这里还要设置 window 的宽高呢？
            //因为如果 xml 里面的宽高为 match_parent 的时候，window 的宽高也必须是 MATCH_PARENT，否则无法生效！
            attributes.width = ViewGroup.LayoutParams.MATCH_PARENT;
            attributes.height = ViewGroup.LayoutParams.WRAP_CONTENT;

            //设置 DialogFragment 的进出动画
//            attributes.windowAnimations = R.style.DialogAnimation;
            window.setAttributes(attributes);

        }
    }

    public abstract int getLayoutId();

    // Dialog展示的位置
    protected int getGravity(){
        return Gravity.CENTER;
    }
}
