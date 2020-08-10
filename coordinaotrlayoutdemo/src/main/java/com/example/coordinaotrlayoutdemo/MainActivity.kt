package com.example.coordinaotrlayoutdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.btn_with_toolbar).setOnClickListener( View.OnClickListener {
            startActivity(Intent(this, ToolbarWithCoordinatorLayoutActivity::class.java))
        })
        findViewById<View>(R.id.btn_with_toolbar_snap).setOnClickListener( View.OnClickListener {
            startActivity(Intent(this, ToolbarSnapActivity::class.java))
        })
        findViewById<View>(R.id.btn_with_view_pager).setOnClickListener( View.OnClickListener {
            startActivity(Intent(this, CoordinatorLayoutAndViewPagerActivity::class.java))
        })
    }
}
