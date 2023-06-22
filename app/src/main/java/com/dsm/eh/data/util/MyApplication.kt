package com.dsm.eh.data.util

import android.app.Application

//토큰 값 저장하기 위해
class MyApplication : Application() {
    companion object{
        lateinit var prefs: PreferenceUtil
    }

    override fun onCreate() {
        prefs = PreferenceUtil(applicationContext)
        super.onCreate()
    }
}