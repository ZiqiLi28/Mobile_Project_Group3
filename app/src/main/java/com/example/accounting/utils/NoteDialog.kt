package com.example.accounting.utils

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.Display
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import com.example.accounting.R.*

class NoteDialog(context: Context, initialText: CharSequence) : Dialog(context), View.OnClickListener {

    private var editText: CharSequence? = null

    init {
        editText = initialText
    }

    private lateinit var et: EditText
    private lateinit var cancelBtn: Button
    private lateinit var ensureBtn: Button
    private var onEnsureListener: OnEnsureListener? = null

    @SuppressLint("HandlerLeak")
    private val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    fun setOnEnsureListener(onEnsureListener:OnEnsureListener?) {
        this.onEnsureListener = onEnsureListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.dialog_note)
        et = findViewById(id.dialog_note_et)
        cancelBtn = findViewById(id.dialog_note_btn_cancel)
        ensureBtn = findViewById(id.dialog_note_btn_ensure)
        cancelBtn.setOnClickListener(this)
        ensureBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            id.dialog_note_btn_cancel -> cancel()
            id.dialog_note_btn_ensure -> onEnsureListener?.onEnsure()
        }
    }

    fun getEditText(): String {
        return et.text.toString().trim()
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
        fun onEnsure()
    }
}

