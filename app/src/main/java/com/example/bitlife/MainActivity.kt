package com.example.bitlife

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val entries = mutableListOf<HealthEntry>()
    private lateinit var adapter: EntryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rvEntries = findViewById<RecyclerView>(R.id.rvEntries)
        adapter = EntryAdapter(entries)
        rvEntries.adapter = adapter
        rvEntries.layoutManager = LinearLayoutManager(this)

        val healthDao = (application as BitFitApplication).db.healthDao()

        lifecycleScope.launch {
            healthDao.getAllEntries().collect { entryList ->
                adapter.updateData(entryList)
            }
        }

        val btnRecord = findViewById<Button>(R.id.btnRecordEntry)
        btnRecord.setOnClickListener {
            val intent = Intent(this, CreateEntryActivity::class.java)
            startActivity(intent)
        }
    }
}