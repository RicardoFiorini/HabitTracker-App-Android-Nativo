package com.example.habittracker.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker.data.Habit
import com.example.habittracker.databinding.ItemHabitBinding

class HabitAdapter(
    private val onCheckClick: (Habit) -> Unit,
    private val onItemClick: (Habit) -> Unit
) : ListAdapter<Habit, HabitAdapter.HabitViewHolder>(HabitDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val binding = ItemHabitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HabitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = getItem(position)
        holder.bind(habit)
    }

    inner class HabitViewHolder(private val binding: ItemHabitBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(habit: Habit) {
            binding.tvHabitTitle.text = habit.title
            binding.tvHabitDesc.text = habit.description

            binding.btnCheckToday.setOnClickListener {
                onCheckClick(habit)
            }

            binding.root.setOnClickListener {
                onItemClick(habit)
            }
        }
    }

    class HabitDiffCallback : DiffUtil.ItemCallback<Habit>() {
        override fun areItemsTheSame(oldItem: Habit, newItem: Habit) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Habit, newItem: Habit) = oldItem == newItem
    }
}