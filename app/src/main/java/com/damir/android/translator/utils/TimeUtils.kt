package com.damir.android.translator.utils

class TimeUtils {

    companion object {

        fun millsToMin(mills: Int): String {
            val sec: Double = mills/1000.0
            val min = sec.toInt()/60
            val minResidue = (sec%60).toInt()

            return if(minResidue < 10) {
                "$min:0$minResidue"
            }else {
                "$min:$minResidue"
            }
        }
    }
}