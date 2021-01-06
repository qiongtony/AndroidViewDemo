package com.example.androidviewdemo.view

import com.example.androidviewdemo.R
import com.example.androidviewdemo.databinding.ContentDialogBottomSheetBinding
import com.qiongtony.commonwidget.BaseBindingBtsDialogFragment

class CustomBottomSheetDialog() : BaseBindingBtsDialogFragment<ContentDialogBottomSheetBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.content_dialog_bottom_sheet
    }

    override fun init() {

    }
}