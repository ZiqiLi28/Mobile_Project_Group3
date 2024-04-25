package com.example.accounting.frag_chart

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.accounting.R
import com.example.accounting.adapter.ChartItemAdapter
import com.example.accounting.db.ChartItemBean
import com.example.accounting.db.DBManager
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.formatter.ValueFormatter

abstract class BaseChartFragment : Fragment() {
    private lateinit var chartLv: ListView
    var year: Int = 0
    var month: Int = 0
    private var mDatas: MutableList<ChartItemBean> = mutableListOf()
    lateinit var barChart: BarChart
    lateinit var chartTv: TextView
    private lateinit var itemAdapter: ChartItemAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_income_chart, container, false)
        chartLv = view.findViewById(R.id.frag_chart_lv)
        val bundle = arguments
        if (bundle != null) {
            year = bundle.getInt("year")
            month = bundle.getInt("month")
        }
        itemAdapter = context?.let { ChartItemAdapter(it, mDatas) }!!
        chartLv.adapter = itemAdapter
        addLVHeaderView()
        return view
    }

    @SuppressLint("InflateParams")
    private fun addLVHeaderView() {
        val headerView = layoutInflater.inflate(R.layout.item_chartfrag_top, null)
        chartLv.addHeaderView(headerView)
        barChart = headerView.findViewById(R.id.item_chartfrag_chart)
        chartTv = headerView.findViewById(R.id.item_chartfrag_top_tv)
        barChart.description.isEnabled = false
        barChart.setExtraOffsets(20f, 20f, 20f, 20f)
        setAxis(year, month)
        setAxisData(year, month)
    }

    protected abstract fun setAxisData(year: Int, month: Int)

    private fun setAxis(year: Int, month: Int) {
        val xAxis = barChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(true)
        xAxis.labelCount = 31
        xAxis.textSize = 12f
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                val valInt = value.toInt()
                return when {
                    valInt == 0 -> "$month-1"
                    valInt == 14 -> "$month-15"
                    month == 2 && valInt == 27 -> "$month-28"
                    month in listOf(1, 3, 5, 7, 8, 10, 12) && valInt == 30 -> "$month-31"
                    month in listOf(4, 6, 9, 11) && valInt == 29 -> "$month-30"
                    else -> ""
                }
            }
        }

        xAxis.yOffset = 10f
        setYAxis(year, month)
    }

    protected abstract fun setYAxis(year: Int, month: Int)

    open fun setDate(year: Int, month: Int) {
        this.year = year
        this.month = month
        barChart.clear()
        barChart.invalidate()
        setAxis(year, month)
        setAxisData(year, month)
    }

    fun loadData(year: Int, month: Int, kind: Int) {
        val list = DBManager.getChartListFromAccounttb(year, month, kind)
        mDatas.clear()
        mDatas.addAll(list)
        itemAdapter.notifyDataSetChanged()
    }
}
