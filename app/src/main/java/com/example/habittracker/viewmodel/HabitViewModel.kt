package com.example.habittracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.habittracker.data.Habit
import com.example.habittracker.data.HabitEntry
import com.example.habittracker.data.HabitRepository
import kotlinx.coroutines.launch

class HabitViewModel(private val repository: HabitRepository) : ViewModel() {

    // Transforma Flow em LiveData para a View observar
    val allHabits: LiveData<List<Habit>> = repository.allHabits.asLiveData()

    fun insert(title: String, description: String) = viewModelScope.launch {
        repository.createHabit(title, description)
    }

    fun toggleHabitForToday(habitId: Int) = viewModelScope.launch {
        val today = repository.getTodayTimestamp()
        repository.toggleHabitCompletion(habitId, today)
    }

    fun getHistory(habitId: Int): LiveData<List<HabitEntry>> {
        return repository.getHabitHistory(habitId).asLiveData()
    }
}

// Factory para criar o ViewModel com dependências
class HabitViewModelFactory(private val repository: HabitRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HabitViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HabitViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}