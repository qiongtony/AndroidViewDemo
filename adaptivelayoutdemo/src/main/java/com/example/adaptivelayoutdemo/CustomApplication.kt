package com.example.adaptivelayoutdemo

import android.app.Application
import android.content.Context

class CustomApplication :Application(){

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object{
        lateinit var context :Context
    }
}