package com.newpath.jeg.habbitnow.ui.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.newpath.jeg.habbitnow.R
import com.newpath.jeg.habbitnow.models.MyHabit

class HabitItemViewHolder private constructor(itemView: View): RecyclerView.ViewHolder(itemView) {

    val habitName: TextView = itemView.findViewById(R.id.tv_habit_name)
    val daysActive: TextView = itemView.findViewById(R.id.tv_days_active)

    companion object {
        public fun from(parent: ViewGroup): HabitItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater
                .inflate(R.layout.item_habit, parent, false) as ConstraintLayout

            return HabitItemViewHolder(view)
        }
    }

    public fun bind(habitItem: MyHabit) {
        habitName.text = "" + habitItem.habitName
        daysActive.text = "" + habitItem.alarmType
    }
}