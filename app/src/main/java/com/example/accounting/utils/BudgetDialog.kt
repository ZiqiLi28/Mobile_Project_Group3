package com.example.accounting.utils

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.view.Display
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.accounting.R

class BudgetDialog(context: Context) : Dialog(context), View.OnClickListener {
    private lateinit var cancelIv: ImageView
    private lateinit var ensureBtn: Button
    private lateinit var moneyEt: EditText
    private var onEnsureListener: OnEnsureListener? = null

    @SuppressLint("HandlerLeak")
    private val handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            val inputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    fun setOnEnsureListener(listener: OnEnsureListener) {
        this.onEnsureListener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_budget)
        cancelIv = findViewById(R.id.dialog_budget_iv_error)
        ensureBtn = findViewById(R.id.dialog_budget_btn_ensure)
        moneyEt = findViewById(R.id.dialog_budget_et)
        cancelIv.setOnClickListener(this)
        ensureBtn.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.dialog_budget_iv_error -> cancel()
            R.id.dialog_budget_btn_ensure -> {
                val data = moneyEt.text.toString()
                if (TextUtils.isEmpty(data)) {
                    Toast.makeText(context, "Input data cannot be empty!", Toast.LENGTH_SHORT).show()
                    return
                }
                val money = data.toFloat()
                if (money <= 0) {
                    Toast.makeText(context, "Budget amount must be greater than 0", Toast.LENGTH_SHORT).show()
                    return
                }
                onEnsureListener?.onEnsure(money)
                cancel()
            }
        }
    }


    fun setDialogSize() {
        val window = window ?: return
        val wlp = window.attributes
        val d: Display = window.windowManager.defaultDisplay
        wlp.width = d.width
        wlp.gravity = Gravity.BOTTOM
        window.setBackgroundDrawableResource(android.R.color.transparent)
        window.attributes = wlp
        handler.sendEmptyMessageDelayed(1, 100)
    }

    interface OnEnsureListener {
        fun onEnsure(money: Float)
    }
}
