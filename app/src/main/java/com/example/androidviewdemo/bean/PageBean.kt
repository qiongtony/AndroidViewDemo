package com.example.androidviewdemo.bean

import kotlin.reflect.KClass

data class PageBean<T>(val content :String, val clazz: Class<T>) {
}