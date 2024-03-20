package com.example.accounting

import android.os.Bundle
import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.main_iv_search -> {
            }
            R.id.main_btn_edit -> {
                val it1 = Intent(this@MainActivity, RecordActivity::class.java)
                startActivity(it1)
            }
            R.id.main_btn_more -> {
            }
        }
    }
}