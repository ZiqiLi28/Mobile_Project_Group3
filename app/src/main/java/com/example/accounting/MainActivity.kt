package com.example.accounting

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.accounting.adapter.AccountAdapter
import com.example.accounting.db.AccountBean
import com.example.accounting.db.DBManager
import com.example.accounting.utils.BudgetDialog
import com.example.accounting.utils.MoreDialog
import com.google.firebase.auth.FirebaseAuth
import java.util.Calendar

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var todayLv: ListView
    private lateinit var mDatas: MutableList<AccountBean>
    private lateinit var searchIv: ImageView
    private lateinit var editBtn: Button
    private lateinit var moreBtn: ImageButton
    private lateinit var adapter: AccountAdapter
    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0
    private lateinit var headerView: View
    private lateinit var topOutTv: TextView
    private lateinit var topInTv: TextView
    private lateinit var topbudgetTv: TextView
    private lateinit var topConTv: TextView
    private lateinit var topShowIv: ImageView
    private lateinit var accountBtn: TextView
    private lateinit var preferences: SharedPreferences
    private var isShow: Boolean = true

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initTime()
        initView()
        DBManager.initDB(this)
        preferences = getSharedPreferences("budget", Context.MODE_PRIVATE)
        addLVHeaderView()
        mDatas = ArrayList()
        adapter = AccountAdapter(this, mDatas)
        todayLv.adapter = adapter
    }

    private fun initView() {
        todayLv = findViewById(R.id.main_lv)
        editBtn = findViewById(R.id.main_btn_edit)
        moreBtn = findViewById(R.id.main_btn_more)
        searchIv = findViewById(R.id.main_iv_search)
        accountBtn = findViewById(R.id.main_tv_signin)
        editBtn.setOnClickListener(this)
        moreBtn.setOnClickListener(this)
        searchIv.setOnClickListener(this)
        setLVLongClickListener()
    }

    private fun setLVLongClickListener() {
        todayLv.setOnItemLongClickListener { _, _, position, _ ->
            if (position == 0) {
                return@setOnItemLongClickListener false
            }
            val pos = position - 1
            val clickBean = mDatas[pos]
            showDeleteItemDialog(clickBean)
            return@setOnItemLongClickListener false

        }
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            accountBtn.text = currentUser.email
            accountBtn.setOnClickListener {
                val profileIntent = Intent(this, ProfileActivity::class.java)
                val currentUser = FirebaseAuth.getInstance().currentUser
                currentUser?.let {
                    profileIntent.putExtra("email", currentUser.email)
                }
                startActivity(profileIntent)
            }

        } else {
            accountBtn.setOnClickListener {
                val signinIntent = Intent(this, SigninActivity::class.java)
                startActivity(signinIntent)
            }
        }
    }

    private fun showDeleteItemDialog(clickBean: AccountBean) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Info").setMessage("Delete this record？")
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Confirm") { _, _ ->
                val click_id = clickBean.id
                DBManager.deleteItemFromAccounttbById(click_id)
                mDatas.remove(clickBean)
                adapter.notifyDataSetChanged()
                setTopTvShow()
            }
        builder.create().show()
    }

    @SuppressLint("InflateParams")
    private fun addLVHeaderView() {
        headerView = layoutInflater.inflate(R.layout.item_mainlv_top, null)
        todayLv.addHeaderView(headerView)
        topOutTv = headerView.findViewById(R.id.item_mainlv_top_tv_out)
        topInTv = headerView.findViewById(R.id.item_mainlv_top_tv_in)
        topbudgetTv = headerView.findViewById(R.id.item_mainlv_top_tv_budget)
        topConTv = headerView.findViewById(R.id.item_mainlv_top_tv_day)
        topShowIv = headerView.findViewById(R.id.item_mainlv_top_iv_hide)
        topbudgetTv.setOnClickListener(this)
        topShowIv.setOnClickListener(this)
        headerView.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, MonthChartActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initTime() {
        val calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH) + 1
        day = calendar.get(Calendar.DAY_OF_MONTH)
    }

    override fun onResume() {
        super.onResume()
        loadDBData()
        setTopTvShow()
    }

    @SuppressLint("SetTextI18n")
    private fun setTopTvShow() {
        val incomeOneDay = DBManager.getSumMoneyOneDay(year, month, day, 1)
        val outcomeOneDay = DBManager.getSumMoneyOneDay(year, month, day, 0)
        val infoOneDay = "Today's expenses €$outcomeOneDay  Income €$incomeOneDay"
        topConTv.text = infoOneDay
        val incomeOneMonth = DBManager.getSumMoneyOneMonth(year, month, 1)
        val outcomeOneMonth = DBManager.getSumMoneyOneMonth(year, month, 0)
        topInTv.text = "€$incomeOneMonth"
        topOutTv.text = "€$outcomeOneMonth"
        val bmoney = preferences.getFloat("bmoney", 0f)
        if (bmoney == 0f) {
            topbudgetTv.text = "€ 0"
        } else {
            val syMoney = bmoney - outcomeOneMonth
            topbudgetTv.text = "€$syMoney"
        }
    }

    private fun loadDBData() {
        val list = DBManager.getAccountListOneDayFromAccounttb(year, month, day)
        mDatas.clear()
        mDatas.addAll(list)
        adapter.notifyDataSetChanged()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.main_iv_search -> {
                val it = Intent(this, SearchActivity::class.java)
                startActivity(it)
            }
            R.id.main_tv_signin -> {
                val it = Intent(this, SigninActivity::class.java)
                startActivity(it)
            }
            R.id.main_btn_edit -> {
                val it1 = Intent(this, RecordActivity::class.java)
                startActivity(it1)
            }
            R.id.main_btn_more -> {
                val moreDialog = MoreDialog(this)
                moreDialog.show()
                moreDialog.setDialogSize()
            }
            R.id.item_mainlv_top_tv_budget -> showBudgetDialog()
            R.id.item_mainlv_top_iv_hide -> toggleShow()
            R.id.headerView -> {
                val intent = Intent()
                intent.setClass(this, MonthChartActivity::class.java)
                startActivity(intent)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showBudgetDialog() {
        val dialog = BudgetDialog(this)
        dialog.show()
        dialog.setDialogSize()
        dialog.setOnEnsureListener(object : BudgetDialog.OnEnsureListener{
            override fun onEnsure(money: Float) {
                // Handle the ensure event here
                topbudgetTv.text = "€$money"
                preferences.edit().putFloat("bmoney", money).apply()
                // For example, update UI or save data
                val outcomeOneMonth = DBManager.getSumMoneyOneMonth(year, month, 0)
                val syMoney = money - outcomeOneMonth
                topbudgetTv.text = "€$syMoney"
            }
        })
    }

    private fun toggleShow() {
        if (isShow) {
            val passwordMethod = PasswordTransformationMethod.getInstance()
            topInTv.transformationMethod = passwordMethod
            topOutTv.transformationMethod = passwordMethod
            topbudgetTv.transformationMethod = passwordMethod
            topShowIv.setImageResource(R.mipmap.ih_hide)
            isShow = false
        } else {
            val hideMethod = HideReturnsTransformationMethod.getInstance()
            topInTv.transformationMethod = hideMethod
            topOutTv.transformationMethod = hideMethod
            topbudgetTv.transformationMethod = hideMethod
            topShowIv.setImageResource(R.mipmap.ih_show)
            isShow = true
        }
    }
}