package com.example.baitaptuan21

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import android.view.ViewGroup
import android.view.Gravity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etNumber = findViewById<EditText>(R.id.etNumber)
        val btnGenerate = findViewById<Button>(R.id.btnGenerate)
        val tvError = findViewById<TextView>(R.id.tvError)
        val layoutList = findViewById<LinearLayout>(R.id.layoutList)

        btnGenerate.setOnClickListener {
            layoutList.removeAllViews() // xóa cũ
            tvError.visibility = TextView.GONE

            val input = etNumber.text.toString()
            if (input.isEmpty() || !input.matches(Regex("\\d+"))) {
                tvError.visibility = TextView.VISIBLE
                layoutList.visibility = ViewGroup.GONE
                return@setOnClickListener
            }

            val count = input.toInt()
            layoutList.visibility = ViewGroup.VISIBLE

            for (i in 1..count) {
                // Tạo CardView
                val card = CardView(this).apply {
                    val params = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    params.setMargins(0, 8, 0, 8)
                    layoutParams = params
                    radius = 16f
                    cardElevation = 6f
                    setContentPadding(24, 24, 24, 24)
                    setCardBackgroundColor(android.graphics.Color.parseColor("#FFF8E1"))
                }

                // Tạo TextView bên trong Card
                val textView = TextView(this).apply {
                    text = "Phần tử số $i"
                    textSize = 16f
                    setTextColor(android.graphics.Color.parseColor("#212121"))
                    gravity = Gravity.CENTER
                }

                // Thêm TextView vào Card
                card.addView(textView)

                // Thêm Card vào layoutList
                layoutList.addView(card)
            }

            // Cuộn xuống cuối danh sách
            layoutList.post {
                layoutList.parent?.let {
                    if (it is ScrollView) it.fullScroll(ScrollView.FOCUS_DOWN)
                }
            }
        }
    }
}