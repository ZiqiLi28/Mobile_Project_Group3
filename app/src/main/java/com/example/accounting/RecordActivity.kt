package com.example.accounting

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.example.accounting.adapter.RecordPagerAdapter
import com.example.accounting.frag_record.IncomeFragment
import com.example.accounting.frag_record.OutcomeFragment
import java.util.ArrayList

class RecordActivity : AppCompatActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)
        tabLayout = findViewById(R.id.record_tabs)
        viewPager = findViewById(R.id.record_vp)
        initPager()
    }

    private fun initPager() {
        val fragmentList = ArrayList<Fragment>()
        val outFrag = OutcomeFragment()
        val inFrag = IncomeFragment()
        fragmentList.add(outFrag)
        fragmentList.add(inFrag)
        val pagerAdapter = RecordPagerAdapter(supportFragmentManager, fragmentList)
        viewPager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

    fun onClick(view: View) {
        if (view.id == R.id.record_iv_back) {
            finish()
        }
    }
}