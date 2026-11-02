package com.example.bitlife

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class LogListFragment : Fragment() {

    private val entries = mutableListOf<HealthEntry>()
    private lateinit var adapter: EntryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_log_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvEntries = view.findViewById<RecyclerView>(R.id.rvEntries)
        val btnAddEntry = view.findViewById<Button>(R.id.btnAddEntry)

        // Fixed: Only pass entries to EntryAdapter
        adapter = EntryAdapter(entries)
        rvEntries.adapter = adapter
        rvEntries.layoutManager = LinearLayoutManager(requireContext())

        // Fetch data from Room
        lifecycleScope.launch {
            (requireActivity().application as BitFitApplication).db.healthDao().getAllEntries().collect { databaseList ->
                entries.clear()
                entries.addAll(databaseList)
                adapter.notifyDataSetChanged()
            }
        }

        btnAddEntry.setOnClickListener {
            val intent = Intent(requireContext(), CreateEntryActivity::class.java)
            startActivity(intent)
        }
    }
}