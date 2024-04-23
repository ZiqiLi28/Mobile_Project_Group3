package com.example.accounting

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.accounting.adapter.AccountAdapter
import com.example.accounting.db.AccountBean
import com.example.accounting.db.DBManager
import com.example.accounting.utils.CalendarDialog
import java.util.Calendar

class HistoryActivity : AppCompatActivity() {
    private lateinit var historyLv: ListView
    private lateinit var timeTv: TextView
    private var mDatas: MutableList<AccountBean> = mutableListOf()
    private lateinit var adapter: AccountAdapter
    private var year: Int = 0
    private var month: Int = 0
    private var dialogSelPos: Int = -1
    private var dialogSelMonth: Int = -1

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        historyLv = findViewById(R.id.history_lv)
        timeTv = findViewById(R.id.history_tv_time)
        adapter = AccountAdapter(this, mDatas)
        historyLv.adapter = adapter
        initTime()
        timeTv.text = "$year $month"
        loadData(year, month)
        setLVClickListener()
    }

    private fun setLVClickListener() {
        historyLv.setOnItemLongClickListener { _, _, position, _ ->
            val accountBean = mDatas[position]
            deleteItem(accountBean)
            false
        }
    }

    private fun deleteItem(accountBean: AccountBean) {
        val delId = accountBean.id
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Reminder").setMessage("Are you sure you want to delete this record?")
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Delete") { _, _ ->
                DBManager.deleteItemFromAccounttbById(delId)
                mDatas.remove(accountBean)
                adapter.notifyDataSetChanged()
            }
        builder.create().show()
    }

    private fun loadData(year: Int, month: Int) {
        val list = DBManager.getAccountListOneMonthFromAccounttb(year, month)
        mDatas.clear()
        mDatas.addAll(list)
        adapter.notifyDataSetChanged()

    }

    private fun initTime() {
        val calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH) + 1
    }

    @SuppressLint("NonConstantResourceId", "SetTextI18n")
    fun onClick(view: View) {
        when (view.id) {
            R.id.history_iv_back -> finish()
            R.id.history_iv_rili -> {
                val dialog = CalendarDialog(this, dialogSelPos, dialogSelMonth)
                dialog.show()
                dialog.setDialogSize()
                dialog.setOnRefreshListener { selPos, month, year ->
                    timeTv.text = "$month $year"
                    loadData(month, year)
                    dialogSelPos = selPos
                    dialogSelMonth = month
                }
            }
        }
    }
}
