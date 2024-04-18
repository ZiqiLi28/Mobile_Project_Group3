package com.example.accounting.db

data class ChartItemBean(
    var sImageId: Int = 0,
    var type: String = "",
    var ratio: Float = 0f,
    var totalMoney: Float = 0f
)