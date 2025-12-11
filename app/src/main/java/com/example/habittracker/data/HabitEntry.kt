package com.example.habittracker.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "habit_entries",
    foreignKeys = [
        ForeignKey(
            entity = Habit::class,
            parentColumns = ["id"],
            childColumns = ["habitId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class HabitEntry(
    @PrimaryKey(autoGenerate = true) val entryId: Int = 0,
    val habitId: Int,
    val dateTimestamp: Long // Vamos salvar o dia em millis (zerando as horas)
)