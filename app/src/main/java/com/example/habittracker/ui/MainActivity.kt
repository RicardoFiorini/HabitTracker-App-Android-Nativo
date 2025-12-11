package com.example.habittracker.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.habittracker.HabitTrackerApplication
import com.example.habittracker.databinding.ActivityMainBinding
import com.example.habittracker.viewmodel.HabitViewModel
import com.example.habittracker.viewmodel.HabitViewModelFactory
import com.example.habittracker.worker.ReminderWorker
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: HabitViewModel by viewModels {
        HabitViewModelFactory((application as HabitTrackerApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // A Splash Screen deve ser instalada ANTES do super.onCreate e do setContentView
        installSplashScreen()

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = HabitAdapter(
            onCheckClick = { habit ->
                viewModel.toggleHabitForToday(habit.id)
                // Feedback visual simples
                Toast.makeText(this, "${habit.title} marcado!", Toast.LENGTH_SHORT).show()
            },
            onItemClick = { habit ->
                val intent = Intent(this, HabitDetailActivity::class.java).apply {
                    putExtra("HABIT_ID", habit.id)
                    putExtra("HABIT_TITLE", habit.title)
                }
                startActivity(intent)
            }
        )

        binding.rvHabits.layoutManager = LinearLayoutManager(this)
        binding.rvHabits.adapter = adapter

        // Observa a lista de hábitos do banco de dados
        viewModel.allHabits.observe(this) { habits ->
            adapter.submitList(habits)
        }

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, AddHabitActivity::class.java))
        }

        setupWorker()
    }

    private fun setupWorker() {
        // Configura o lembrete diário para rodar a cada 24 horas
        val workRequest = PeriodicWorkRequestBuilder<ReminderWorker>(24, TimeUnit.HOURS)
            .build()
        WorkManager.getInstance(this).enqueue(workRequest)
    }
}