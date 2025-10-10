package com.example.baitaptuan21

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class EmailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email)

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val tvError = findViewById<TextView>(R.id.tvError)
        val btnCheck = findViewById<Button>(R.id.btnCheck)

        // ✅ Khi người dùng nhấn vào EditText → tự bật bàn phím
        etEmail.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
            }
        }

        // ✅ Khi người dùng nhấn vào EditText (trường hợp focusChange chưa gọi)
        etEmail.setOnClickListener {
            etEmail.requestFocus()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(etEmail, InputMethodManager.SHOW_IMPLICIT)
        }

        // 🔹 Kiểm tra email
        btnCheck.setOnClickListener {
            val email = etEmail.text.toString().trim()

            tvError.setTextColor(getColor(android.R.color.holo_red_dark))
            when {
                email.isEmpty() -> {
                    tvError.text = "⚠️ Email không được để trống"
                    tvError.visibility = TextView.VISIBLE
                }
                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    tvError.text = "❌ Email không đúng định dạng"
                    tvError.visibility = TextView.VISIBLE
                }
                else -> {
                    tvError.text = "✅ Email hợp lệ"
                    tvError.setTextColor(getColor(android.R.color.holo_green_dark))
                    tvError.visibility = TextView.VISIBLE
                }
            }
        }
    }
}
