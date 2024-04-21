package com.example.accounting.utils

import java.math.BigDecimal
import java.math.RoundingMode

object FloatUtils {
    fun div(v1: Float, v2: Float): Float {
        val v3 = v1 / v2
        val b1 = BigDecimal(v3.toDouble())
        return b1.setScale(4, RoundingMode.HALF_UP).toFloat()
    }

    fun ratioToPercent(value: Float): String {
        // Correcting the conversion logic
        val v = value * 100
        val b1 = BigDecimal(v.toDouble())
        val v1 = b1.setScale(2, RoundingMode.HALF_UP).toFloat()
        return "$v1%"
    }
}
