package com.example.androidviewdemo.view;

import com.example.androidviewdemo.R;
import com.example.androidviewdemo.databinding.ContentDialogBinding;
import com.qiongtony.commonwidget.BaseDialogFragment;

public class CustomDialogFragment extends BaseDialogFragment<ContentDialogBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.content_dialog;
    }
}
