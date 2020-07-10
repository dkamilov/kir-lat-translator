package com.damir.android.translator.utils

import android.os.Handler
import android.util.Log
import com.damir.android.translator.firebase.FirebaseDatabase
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.log

object SpentTimeCounter {

    private var handler: Handler? = null
    private var timerSpentTime = 0L
    private val spentTimeRunnable= object: Runnable {
        override fun run() {
            timerSpentTime += 1L
            if(timerSpentTime%60 == 0L)
                saveSpentTime()
            handler?.postDelayed(this, 1000)
        }
    }

    fun setHandler(handler: Handler) {
        this.handler = handler
    }

    fun start() {
        Log.d("DamirK", "start: handler = $handler")
        if(FirebaseAuth.getInstance().currentUser != null) {
            handler?.post(spentTimeRunnable)
            Log.d("DamirK", "timer started")
        }
    }

    fun stop() {
        handler?.removeCallbacks(spentTimeRunnable)
        Log.d("DamirK", "stop: handler = $handler")
        saveSpentTime()
    }

    private fun saveSpentTime() {
        FirebaseDatabase.updateSpentTime(timerSpentTime)
    }
}