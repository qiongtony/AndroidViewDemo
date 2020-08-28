package com.example.coordinaotrlayoutdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

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
        findViewById<Button>(R.id.btn_custom_item_decoration).setOnClickListener {
            ScrollToPosActivity.start(this);
         }

        findViewById<Button>(R.id.btn_custom_simple_behavior).setOnClickListener {
            startActivity(Intent(this, SimpleBehaviorActivity::class.java))
        }

        findViewById<Button>(R.id.btn_nestscroll).setOnClickListener {
            startActivity(Intent(this, NestedScrollViewActivity::class.java))
        }
        findViewById<Button>(R.id.btn_space).setOnClickListener {
            startActivity(Intent(this, SpaceRvActivity::class.java))
        }
    }
}
