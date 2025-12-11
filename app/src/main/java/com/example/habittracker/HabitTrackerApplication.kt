package com.example.habittracker

import android.app.Application
import com.example.habittracker.data.AppDatabase
import com.example.habittracker.data.HabitRepository

class HabitTrackerApplication : Application() {
    // Lazy initialization
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { HabitRepository(database.habitDao()) }
}