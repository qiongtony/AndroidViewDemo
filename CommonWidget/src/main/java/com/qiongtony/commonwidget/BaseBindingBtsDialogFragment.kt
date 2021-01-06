package com.qiongtony.commonwidget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBindingBtsDialogFragment<T : ViewDataBinding> : BottomSheetDialogFragment() {
    protected lateinit var mBinding: T
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate<T>(inflater, getLayoutId(), container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onStart() {
        super.onStart()
        // 处理虚拟按键，防止被遮挡
        dialog?.apply {
            setCancelable(canCanceledTouchOutside())
            setCanceledOnTouchOutside(canCanceledTouchOutside())

            window?.setBackgroundDrawableResource(R.color.transparent);
            val params = window?.attributes
            params?.width = WindowManager.LayoutParams.MATCH_PARENT
            params?.height = requireContext().resources.displayMetrics.heightPixels - if (Utils.hasSoftKeys(requireContext())) Utils.getNavigationBarHeight(requireContext()) else 0
            window?.attributes = params
        }
    }

    // 全屏且背景设成透明
    override fun getTheme(): Int {
        return R.style.ErbanBottomSheetDialog
    }

    abstract fun getLayoutId(): Int

    protected abstract fun init()

    fun canCanceledTouchOutside() = true
}