package com.example.bitlife

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateEntryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_entry)

        val etItemName = findViewById<EditText>(R.id.etItemName)
        val etCalories = findViewById<EditText>(R.id.etCalories)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)

        btnSubmit.setOnClickListener {
            val name = etItemName.text.toString().trim()
            val calString = etCalories.text.toString().trim()

            if (name.isEmpty() || calString.isEmpty()) {
                Toast.makeText(this, "Please enter both fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val calories = calString.toIntOrNull() ?: 0
            val newEntry = HealthEntry(name = name, calories = calories)

            lifecycleScope.launch(Dispatchers.IO) {
                (application as BitFitApplication).db.healthDao().insert(newEntry)
                finish()
            }
        }
    }
}