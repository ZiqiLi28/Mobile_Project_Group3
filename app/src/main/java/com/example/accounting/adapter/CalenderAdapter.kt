package com.example.accounting.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.accounting.R

class CalendarAdapter(private val context: Context, yearValue: Int) : BaseAdapter() {
    var year = 0
    var selPos = -1
    private val mDatas: MutableList<String> = ArrayList()

    init {
        loadDatas(yearValue)
    }

    fun setCalenderYear(year: Int) {
        this.year = year
        mDatas.clear()
        loadDatas(year)
        notifyDataSetChanged()
    }

    private fun loadDatas(year: Int) {
        for (i in 1 until 13) {
            val data = "$year/$i"
            mDatas.add(data)
        }
    }

    override fun getCount(): Int {
        return mDatas.size
    }

    override fun getItem(position: Int): Any {
        return mDatas[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_dialogcal_gv, parent, false)
        val tv = view.findViewById<TextView>(R.id.item_dialogcal_gv_tv)
        tv.text = mDatas[position]
        tv.setBackgroundResource(R.color.grey_f3f3f3)
        tv.setTextColor(Color.BLACK)
        if (position == selPos) {
            tv.setBackgroundResource(R.color.purple)
            tv.setTextColor(Color.WHITE)
        }
        return view
    }
}
