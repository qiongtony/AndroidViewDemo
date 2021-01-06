package com.example.androidviewdemo.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidviewdemo.R
import com.example.androidviewdemo.adapter.PageAdapter
import com.example.androidviewdemo.repo.PageRepo

class NewMainActivity : AppCompatActivity() {
    lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_main)
        recycler = findViewById(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler.adapter = PageAdapter(this, PageRepo.list)
    }
}