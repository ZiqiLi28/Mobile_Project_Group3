package com.example.accounting

import android.app.Application
import com.example.accounting.db.DBManager

class UniteApp : Application() {
    override fun onCreate() {
        super.onCreate()
        DBManager.initDB(applicationContext)
    }
}
