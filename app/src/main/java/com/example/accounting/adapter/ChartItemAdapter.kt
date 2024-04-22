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
import com.example.accounting.db.ChartItemBean
import com.example.accounting.utils.FloatUtils

class ChartItemAdapter(context: Context, private val mDatas: List<ChartItemBean>) : BaseAdapter() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return mDatas.size
    }

    override fun getItem(position: Int): Any {
        return mDatas[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val holder: ViewHolder?
        var convertView = convertView
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_chartfrag_lv, parent, false)
            holder = ViewHolder(convertView)
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        val bean = mDatas[position]
        holder.iv.setImageResource(bean.sImageId)
        holder.typeTv.text = bean.type
        val ratio = bean.ratio
        val pert = FloatUtils.ratioToPercent(ratio)
        holder.ratioTv.text = pert
        holder.totalTv.text = "€ ${bean.totalMoney}"
        return convertView!!
    }

    private class ViewHolder(view: View) {
        val typeTv: TextView = view.findViewById(R.id.item_chartfrag_tv_type)
        val ratioTv: TextView = view.findViewById(R.id.item_chartfrag_tv_pert)
        val totalTv: TextView = view.findViewById(R.id.item_chartfrag_tv_sum)
        val iv: ImageView = view.findViewById(R.id.item_chartfrag_iv)
    }
}
