package com.example.accounting.frag_chart

import android.graphics.Color
import android.view.View
import com.example.accounting.db.DBManager
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import kotlin.math.ceil

class IncomeChartFragment : BaseChartFragment() {
    var kind = 1

    override fun onResume() {
        super.onResume()
        loadData(year, month, kind)
    }

    override fun setAxisData(year: Int, month: Int) {
        val sets = mutableListOf<IBarDataSet>()
        val list = DBManager.getSumMoneyOneDayInMonth(year, month, kind)
        if (list.isEmpty()) {
            barChart.visibility = View.GONE
            chartTv.visibility = View.VISIBLE
        } else {
            barChart.visibility = View.VISIBLE
            chartTv.visibility = View.GONE
            val barEntries1 = mutableListOf<BarEntry>()
            for (i in 0 until 31) {
                val entry = BarEntry(i.toFloat(), 0.0f)
                barEntries1.add(entry)
            }
            for (i in list.indices) {
                val itemBean = list[i]
                val day = itemBean.day
                val xIndex = day - 1
                val barEntry = barEntries1[xIndex]
                barEntry.y = itemBean.summoney
            }
            val barDataSet1 = BarDataSet(barEntries1, "")
            barDataSet1.valueTextColor = Color.BLACK
            barDataSet1.valueTextSize = 8f
            barDataSet1.color = Color.parseColor("#006400")

            barDataSet1.valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return if (value == 0f) "" else value.toString()
                }
            }

            sets.add(barDataSet1)
            val barData = BarData(sets)
            barData.barWidth = 0.2f
            barChart.data = barData
        }
    }

    override fun setYAxis(year: Int, month: Int) {
        val maxMoney = DBManager.getMaxMoneyOneDayInMonth(year, month, kind)
        val max = ceil(maxMoney.toDouble()).toFloat()
        val yAxisRight = barChart.axisRight
        yAxisRight.axisMaximum = max
        yAxisRight.axisMinimum = 0f
        yAxisRight.isEnabled = false
        val yAxisLeft = barChart.axisLeft
        yAxisLeft.axisMaximum = max
        yAxisLeft.axisMinimum = 0f
        yAxisLeft.isEnabled = false
        val legend = barChart.legend
        legend.isEnabled = false
    }

    override fun setDate(year: Int, month: Int) {
        super.setDate(year, month)
        loadData(year, month, kind)
    }
}
