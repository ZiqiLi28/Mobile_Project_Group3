package com.example.accounting.frag_chart

import android.graphics.Color
import android.view.View
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.example.accounting.db.DBManager
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlin.math.ceil

class OutcomeChartFragment : BaseChartFragment() {
    var kind = 0

    override fun onResume() {
        super.onResume()
        loadData(year, month, kind)
    }

    override fun setAxisData(year: Int, month: Int) {
        val sets: MutableList<IBarDataSet> = mutableListOf()
        val list = DBManager.getSumMoneyOneDayInMonth(year, month, kind)
        if (list.isEmpty()) {
            barChart.visibility = View.GONE
            chartTv.visibility = View.VISIBLE
        } else {
            barChart.visibility = View.VISIBLE
            chartTv.visibility = View.GONE
            val barEntries1: MutableList<BarEntry> = mutableListOf()
            for (i in 0 until 31) {
                val entry = BarEntry(i.toFloat(), 0.0f)
                barEntries1.add(entry)
            }
            list.forEach { itemBean ->
                val day = itemBean.day
                val xIndex = day - 1
                val barEntry = barEntries1[xIndex]
                barEntry.y = itemBean.summoney
            }
            val barDataSet1 = BarDataSet(barEntries1, "").apply {
                valueTextColor = Color.BLACK
                valueTextSize = 8f
                color = Color.RED
                valueFormatter = CustomValueFormatter()
            }
            sets.add(barDataSet1 as IBarDataSet)
            val barData = BarData(sets).apply {
                barWidth = 0.2f
            }
            barChart.data = barData
        }
    }

    override fun setYAxis(year: Int, month: Int) {
        val maxMoney = DBManager.getMaxMoneyOneDayInMonth(year, month, kind)
        val max = ceil(maxMoney.toDouble()).toFloat()
        barChart.axisRight.apply {
            axisMaximum = max
            axisMinimum = 0f
            isEnabled = false
        }
        barChart.axisLeft.apply {
            axisMaximum = max
            axisMinimum = 0f
            isEnabled = false
        }
        barChart.legend.isEnabled = false
    }

    override fun setDate(year: Int, month: Int) {
        super.setDate(year, month)
        loadData(year, month, kind)
    }

    private class CustomValueFormatter : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            return if (value == 0f) "" else value.toString()
        }
    }
}
