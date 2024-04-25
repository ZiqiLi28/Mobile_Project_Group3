package com.example.accounting.frag_record

import android.annotation.SuppressLint
import com.example.accounting.R
import com.example.accounting.db.DBManager
import com.example.accounting.db.TypeBean

class IncomeFragment : BaseRecordFragment() {
    @SuppressLint("SetTextI18n")
    override fun loadDataGV() {
        super.loadDataGV()
        val inlist: List<TypeBean> = DBManager.getTypeList(1)
        typeList.addAll(inlist)
        adapter.notifyDataSetChanged()
        typeTv.text = "Other"
        typeIv.setImageResource(R.mipmap.in_qt_fs)
    }

    override fun saveAccountToDB() {
        accountBean.kind = 1
        DBManager.insertItemToAccounttb(accountBean)
    }
}
