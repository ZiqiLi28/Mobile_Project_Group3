package com.example.accounting.utils

import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.text.Editable
import android.text.InputType
import android.view.View
import android.widget.EditText
import com.example.accounting.R

class KeyBoardUtils(private val keyboardView: KeyboardView, private val editText: EditText) {

    private val k1: Keyboard = Keyboard(editText.context, R.xml.key)

    var onEnsureListener: OnEnsureListener? = null

    private val listener = object : KeyboardView.OnKeyboardActionListener {
        override fun onPress(primaryCode: Int) {}

        override fun onRelease(primaryCode: Int) {}

        override fun onKey(primaryCode: Int, keyCodes: IntArray) {
            val editable: Editable = editText.text
            val start: Int = editText.selectionStart
            when (primaryCode) {
                Keyboard.KEYCODE_DELETE -> {
                    if (editable.isNotEmpty() && start > 0) {
                        editable.delete(start - 1, start)
                    }
                }
                Keyboard.KEYCODE_CANCEL -> {
                    editable.clear()
                }
                Keyboard.KEYCODE_DONE -> {
                    onEnsureListener?.onEnsure()
                }
                else -> {
                    editable.insert(start, (primaryCode.toChar()).toString())
                }
            }
        }

        override fun onText(text: CharSequence) {}

        override fun swipeLeft() {}

        override fun swipeRight() {}

        override fun swipeDown() {}

        override fun swipeUp() {}
    }

    init {
        editText.inputType = InputType.TYPE_NULL

        keyboardView.keyboard = k1
        keyboardView.isEnabled = true
        keyboardView.isPreviewEnabled = false
        keyboardView.setOnKeyboardActionListener(listener)
    }

    fun setOnEnsureListenerCustom(onEnsureListener: OnEnsureListener?) {
        this.onEnsureListener = onEnsureListener
    }

    fun showKeyboard() {
        val visibility = keyboardView.visibility
        if (visibility == View.INVISIBLE || visibility == View.GONE) {
            keyboardView.visibility = View.VISIBLE
        }
    }

    interface OnEnsureListener {
        fun onEnsure()
    }
}

