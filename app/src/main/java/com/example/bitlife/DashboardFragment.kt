package com.example.bitlife

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvAverageCalories = view.findViewById<TextView>(R.id.tvAverageCalories)
        val tvMinCalories = view.findViewById<TextView>(R.id.tvMinCalories)
        val tvMaxCalories = view.findViewById<TextView>(R.id.tvMaxCalories)
        val btnAddFromDashboard = view.findViewById<Button>(R.id.btnAddFromDashboard)

        // Collect items from Room and compute average, minimum, and maximum calories
        lifecycleScope.launch {
            (requireActivity().application as BitFitApplication).db.healthDao().getAllEntries().collect { items ->
                val calories = items.mapNotNull { it.calories?.toDouble() }

                if (calories.isNotEmpty()) {
                    tvAverageCalories.text = String.format("Average Calories: %.1f", calories.average())
                    tvMinCalories.text = String.format("Lowest Calories: %.1f", calories.minOrNull() ?: 0.0)
                    tvMaxCalories.text = String.format("Highest Calories: %.1f", calories.maxOrNull() ?: 0.0)
                } else {
                    tvAverageCalories.text = "Average Calories: 0"
                    tvMinCalories.text = "Lowest Calories: 0"
                    tvMaxCalories.text = "Highest Calories: 0"
                }
            }
        }

        // Open the entry form screen directly from the dashboard
        btnAddFromDashboard.setOnClickListener {
            val intent = Intent(requireContext(), CreateEntryActivity::class.java)
            startActivity(intent)
        }
    }
}