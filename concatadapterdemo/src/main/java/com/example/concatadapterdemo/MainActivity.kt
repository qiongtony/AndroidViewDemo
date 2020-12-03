package com.example.concatadapterdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_open_concat_page.setOnClickListener {
            openActivity(ConcatAdapterActivity::class.java)
        }
        btn_open_vlayout_page.setOnClickListener {
            openActivity(VLayoutActivity::class.java)
        }
    }

    fun openActivity(className: Class<*>){
        val intent = Intent(this, className)
        startActivity(intent)
    }
}