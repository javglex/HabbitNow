package com.newpath.jeg.habbitnow.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.newpath.jeg.habbitnow.R
import com.newpath.jeg.habbitnow.models.MyHabit
import com.newpath.jeg.habbitnow.ui.viewholders.HabitItemViewHolder

class HabitsAdapter: RecyclerView.Adapter<HabitItemViewHolder>() {

    var myHabitsList =  listOf<MyHabit>()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitItemViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.item_habit, parent, false) as ConstraintLayout

        return  HabitItemViewHolder(view)

    }

    override fun onBindViewHolder(holder: HabitItemViewHolder, position: Int) {

        val habitItem: MyHabit = myHabitsList[position]
        (holder.layout.getViewById(R.id.tv_habit_name) as TextView).text = "" + habitItem.habitName
        (holder.layout.getViewById(R.id.tv_days_active) as TextView).text = "" + habitItem.alarmType

    }

    override fun getItemCount(): Int = myHabitsList.size


}