package net.sarasarasa.redeemcode.base

import android.app.Application
import android.content.Context
import org.litepal.LitePal

class MyApplication : Application() {

    companion object {
        lateinit var instance: Context
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        LitePal.initialize(this)
    }
}

fun getApplicationContext() = MyApplication.instance