package com.example.accounting

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.about_iv_back -> {
                finish()
            }
        }
    }
}
