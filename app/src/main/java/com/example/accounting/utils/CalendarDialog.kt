package com.example.accounting.utils

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Display
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.GridView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.accounting.R
import com.example.accounting.adapter.CalendarAdapter
import com.example.accounting.db.DBManager
import java.util.*

class CalendarDialog(
    context: Context,
    private var selectPos: Int,
    private val selectMonth: Int
) : Dialog(context), View.OnClickListener {
    private lateinit var errorIv: ImageView
    private lateinit var gv: GridView
    private lateinit var hsvLayout: LinearLayout
    private lateinit var hsvViewList: MutableList<TextView>
    private lateinit var yearList: MutableList<Int>
    private var onRefreshListener: OnRefreshListener? = null
    private lateinit var adapter: CalendarAdapter

    fun setOnRefreshListener(onRefreshListener: (Int, Int, Int) -> Unit) {
        this.onRefreshListener = object : OnRefreshListener {
            override fun onRefresh(selPos: Int, year: Int, month: Int) {
                onRefreshListener(selPos, year, month)
            }
        }
    }


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_calendar)
        gv = findViewById(R.id.dialog_calendar_gv)
        errorIv = findViewById(R.id.dialog_calendar_iv)
        hsvLayout = findViewById(R.id.dialog_calendar_layout)
        errorIv.setOnClickListener(this)
        addViewToLayout()
        initGridView()
        setGVListener()
    }

    private fun setGVListener() {
        gv.setOnItemClickListener { _, _, position, _ ->
            adapter.selPos = position
            adapter.notifyDataSetInvalidated()
            val month = position + 1
            val year = adapter.year
            onRefreshListener?.onRefresh(selectPos, year, month)
            cancel()
        }
    }

    private fun initGridView() {
        val selYear = yearList[selectPos]
        adapter = CalendarAdapter(context, selYear)
        adapter.selPos = if (selectMonth == -1) {
            Calendar.getInstance().get(Calendar.MONTH)
        } else {
            selectMonth - 1
        }
        gv.adapter = adapter
    }

    @SuppressLint("SetTextI18n", "InflateParams")
    private fun addViewToLayout() {
        hsvViewList = ArrayList()
        yearList = DBManager.getYearListFromAccounttb().toMutableList()
        if (yearList.isEmpty()) {
            val year = Calendar.getInstance().get(Calendar.YEAR)
            yearList.add(year)
        }
        for (i in yearList.indices) {
            val year = yearList[i]
            val view = layoutInflater.inflate(R.layout.item_dialogcal_hsv, null)
            hsvLayout.addView(view)
            val hsvTv = view.findViewById<TextView>(R.id.item_dialogcal_hsv_tv)
            hsvTv.text = year.toString()
            hsvViewList.add(hsvTv)
        }
        if (selectPos == -1) {
            selectPos = hsvViewList.size - 1
        }
        changeTvbg(selectPos)
        setHSVClickListener()
    }

    private fun setHSVClickListener() {
        for (i in hsvViewList.indices) {
            val view = hsvViewList[i]
            view.setOnClickListener {
                changeTvbg(i)
                selectPos = i
                val year = yearList[selectPos]
                adapter.setCalenderYear(year)
            }
        }
    }

    private fun changeTvbg(selectPos: Int) {
        for (i in hsvViewList.indices) {
            val tv = hsvViewList[i]
            tv.setBackgroundResource(R.drawable.dialog_btn_bg)
            tv.setTextColor(Color.BLACK)
        }
        val selView = hsvViewList[selectPos]
        selView.setBackgroundResource(R.drawable.main_recordbtn_bg)
        selView.setTextColor(Color.WHITE)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.dialog_calendar_iv) {
            cancel()
        }
    }

    fun setDialogSize() {
        val window: Window = window!!
        val wlp: WindowManager.LayoutParams = window.attributes
        val d: Display = window.windowManager.defaultDisplay
        wlp.width = d.width
        wlp.gravity = Gravity.TOP
        window.setBackgroundDrawableResource(android.R.color.transparent)
        window.attributes = wlp
    }

    interface OnRefreshListener {
        fun onRefresh(selPos: Int, year: Int, month: Int)
    }
}