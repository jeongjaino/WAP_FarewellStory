package com.example.loveapp

import android.app.Application
import android.content.Context

class MyApplication : Application() {
    init{
        instance = this
    }
    companion object {
        lateinit var instance: MyApplication
        fun ApplicationContext() : Context {
            return instance.applicationContext
        }
    }
}