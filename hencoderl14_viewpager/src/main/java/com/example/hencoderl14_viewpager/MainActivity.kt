package com.example.hencoderl14_viewpager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent  = Intent(this, ViewPagerActivity::class.java)
        startActivity(intent)
    }
}
