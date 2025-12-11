package com.example.habittracker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    // Retorna Habits junto com se foi completado hoje (lógica complexa seria feita no Repository, mas aqui simplificamos)
    @Query("SELECT * FROM habits ORDER BY createdAt DESC")
    fun getAllHabits(): Flow<List<Habit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: Habit)

    // Verifica histórico de um hábito específico
    @Query("SELECT * FROM habit_entries WHERE habitId = :habitId ORDER BY dateTimestamp ASC")
    fun getEntriesForHabit(habitId: Int): Flow<List<HabitEntry>>

    // Marcar como feito
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEntry(entry: HabitEntry)

    // Desmarcar (se clicou errado)
    @Query("DELETE FROM habit_entries WHERE habitId = :habitId AND dateTimestamp = :date")
    suspend fun deleteEntry(habitId: Int, date: Long)

    @Query("SELECT EXISTS(SELECT * FROM habit_entries WHERE habitId = :habitId AND dateTimestamp = :date)")
    suspend fun isHabitCompletedOnDate(habitId: Int, date: Long): Boolean
}