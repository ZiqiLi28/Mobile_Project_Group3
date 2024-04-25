package com.example.accounting

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.accounting.adapter.AccountAdapter
import com.example.accounting.db.AccountBean
import com.example.accounting.db.DBManager

class SearchActivity : AppCompatActivity() {
    private lateinit var searchLv: ListView
    private lateinit var searchEt: EditText
    private lateinit var emptyTv: TextView
    private lateinit var mDatas: MutableList<AccountBean>
    private lateinit var adapter: AccountAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initView()
        mDatas = mutableListOf()
        adapter = AccountAdapter(this, mDatas)
        searchLv.adapter = adapter
        searchLv.emptyView = emptyTv
    }

    private fun initView() {
        searchEt = findViewById(R.id.search_et)
        searchLv = findViewById(R.id.search_lv)
        emptyTv = findViewById(R.id.search_tv_empty)
    }

    @SuppressLint("NonConstantResourceId")
    fun onClick(view: View) {
        when (view.id) {
            R.id.search_iv_back -> finish()
            R.id.search_iv_sh -> {
                val msg = searchEt.text.toString().trim()
                if (TextUtils.isEmpty(msg)) {
                    Toast.makeText(this, "The input content cannot be empty!", Toast.LENGTH_SHORT).show()
                    return
                }
                val list = DBManager.getAccountListByRemarkFromAccounttb(msg)
                mDatas.clear()
                mDatas.addAll(list)
                adapter.notifyDataSetChanged()
            }
        }
    }
}