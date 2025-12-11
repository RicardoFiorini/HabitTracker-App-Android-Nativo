package com.example.habittracker.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.habittracker.HabitTrackerApplication
import com.example.habittracker.databinding.ActivityHabitDetailBinding
import com.example.habittracker.viewmodel.HabitViewModel
import com.example.habittracker.viewmodel.HabitViewModelFactory
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HabitDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHabitDetailBinding
    private val viewModel: HabitViewModel by viewModels {
        HabitViewModelFactory((application as HabitTrackerApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHabitDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val habitId = intent.getIntExtra("HABIT_ID", -1)
        val habitTitle = intent.getStringExtra("HABIT_TITLE") ?: "Detalhes"

        binding.tvDetailTitle.text = habitTitle

        if (habitId != -1) {
            setupChartObserver(habitId)
        }
    }

    private fun setupChartObserver(habitId: Int) {
        viewModel.getHistory(habitId).observe(this) { entries ->
            val entriesList = ArrayList<BarEntry>()
            entries.forEachIndexed { index, _ ->
                entriesList.add(BarEntry(index.toFloat(), 1f))
            }

            // --- ESTILO DO DATASET ---
            val dataSet = BarDataSet(entriesList, "Dias Completados")
            dataSet.color = android.graphics.Color.parseColor("#00BFA5") // Cor Verde Menta
            dataSet.setDrawValues(false) // Remove os números em cima das barras

            val barData = BarData(dataSet)
            barData.barWidth = 0.5f // Barras mais finas

            // --- ESTILO DO GRÁFICO ---
            binding.chartProgress.apply {
                data = barData
                description.isEnabled = false // Remove descrição no canto
                legend.isEnabled = false // Remove legenda
                setTouchEnabled(false) // Gráfico estático, apenas visual
                setPinchZoom(false)

                // Eixo X (Embaixo)
                xAxis.setDrawGridLines(false) // Remove grade vertical
                xAxis.setDrawLabels(false) // Remove números do eixo X
                xAxis.setDrawAxisLine(false)

                // Eixo Y (Esquerda/Direita)
                axisLeft.setDrawGridLines(false) // Remove grade horizontal
                axisLeft.setDrawLabels(false) // Remove números do eixo Y
                axisLeft.setDrawAxisLine(false)
                axisRight.isEnabled = false

                // Animação bonita ao abrir
                animateY(1000)

                invalidate()
            }

            binding.tvStats.text = "Total realizado: ${entries.size} vezes"
        }
    }
}