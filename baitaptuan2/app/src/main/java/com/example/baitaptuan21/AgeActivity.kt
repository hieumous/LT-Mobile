package com.example.baitaptuan21

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class AgeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_age)

        val edtName = findViewById<EditText>(R.id.edtName)
        val edtAge = findViewById<EditText>(R.id.edtAge)
        val btnCheck = findViewById<Button>(R.id.btnCheck)
        val tvResult = findViewById<TextView>(R.id.tvResult)

        btnCheck.setOnClickListener {
            val name = edtName.text.toString().trim()
            val ageText = edtAge.text.toString().trim()

            if (name.isEmpty() || ageText.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val age = ageText.toIntOrNull()
            if (age == null) {
                Toast.makeText(this, "Tuổi phải là số", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val category = when {
                age > 65 -> "Người già 👴"
                age >= 6 -> "Người lớn 🧑"
                age >= 2 -> "Trẻ em 👧"
                else -> "Em bé 👶"
            }

            tvResult.text = "$name là $category"
            tvResult.visibility = TextView.VISIBLE
        }
    }
}
