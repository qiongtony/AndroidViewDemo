package com.example.androidviewdemo.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.androidviewdemo.R
import com.example.androidviewdemo.view.CustomBottomSheetDialog
import com.example.androidviewdemo.view.CustomDialogFragment

class BottomSheetDialogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_sheet_dialog)
        findViewById<Button>(R.id.btn_show).setOnClickListener {
            show()
        }

        findViewById<Button>(R.id.btn_show_dialog).setOnClickListener {
            showDialog()
        }

        show()
    }

    private fun showDialog(){
        CustomDialogFragment().show(supportFragmentManager, "custom")
    }

    private fun show() {
        CustomBottomSheetDialog().show(supportFragmentManager, "custom")
    }
}