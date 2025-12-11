package com.example.habittracker.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.habittracker.HabitTrackerApplication
import com.example.habittracker.databinding.ActivityAddHabitBinding
import com.example.habittracker.viewmodel.HabitViewModel
import com.example.habittracker.viewmodel.HabitViewModelFactory

class AddHabitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddHabitBinding
    private val viewModel: HabitViewModel by viewModels {
        HabitViewModelFactory((application as HabitTrackerApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddHabitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val desc = binding.etDescription.text.toString()

            if (title.isNotBlank()) {
                viewModel.insert(title, desc)
                finish()
            } else {
                Toast.makeText(this, "Preencha o título", Toast.LENGTH_SHORT).show()
            }
        }
    }
}