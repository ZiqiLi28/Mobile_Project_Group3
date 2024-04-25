package com.example.accounting

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.accounting.adapter.ChartVPAdapter
import com.example.accounting.db.DBManager
import com.example.accounting.frag_chart.IncomeChartFragment
import com.example.accounting.frag_chart.OutcomeChartFragment
import com.example.accounting.utils.CalendarDialog
import java.util.*

class MonthChartActivity : AppCompatActivity() {
    private lateinit var inBtn: Button
    private lateinit var outBtn: Button
    private lateinit var dateTv: TextView
    private lateinit var inTv: TextView
    private lateinit var outTv: TextView
    private lateinit var chartVp: ViewPager
    private var year: Int = 0
    private var month: Int = 0
    private var selectPos = -1
    private var selectMonth = -1
    private lateinit var chartFragList: MutableList<Fragment>
    private lateinit var incomeChartFragment: IncomeChartFragment
    private lateinit var outcomeChartFragment: OutcomeChartFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_month_chart)
        initView()
        initTime()
        initStatistics(year, month)
        initFrag()
        setVPSelectListener()
    }

    private fun setVPSelectListener() {
        chartVp.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                setButtonStyle(position)
            }
        })
    }

    private fun initFrag() {
        chartFragList = mutableListOf()
        incomeChartFragment = IncomeChartFragment()
        outcomeChartFragment = OutcomeChartFragment()
        val bundle = Bundle()
        bundle.putInt("year", year)
        bundle.putInt("month", month)
        incomeChartFragment.arguments = bundle
        outcomeChartFragment.arguments = bundle
        chartFragList.add(outcomeChartFragment)
        chartFragList.add(incomeChartFragment)
        val chartVPAdapter = ChartVPAdapter(supportFragmentManager, chartFragList)
        chartVp.adapter = chartVPAdapter
    }

    @SuppressLint("SetTextI18n")
    private fun initStatistics(year: Int, month: Int) {
        val inMoneyOneMonth = DBManager.getSumMoneyOneMonth(year, month, 1)
        val outMoneyOneMonth = DBManager.getSumMoneyOneMonth(year, month, 0)
        val incountItemOneMonth = DBManager.getCountItemOneMonth(year, month, 1)
        val outcountItemOneMonth = DBManager.getCountItemOneMonth(year, month, 0)
        dateTv.text = "${month}.${year} Monthly Bill"
        outTv.text = "Total $outcountItemOneMonth expenses: € $outMoneyOneMonth"
        inTv.text = "Total $incountItemOneMonth income: € $inMoneyOneMonth"
    }

    private fun initTime() {
        val calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH) + 1
    }

    private fun initView() {
        inBtn = findViewById(R.id.chart_btn_in)
        outBtn = findViewById(R.id.chart_btn_out)
        dateTv = findViewById(R.id.chart_tv_date)
        inTv = findViewById(R.id.chart_tv_in)
        outTv = findViewById(R.id.chart_tv_out)
        chartVp = findViewById(R.id.chart_vp)
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.chart_iv_back -> finish()
            R.id.chart_iv_rili -> showCalendarDialog()
            R.id.chart_btn_in -> {
                setButtonStyle(1)
                chartVp.currentItem = 1
            }
            R.id.chart_btn_out -> {
                setButtonStyle(0)
                chartVp.currentItem = 0
            }
        }
    }

    private fun showCalendarDialog() {
        val dialog = CalendarDialog(this, selectPos, selectMonth)
        dialog.show()
        dialog.setDialogSize()
        dialog.setOnRefreshListener { selPos, year, month ->
            this.selectPos = selPos
            this.selectMonth = month
            initStatistics(year, month)
            incomeChartFragment.setDate(year, month)
            outcomeChartFragment.setDate(year, month)

        }
    }

    private fun setButtonStyle(kind: Int) {
        if (kind == 0) {
            outBtn.setBackgroundResource(R.drawable.main_recordbtn_bg)
            outBtn.setTextColor(Color.WHITE)
            inBtn.setBackgroundResource(R.drawable.dialog_btn_bg)
            inBtn.setTextColor(Color.BLACK)
        } else if (kind == 1) {
            inBtn.setBackgroundResource(R.drawable.main_recordbtn_bg)
            inBtn.setTextColor(Color.WHITE)
            outBtn.setBackgroundResource(R.drawable.dialog_btn_bg)
            outBtn.setTextColor(Color.BLACK)
        }
    }
}
