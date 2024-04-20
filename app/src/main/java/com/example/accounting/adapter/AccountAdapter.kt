package com.example.accounting.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.accounting.R
import com.example.accounting.db.AccountBean
import java.util.Calendar

class AccountAdapter(
    context: Context,
    private val mDatas: List<AccountBean>
) : BaseAdapter() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val year: Int
    private val month: Int
    private val day: Int

    init {
        val calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH) + 1
        day = calendar.get(Calendar.DAY_OF_MONTH)
    }

    override fun getCount(): Int = mDatas.size

    override fun getItem(position: Int): Any = mDatas[position]

    override fun getItemId(position: Int): Long = position.toLong()

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = inflater.inflate(R.layout.item_mainlv, parent, false)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }

        val bean = mDatas[position]
        holder.typeIv.setImageResource(bean.sImageId)
        holder.typeTv.text = bean.typename
        holder.noteTv.text = bean.note
        holder.moneyTv.text = "€ ${bean.money}"
        if (bean.year == year && bean.month == month && bean.day == day) {
            val time = bean.time?.split(" ")?.get(1)
            holder.timeTv.text = "Today $time"
        } else {
            holder.timeTv.text = bean.time
        }

        return view
    }

    private class ViewHolder(view: View) {
        val typeIv: ImageView = view.findViewById(R.id.item_mainlv_iv)
        val typeTv: TextView = view.findViewById(R.id.item_mainlv_tv_title)
        val timeTv: TextView = view.findViewById(R.id.item_mainlv_tv_time)
        val noteTv: TextView = view.findViewById(R.id.item_mainlv_tv_note)
        val moneyTv: TextView = view.findViewById(R.id.item_mainlv_tv_money)
    }
}


