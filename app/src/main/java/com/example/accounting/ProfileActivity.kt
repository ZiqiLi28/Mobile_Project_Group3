package com.example.accounting

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        enableEdgeToEdge()

        // Retrieve email from intent extra
        val email = intent.getStringExtra("email")

        // Set email TextView
        val emailTextView = findViewById<TextView>(R.id.emailTextView)
        emailTextView.text = "Email: $email"

        // Get current user
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            // Retrieve creation timestamp from user metadata
            val metadata = it.metadata
            if (metadata != null) {
                val creationTimestamp = metadata.creationTimestamp

                // Convert timestamp to date string
                val creationDate = convertTimestampToDate(creationTimestamp)

                // Set creation date TextView
                val creationDateTextView = findViewById<TextView>(R.id.creationDateTextView)
                creationDateTextView.text = "Account created on: $creationDate"
            }
        }

        // Apply edge-to-edge navigation
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.root_layout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
// 设置返回按钮点击事件
        val backButton = findViewById<ImageView>(R.id.profile_iv_back_main)
        backButton.setOnClickListener {
            // 返回到 MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        // 设置注销按钮点击事件
        val logoutButton = findViewById<Button>(R.id.logoutButton)
        logoutButton.setOnClickListener {
            // 调用 FirebaseAuth 的 signOut() 方法退出当前用户账户
            FirebaseAuth.getInstance().signOut()
            // 返回到登录界面（或其他适当的界面）
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            // 关闭当前 Activity
            finish()
        }
    }



    private fun convertTimestampToDate(timestamp: Long): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(Date(timestamp))
    }

    private fun enableEdgeToEdge() {

    }
}
