package com.example.my_mobile_game

import android.app.Application
import com.example.my_mobile_game.utils.SignalManager

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        SignalManager.init(this)
    }
}