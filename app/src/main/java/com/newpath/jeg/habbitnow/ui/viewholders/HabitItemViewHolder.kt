package com.newpath.jeg.habbitnow.ui.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.newpath.jeg.habbitnow.R
import com.newpath.jeg.habbitnow.databinding.ItemHabitBinding
import com.newpath.jeg.habbitnow.models.MyHabit
import com.newpath.jeg.habbitnow.utils.StringGenerator


class HabitItemViewHolder private constructor(val binding: ItemHabitBinding): RecyclerView.ViewHolder(
    binding.root
) {


    companion object {
        const val ACTION_MENU: Int = 0
        const val ACTION_TIME: Int = 1

        fun from(parent: ViewGroup): HabitItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemHabitBinding.inflate(layoutInflater, parent, false)
            return HabitItemViewHolder(binding)
        }
    }

    fun bind(habitItem: MyHabit, myCallback: (result: MyHabit, action: Int) -> Unit) {

        binding.tvHabitName.text = "" + habitItem.habitName
        binding.tvDaysActive.text = StringGenerator.getDaysFromByte(habitItem.daysActive, binding.root.context)

        formatTimeDisplay(habitItem, binding.tvTimeBegins)

        binding.ibHabitItemMenu.setOnClickListener { view ->
            when(view.id){
                R.id.ib_habit_item_menu -> myCallback(habitItem, ACTION_MENU)
            }
        }

        binding.tvTimeBegins.setOnClickListener { view ->
            when(view.id){
                R.id.tv_time_begins -> myCallback(habitItem, ACTION_TIME)
            }
        }
    }

    private fun formatTimeDisplay(habit: MyHabit, tvTime: TextView) {

        val alarmHour: Int = habit.alarmTimeHours
        val alarmMinute: Int = habit.alarmTimeMinutes

        tvTime.text = StringGenerator.getTime(alarmHour,alarmMinute)

    }


}