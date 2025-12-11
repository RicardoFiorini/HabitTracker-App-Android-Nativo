package com.example.habittracker.data

import kotlinx.coroutines.flow.Flow
import java.util.Calendar

class HabitRepository(private val habitDao: HabitDao) {

    val allHabits: Flow<List<Habit>> = habitDao.getAllHabits()

    suspend fun createHabit(title: String, description: String) {
        val habit = Habit(title = title, description = description)
        habitDao.insertHabit(habit)
    }

    fun getHabitHistory(habitId: Int): Flow<List<HabitEntry>> {
        return habitDao.getEntriesForHabit(habitId)
    }

    suspend fun toggleHabitCompletion(habitId: Int, dateTimestamp: Long) {
        // Verifica se já existe, se sim deleta, se não cria
        if (habitDao.isHabitCompletedOnDate(habitId, dateTimestamp)) {
            habitDao.deleteEntry(habitId, dateTimestamp)
        } else {
            habitDao.insertEntry(HabitEntry(habitId = habitId, dateTimestamp = dateTimestamp))
        }
    }

    // Utilitário para pegar o dia de hoje zerado (meia noite)
    fun getTodayTimestamp(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }
}