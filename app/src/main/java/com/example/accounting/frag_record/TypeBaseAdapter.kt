package com.example.accounting.frag_record

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.accounting.R
import com.example.accounting.db.TypeBean

class TypeBaseAdapter(private val context: Context, private val mDatas: List<TypeBean>) : BaseAdapter() {
    var selectPos = 0

    override fun getCount(): Int = mDatas.size

    override fun getItem(position: Int): Any = mDatas[position]

    override fun getItemId(position: Int): Long = position.toLong()

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recordfrag_gv, parent, false)
        val iv: ImageView = view.findViewById(R.id.item_recordfrag_iv)
        val tv: TextView = view.findViewById(R.id.item_recordfrag_tv)
        val typeBean = mDatas[position]
        tv.text = typeBean.typename
        if (selectPos == position) {
            iv.setImageResource(typeBean.simageId)
        } else {
            iv.setImageResource(typeBean.imageId)
        }
        return view
    }
}