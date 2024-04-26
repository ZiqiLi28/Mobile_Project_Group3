package com.example.accounting

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.accounting.db.DBManager

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.setting_iv_back -> finish()
            R.id.setting_tv_clear -> showDeleteDialog()
        }
    }

    private fun showDeleteDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Reminder")
            .setMessage("Are you sure you want to delete all records?\nYou can't recover them after deletion!")
            .setPositiveButton("Cancel", null)
            .setNegativeButton("Delete") { _, _ ->
                DBManager.deleteAllAccount()
                Toast.makeText(this@SettingActivity, "Deleted successfully!", Toast.LENGTH_SHORT).show()
            }
        builder.create().show()
    }
}
