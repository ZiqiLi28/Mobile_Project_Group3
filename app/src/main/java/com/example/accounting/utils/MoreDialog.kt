package com.example.accounting.utils

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Display
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import com.example.accounting.AboutActivity
import com.example.accounting.HistoryActivity
import com.example.accounting.R
import com.example.accounting.SettingActivity

class MoreDialog(context: Context) : Dialog(context), View.OnClickListener {
    private lateinit var aboutBtn: Button
    private lateinit var settingBtn: Button
    private lateinit var historyBtn: Button
    private lateinit var errorIv: ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_more)
        aboutBtn = findViewById(R.id.dialog_more_btn_about)
        settingBtn = findViewById(R.id.dialog_more_btn_setting)
        historyBtn = findViewById(R.id.dialog_more_btn_record)
        errorIv = findViewById(R.id.dialog_more_iv)
        aboutBtn.setOnClickListener(this)
        settingBtn.setOnClickListener(this)
        historyBtn.setOnClickListener(this)
        errorIv.setOnClickListener(this)
    }

    @SuppressLint("NonConstantResourceId")
    override fun onClick(v: View?) {
        val intent = Intent()
        when (v?.id) {
            R.id.dialog_more_btn_about -> {
                intent.setClass(context, AboutActivity::class.java)
                context.startActivity(intent)
            }

            R.id.dialog_more_btn_setting -> {
                intent.setClass(context, SettingActivity::class.java)
                context.startActivity(intent)
            }

            R.id.dialog_more_btn_record -> {
                intent.setClass(context, HistoryActivity::class.java)
                context.startActivity(intent)
            }
        }
            cancel()
        }


    fun setDialogSize() {
        val window: Window? = window
        val wlp: WindowManager.LayoutParams = window!!.attributes
        val d: Display = window.windowManager.defaultDisplay
        wlp.width = d.width
        wlp.gravity = Gravity.BOTTOM
        window.setBackgroundDrawableResource(android.R.color.transparent)
        window.attributes = wlp
    }
}
